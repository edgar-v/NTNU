#include <vslc.h>

tlhash_t *global_names;
char **string_list;
size_t n_string_list = 8, stringc = 0;
int func_seq = 0;
int scope = 0;
tlhash_t **scope_list;

char * local_keys[8] = {"abc", "def", "geh", "asd", "mio", "daq", "kop", "iuu" };

void find_globals_rec(node_t *root)
{
    if (root->type == FUNCTION) {

        symbol_t *sym = malloc(sizeof(symbol_t));
        *sym = (symbol_t) {
            .name = root->children[0]->data,
            .node = root,
            .seq = func_seq++,
            .type = SYM_FUNCTION
        };



        // Find parameter names
        int param_seq = 0;
        tlhash_t *local_names = malloc(sizeof(tlhash_t));
        tlhash_init(local_names, 32);
        for (int i = 0; i < root->n_children; i++) {
            if (root->children[i] != NULL && root->children[i]->type == VARIABLE_LIST) {
                for (int j = 0; j < root->children[i]->n_children; j++) {
                    symbol_t *local_sym = malloc(sizeof(symbol_t));
                    *local_sym = (symbol_t) {
                        .name = root->children[i]->children[j]->data,
                        .type = SYM_PARAMETER,
                        .node = root,
                        .seq = param_seq++
                    };
                    tlhash_insert(local_names, local_sym->name, strlen(local_sym->name)+1, local_sym);
                }
            }
        }
        sym->nparms = param_seq;
        sym->locals = local_names;
        tlhash_insert(global_names, sym->name, strlen(sym->name)+1, sym);
        return;
    }

    else if (root->type == IDENTIFIER_DATA) {
        symbol_t *sym = malloc(sizeof(symbol_t));
        *sym = (symbol_t) {
            .name = root->data,
            .type = SYM_GLOBAL_VAR
        };
        tlhash_insert(global_names, sym->name, strlen(sym->name)+1, sym);
    }

    // Recurse further down the tree
    for (int i = 0; i < root->n_children; i++)
    {
        find_globals_rec(root->children[i]);
    }
}

void find_globals ( void )
{
    global_names = malloc ( sizeof(tlhash_t) );
    tlhash_init ( global_names, 32 );
    for (int i = 0; i < root->n_children; i++) {
        find_globals_rec(root->children[i]);
    }
}

void
find_locals(symbol_t *function, node_t *root)
{
    if (root->type == BLOCK || root->type == DECLARATION_LIST ||
            root->type == DECLARATION || root->type == VARIABLE_LIST) {
        for (int i = 0; i < root->n_children; i++) {
            find_locals(function, root->children[i]);
        }
    } else if (root->type == IDENTIFIER_DATA) {
        symbol_t *sym = malloc(sizeof(symbol_t));
        *sym = (symbol_t) {
            .name = root->data,
            .type = SYM_LOCAL_VAR,
            .node = function->node,
            .seq = tlhash_size(function->locals) - function->nparms
        };

        symbol_t *scope_sym = malloc(sizeof(symbol_t));
        scope_sym->name = local_keys[tlhash_size(function->locals)];

        tlhash_insert(scope_list[scope-1], root->data, strlen(root->data)+1, scope_sym);

        tlhash_insert(function->locals, local_keys[tlhash_size(function->locals)], strlen(local_keys[tlhash_size(function->locals)])+1, sym);
    }
}

symbol_t * local_lookup(symbol_t *function, node_t *root)
{
    symbol_t * lookup = NULL;
    for (int i = scope-1; i >= 0; i--) {
        tlhash_lookup(scope_list[i], root->data, strlen(root->data)+1, (void **)&lookup);
        if (lookup != NULL) {
            break;
        }
    }
    if (lookup != NULL) {
        tlhash_lookup(function->locals, lookup->name, strlen(lookup->name)+1, (void **)&lookup);
        return lookup;
    }
    else {
        tlhash_lookup(function->locals, root->data, strlen(root->data)+1, (void **)&lookup);
        return lookup;
    }
}

void bind_names_rec(symbol_t *function, node_t *root)
{
    if (root->type == STRING_DATA) {
        string_list = realloc(string_list, sizeof(char *) * stringc+1);
        string_list[stringc] = root->data;
        root->data = malloc(sizeof(size_t *));
        memcpy(root->data, &stringc, sizeof(size_t *));
        stringc++;
    }
    if (root->type == FUNCTION) {
        for (int i = 0; i < root->n_children; i++) {
            if (root->children[i] == NULL)
                continue;
            else if (root->children[i]->type == BLOCK) {
                root = root->children[i];
                break;
            }
        }
    }
    if (root->type == BLOCK) {
        scope_list = realloc(scope_list, sizeof(symbol_t *) * scope+1);
        tlhash_t * new_scope = malloc(sizeof(tlhash_t));
        tlhash_init(new_scope, 32);
        scope_list[scope] = new_scope;
        scope++;

        find_locals(function, root);
    }
    if (root->type == IDENTIFIER_DATA) {
        symbol_t *lookup = NULL;
        lookup = local_lookup(function, root);
        if (lookup == NULL) {
            tlhash_lookup(global_names, root->data, strlen(root->data)+1, (void **)&lookup);
            if (lookup == NULL) {
            }
            else
                root->entry = lookup;
        } else {
            root->entry = lookup;
        }
    }
    if (root->type == DECLARATION_LIST)
        return;
    for (int i = 0; i < root->n_children; i++)
        bind_names_rec(function, root->children[i]);


    if (root->type == BLOCK) {
        scope--;
    }
}

void
bind_names ( symbol_t *function, node_t *root)
{
    scope = 0;
    bind_names_rec(function, root);
}

void
destroy_symtab ( void )
{
    // Free locals
    size_t sz = tlhash_size(global_names);
    char *keys[sz];
    tlhash_keys(global_names, (void **)keys);
    for (int i = 0; i<sz; i++) {
        symbol_t *lookup = NULL;
        tlhash_lookup(global_names, keys[i], strlen(keys[i])+1, (void**)&lookup);
        if (lookup->locals != NULL)
            tlhash_finalize(lookup->locals);
            free(lookup->locals);
    }
    tlhash_finalize ( global_names );
    free ( global_names );
    free (string_list);
    free (scope_list);
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
        if ( string_index < stringc )
            printf ( "Linked string %zu\n", *((size_t *)root->data) );
        else
            printf ( "(Not an indexed string)\n" );
    }
    for ( size_t c=0; c<root->n_children; c++ )
        print_bindings ( root->children[c] );
}
