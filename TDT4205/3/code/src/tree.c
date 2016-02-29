#include <vslc.h>


void
node_print ( node_t *root, int nesting )
{
    if ( root != NULL )
    {
        printf ( "%*c%s", nesting, ' ', node_string[root->type] );
        if ( root->type == IDENTIFIER_DATA ||
             root->type == STRING_DATA ||
             root->type == RELATION ||
             root->type == EXPRESSION ) 
            printf ( "(%s)", (char *) root->data );
        else if ( root->type == NUMBER_DATA )
            printf ( "(%ld)", *((int64_t *)root->data) );
        putchar ( '\n' );
        for ( int64_t i=0; i<root->n_children; i++ )
            node_print ( root->children[i], nesting+1 );
    }
    else
        printf ( "%*c%p\n", nesting, ' ', root );
}


void
node_init (node_t *nd, node_index_t type, void *data, uint64_t n_children, ...)
{
    va_list child_list;
    *nd = (node_t) {
        .type = type,
        .data = data,
        .entry = NULL,
        .n_children = n_children,
        .children = (node_t **) malloc ( n_children * sizeof(node_t *) )
    };
    va_start ( child_list, n_children );
    for ( uint64_t i=0; i<n_children; i++ )
        nd->children[i] = va_arg ( child_list, node_t * );
    va_end ( child_list );
}


void
node_finalize ( node_t *discard )
{
    if ( discard != NULL )
    {
        free ( discard->data );
        free ( discard->children );
        free ( discard );
    }
}


void
destroy_subtree ( node_t *discard )
{
    if ( discard != NULL )
    {
        for ( uint64_t i=0; i<discard->n_children; i++ )
            destroy_subtree ( discard->children[i] );
        node_finalize ( discard );
    }
}

void remove_null_nodes(node_t *root, int child_index, node_t *child)
{
    if (child == NULL)
        return;
    if (child->n_children == 1 && child->data == NULL &&
            (child->type == IDENTIFIER_DATA ||
             child->type == STRING_DATA ||
             child->type == RELATION ||
             child->type == EXPRESSION ||
             child->type == STATEMENT ||
             child->type == ARGUMENT_LIST ||
             child->type == PRINT_ITEM ||
             child->type == PRINT_LIST ||
             child->type == GLOBAL ||
             child->type == NUMBER_DATA ||
             child->type == PARAMETER_LIST ))
    {
        root->children[child_index] = child->children[0];
    }
}

void simplify_expressions(node_t *root)
{
    if (root->n_children == 1 && root->type == EXPRESSION && root->children[0]->type == NUMBER_DATA) {
        if ( *((char *) root->data) == '-') {
            int64_t * val = malloc(sizeof(int64_t));
            *val = - *((int64_t *)root->children[0]->data);
            root->type = NUMBER_DATA;
            root->data = val;
            root->n_children = 0;
            node_finalize(root->children[0]);
        }
    }
    else if (root->type == EXPRESSION && root->n_children == 2 && root->children[0]->type == NUMBER_DATA && root->children[1]->type == NUMBER_DATA)
    {
        int64_t a =  *((int64_t *)root->children[0]->data);
        int64_t b =  *((int64_t *)root->children[1]->data);
        int64_t * value = malloc(sizeof(int64_t));

        if ( *((char *) root->data) == '+') {
            *value = a+b;
            root->type = NUMBER_DATA;
            root->data = value;
            root->n_children = 0;
            node_finalize(root->children[0]);
            node_finalize(root->children[1]);
        }
        if ( *((char *) root->data) == '-') {
            *value = a-b;
            root->type = NUMBER_DATA;
            root->data = value;
            root->n_children = 0;
            node_finalize(root->children[0]);
            node_finalize(root->children[1]);
        }
        else if ( *((char *) root->data) == '*') {
            *value = a*b;
            root->type = NUMBER_DATA;
            root->data = value;
            root->n_children = 0;
            node_finalize(root->children[0]);
            node_finalize(root->children[1]);
        }
        else if ( *((char *) root->data) == '/') {
            *value = a/b;
            root->type = NUMBER_DATA;
            root->data = value;
            root->n_children = 0;
            node_finalize(root->children[0]);
            node_finalize(root->children[1]);
        }
    }
}

void flatten_list(node_t *root)
{
    if (root->children[0]->n_children == 1)
        root->children[0] = root->children[0]->children[0];
    else if (root->children[0]->n_children > 1)
    {
        node_t * child = root->children[0];
        int new_size = root->n_children + child->n_children-1;

        root->children = realloc(root->children, new_size * sizeof(node_t *));


        // Shift up
        for (int j = new_size-1; j > root->n_children-1; j--)
        {
           root->children[j] = root->children[j-new_size + root->n_children]; 
        }

        for (int j = 0; j < child->n_children; j++)
        {
            if (child->children[j] == NULL)
                printf("error\n");
            else
            {
                root->children[j] = child->children[j];
            }
        }
        root->n_children = new_size;
    }
}

bool same_type(node_t * root)
{
    return (root->type == root->children[0]->type ||
            (root->type == PRINT_STATEMENT && root->children[0]->type == PRINT_LIST));
}


void
simplify_tree ( node_t **simplified, node_t *root )
{
    if (root == NULL)
        return;
    for (int i = 0; i < root->n_children; i++)
    {
        node_t * child = root->children[i];
        remove_null_nodes(root, i, child);

        simplify_tree(&root->children[i], root->children[i]);

        if (root->type == EXPRESSION) {
            simplify_expressions(root);
        }
        else if (same_type(root)) {
            flatten_list(root);
        }
    }
}
