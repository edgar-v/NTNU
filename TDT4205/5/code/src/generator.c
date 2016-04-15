#include "vslc.h"

#define MIN(a,b) (((a)<(b)) ? (a):(b))
static const char *record[6] = {
    "%rdi", "%rsi", "%rdx", "%rcx", "%r8", "%r9"
};


static void
generate_stringtable ( void )
{
    /* These can be used to emit numbers, strings and a run-time
     * error msg. from main
     */ 
    puts ( ".section .rodata" );
    puts ( "intout: .string \"\%ld \"" );
    puts ( "strout: .string \"\%s \"" );
    puts ( "errout: .string \"Wrong number of arguments\"" );

    /* TODO:  handle the strings from the program */
    for (int i = 0; i < stringc; i++) {
        printf("STR%d: .string %s\n", i, string_list[i]);
    }
}

static void
generate_globals(void)
{
    puts(".section .data");
    size_t n_globals = tlhash_size(global_names);
    symbol_t *global_list[n_globals];
    tlhash_values(global_names, (void **)&global_list);
    for (size_t i=0; i < n_globals; i++)
        if (global_list[i]->type == SYM_GLOBAL_VAR)
            printf("_%s: .zero 8\n", global_list[i]->name);
}

static void generate_identifier(node_t *identifier)
{
    symbol_t *symbol = identifier->entry;
    switch (symbol->type)
    {
        case SYM_GLOBAL_VAR:
            printf("_%s", symbol->name);
            break;
        case SYM_PARAMETER:
            if (symbol->seq > 5)
                printf("%ld(%%rbp)", 8+8*(symbol->seq-5));
            else
                printf("%ld(%%rbp)", -8*(symbol->seq+1));
            break;
    }
}

static void generate_expression(node_t *expr)
{
    if (expr->type == IDENTIFIER_DATA)
    {
        printf("\tmovq\t");
        generate_identifier(expr);
        printf(", %%rax\n");
    }
    else if (expr->type == NUMBER_DATA)
    {
        printf("\tmovq\t$%ld, %%rax\n", *(int64_t *)expr->data);
    }
    else if (expr->n_children == 1)
    {
        generate_expression(expr->children[0]);
    }
    else if (expr->n_children == 2)
    {
        if (expr->data != NULL)
        {
            switch ( *((char *)expr->data))
            {
                case '+':
                    generate_expression(expr->children[0]);
                    printf("\tpushq %%rax\n");
                    generate_expression(expr->children[1]);
                    printf("\taddq %%rax, (%%rsp)\n");
                    printf("\tpopq %%rax\n");
                    break;
                case '-':
                    generate_expression(expr->children[0]);
                    printf("\tpushq %%rax\n");
                    generate_expression(expr->children[1]);
                    printf("\tsubq %%rax, (%%rsp)\n");
                    printf("\tpopq %%rax\n");
                    break;
                case '*':
                    printf("\tpushq %%rdx\n");
                    generate_expression(expr->children[1]);
                    printf("\tpushq %%rax\n");
                    generate_expression(expr->children[0]);
                    printf("\tcqo\n");
                    printf("\timulq (%%rsp)\n");
                    printf("\tpopq %%rdx\n");
                    printf("\tpopq %%rdx\n");
                    break;
                case '/':
                    printf("\tpushq %%rdx\n");
                    generate_expression(expr->children[1]);
                    printf("\tpushq %%rax\n");
                    generate_expression(expr->children[0]);
                    printf("\tcqo\n");
                    printf("\tidivq (%%rsp)\n");
                    printf("\tpopq %%rdx\n");
                    printf("\tpopq %%rdx\n");
                    break;

            }
        }
    }
}


static void gen_assignment_statement(node_t *statement)
{
    generate_expression(statement->children[1]);
    printf("\tmovq\t%%rax, ");
    generate_identifier(statement->children[0]);
    printf("\n");
}

static void gen_print_statement(node_t *print)
{
    for (int i = 0; i < print->n_children; i++)
    {
        node_t *item = print->children[i];
        switch (item->type)
        {
            case STRING_DATA:
                printf("\tmovq\t$STR%i, %%rsi\n", *((size_t *)item->data));
                printf("\tmovq $strout, %%rdi\n");
                printf("\tcall printf\n");
                break;
            case NUMBER_DATA:
                printf("\tmovq\t$%ld, %%rsi\n", *((int64_t *)item->data));
                printf("\tmovq $intout, %%rdi\n");
                printf("\tcall printf\n");
                break;
            case IDENTIFIER_DATA:
                printf("\tmovq\t");
                generate_identifier(item);
                printf(", %%rsi\n");
                printf("\tmovq $intout, %%rdi\n");
                printf("\tcall printf\n");
                break;
            case EXPRESSION:
                generate_expression(item);
                printf("\tmovq %%rax, %%rsi\n");
                printf("\tmovq $intout, %%rdi\n");
                printf("\tcall printf\n");
                break;
        }
    }
    printf("\tmovq $\'\\n\', %%rdi\n");
    printf("\tcall putchar\n");
}

static void generate_node(node_t *node)
{
    switch(node->type)
    {
        case ASSIGNMENT_STATEMENT:
            gen_assignment_statement(node);
            break;
        case PRINT_STATEMENT:
            gen_print_statement(node);
            break;
        case RETURN_STATEMENT:
            generate_expression(node->children[0]);
            printf("\tleave\n");
            printf("\tret\n");
            break;
        default:
            for (int i = 0; i < node->n_children; i++)
                generate_node(node->children[i]);
            break;
    }
}

static void
generate_function(symbol_t *function)
{
    printf("_%s:\n", function->name);
    puts ( "\tpushq %rbp" );
    puts ( "\tmovq %rsp, %rbp" );

    for (size_t arg=1; arg<=MIN(6, function->nparms); arg++)
        printf("\tpushq\t%s\n", record[arg-1]);

    if ((tlhash_size(function->locals)&1) == 1)
        puts("\tpushq\t$0");
    generate_node(function->node);
}


static void
generate_main ( symbol_t *first )
{
    puts ( ".globl main" );
    puts ( ".section .text" );
    puts ( "main:" );
    puts ( "\tpushq %rbp" );
    puts ( "\tmovq %rsp, %rbp" );

    puts ( "\tsubq $1, %rdi" );
    printf ( "\tcmpq\t$%zu,%%rdi\n", first->nparms );
    puts ( "\tjne ABORT" );
    puts ( "\tcmpq $0, %rdi" );
    puts ( "\tjz SKIP_ARGS" );

    puts ( "\tmovq %rdi, %rcx" );
    printf ( "\taddq $%zu, %%rsi\n", 8*first->nparms );
    puts ( "PARSE_ARGV:" );
    puts ( "\tpushq %rcx" );
    puts ( "\tpushq %rsi" );

    puts ( "\tmovq (%rsi), %rdi" );
    puts ( "\tmovq $0, %rsi" );
    puts ( "\tmovq $10, %rdx" );
    puts ( "\tcall strtol" );

    /*  Now a new argument is an integer in rax */
    puts ( "\tpopq %rsi" );
    puts ( "\tpopq %rcx" );
    puts ( "\tpushq %rax" );
    puts ( "\tsubq $8, %rsi" );
    puts ( "\tloop PARSE_ARGV" );

    /* Now the arguments are in order on stack */
    for ( int arg=0; arg<MIN(6,first->nparms); arg++ )
        printf ( "\tpopq\t%s\n", record[arg] );

    puts ( "SKIP_ARGS:" );
    printf ( "\tcall\t_%s\n", first->name );
    puts ( "\tjmp END" );
    puts ( "ABORT:" );
    puts ( "\tmovq %rdi, %rsi");
    puts ( "\tmovq $intout, %rdi" );
    puts ( "\tmovq $0, %rax");
    puts ( "\tcall printf" );

    puts ( "END:" );
    puts ( "\tmovq %rax, %rdi" );
    puts ( "\tcall exit" );
}


void
generate_program ( void )
{
    generate_stringtable();
    generate_globals();

    size_t n_globals = tlhash_size(global_names);
    symbol_t *global_list[n_globals];
    tlhash_values(global_names, (void **)&global_list);
    symbol_t *first;
    for (size_t i=0; i < n_globals; i++) {
        if (global_list[i]->type == SYM_FUNCTION) {
            first = global_list[i];
            break;
        }
    }

    generate_main(first);
    for (size_t i=0; i < n_globals; i++) {
        if (global_list[i]->type == SYM_FUNCTION) {
            generate_function(global_list[i]);
        }
    }

}
