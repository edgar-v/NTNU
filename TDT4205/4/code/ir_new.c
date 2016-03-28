#include <vslc.h>

tlhash_t *global_names;
char **string_list;
size_t n_string_list = 8, stringc = 0;
int g_seq = 0;

void find_globals_rec(node_t *root, node_t *parent, bool global)
{
    for (int i = 0; i < root->n_children; i++)
    {
        global = (global && root->type != FUNCTION) ? true : false;
        find_globals_rec(root->children[i], root, global);
    }
    if (root->data == NULL)
        return;

    if (root->type != IDENTIFIER_DATA)
        return;

    symbol_t *sym = malloc(sizeof(symbol_t));
    sym->name = root->data;

    if (parent->type == FUNCTION && global == true) {
        global = false;
        sym->node = parent;
        sym->seq = g_seq++;
        sym->type = SYM_FUNCTION;


        // Find parameter names
        int num_params = 0;
        int local_seq = 0;
        tlhash_t *local_names = malloc(sizeof(tlhash_t));
        tlhash_init(local_names, 32);
        for (int i = 0; i < parent->n_children; i++) {
            if (parent->children[i]->type == VARIABLE_LIST) {
                for (int j = 0; j < parent->children[i]->n_children; j++) {
                    symbol_t *local_sym = malloc(sizeof(symbol_t));
                    *local_sym = (symbol_t) {
                        .name = parent->children[i]->children[j]->data,
                        .type = SYM_PARAMETER,
                        .node = parent,
                        .seq = local_seq++
                    };
                    tlhash_insert(local_names, (char*) local_sym->name, strlen(local_sym->name)+1, local_sym);
                    num_params++;
                }
            }
        }
         
        sym->nparms = num_params;
        sym->locals = local_names;
    }
    if (global) {
        sym->type = SYM_GLOBAL_VAR;
    } else {
        sym->type = SYM_LOCAL_VAR;
    }

    tlhash_insert(global_names, (char*) sym->name, strlen(sym->name)+1, sym);
}

void find_globals ( void )
{
    node_print(root, 0);
    global_names = malloc ( sizeof(tlhash_t) );
    tlhash_init ( global_names, 32 );
    for (int i = 0; i < root->n_children; i++) {
        find_globals_rec(root->children[i], root, true);
    }
}


void
bind_names ( symbol_t *function, node_t *root )
{
    if (root->type == STRING_DATA) {
        string_list = realloc(string_list, sizeof(char *) * stringc+1);
        string_list[stringc] = root->data;
        root->data = malloc(sizeof(size_t *));
        memcpy(root->data, &stringc, sizeof(size_t *)); // will it break?
        stringc++;
    }
    for (int i = 0; i < root->n_children; i++)
        bind_names(function, root->children[i]);
}


void
destroy_symtab ( void )
{
    tlhash_finalize ( global_names );
    free ( global_names );
}


void
print_symbols ( void )
{
    printf ( "String table:\n" );
    for ( size_t s=0; s<stringc; s++ )
        printf  ( "%zu: %s\n", s, string_list[s] );
    printf ( "-- \n" );

    printf ( "Globals:\n" );
    size_t n_globals = tlhash_size(global_names);
    symbol_t *global_list[n_globals];
    tlhash_values ( global_names, (void **)&global_list );
    for ( size_t g=0; g<n_globals; g++ )
    {
        switch ( global_list[g]->type )
        {
            case SYM_FUNCTION:
                printf (
                    "%s: function %zu:\n",
                    global_list[g]->name, global_list[g]->seq
                );
                if ( global_list[g]->locals != NULL )
                {
                    size_t localsize = tlhash_size( global_list[g]->locals );
                    printf (
                        "\t%zu local variables, %zu are parameters:\n",
                        localsize, global_list[g]->nparms
                    );
                    symbol_t *locals[localsize];
                    tlhash_values(global_list[g]->locals, (void **)locals );
                    for ( size_t i=0; i<localsize; i++ )
                    {
                        printf ( "\t%s: ", locals[i]->name );
                        switch ( locals[i]->type )
                        {
                            case SYM_PARAMETER:
                                printf ( "parameter %zu\n", locals[i]->seq );
                                break;
                            case SYM_LOCAL_VAR:
                                printf ( "local var %zu\n", locals[i]->seq );
                                break;
                        }
                    }
                }
                break;
            case SYM_GLOBAL_VAR:
                printf ( "%s: global variable\n", global_list[g]->name );
                break;
        }
    }
    printf ( "-- \n" );
}


void
print_bindings ( node_t *root )
{
    if ( root == NULL )
        return;
    else if ( root->entry != NULL )
    {
        switch ( root->entry->type )
        {
            case SYM_GLOBAL_VAR: 
                printf ( "Linked global var '%s'\n", root->entry->name );
                break;
            case SYM_FUNCTION:
                printf ( "Linked function %zu ('%s')\n",
                    root->entry->seq, root->entry->name
                );
                break; 
            case SYM_PARAMETER:
                printf ( "Linked parameter %zu ('%s')\n",
                    root->entry->seq, root->entry->name
                );
                break;
            case SYM_LOCAL_VAR:
                printf ( "Linked local var %zu ('%s')\n",
                    root->entry->seq, root->entry->name
                );
                break;
        }
    } else if ( root->type == STRING_DATA ) {
        size_t string_index = *((size_t *)root->data);
        printf("%zu\n", string_index);
        if ( string_index < stringc )
            printf ( "Linked string %zu\n", *((size_t *)root->data) );
        else
            printf ( "(Not an indexed string)\n" );
    }
    for ( size_t c=0; c<root->n_children; c++ )
        print_bindings ( root->children[c] );
}
