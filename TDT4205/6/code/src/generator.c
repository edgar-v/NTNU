#include "vslc.h"

#define MIN(a,b) (((a)<(b)) ? (a):(b))
#define RECUR(nd) do { \
    for ( size_t i=0; i<(nd)->n_children; i++ ) \
        generate_node ( (nd)->children[i] );    \
} while ( false )

#define LBL(name) puts ( #name":" )
#define ASM0(op) puts ( "\t"#op )
#define ASM1(op,a) puts ( "\t"#op"\t"#a )
#define ASM2(op,a,b) puts ( "\t"#op"\t"#a", "#b )

static const char *record[6] = {
    "%rdi", "%rsi", "%rdx", "%rcx", "%r8", "%r9"
};

static void generate_node(node_t *node);
static void generate_expression(node_t *node);

symbol_t *current_function = NULL;

// Counters to append to ELSE, WHILE etc. labels
size_t if_labelc = 0;
size_t while_labelc = 0;


static void
generate_stringtable ( void )
{
    puts ( ".section .rodata" );
    puts ( "intout: .string \"\%ld \"" );
    puts ( "strout: .string \"\%s \"" );
    puts ( "errout: .string \"Wrong number of arguments\"" );
    for ( size_t s=0; s<stringc; s++ )
        printf ( "STR%zu: .string %s\n", s, string_list[s] );
}


static void
generate_global_variables ( void )
{
    puts ( ".section .data" );
    size_t nsyms = tlhash_size ( global_names );
    symbol_t *syms[nsyms];
    tlhash_values ( global_names, (void **)&syms );
    for ( size_t n=0; n<nsyms; n++ )
    {
        if ( syms[n]->type == SYM_GLOBAL_VAR )
            printf ( "_%s: .zero 8\n", syms[n]->name );
    }
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
    puts ( "\tmovq $errout, %rdi" );
    puts ( "\tcall puts" );

    puts ( "END:" );
    puts ( "\tmovq %rax, %rdi" );
    puts ( "\tcall exit" );
}


static void
generate_identifier ( node_t *ident )
{
    symbol_t *symbol = ident->entry;
    switch ( symbol->type )
    {
        case SYM_GLOBAL_VAR:
            printf ( "_%s", symbol->name );
            break;
        case SYM_PARAMETER:
            if ( symbol->seq > 5 )
                    printf ( "%ld(%%rbp)", 8+8*(symbol->seq-5) );
            else
                printf ( "%ld(%%rbp)", -8*(symbol->seq+1) );
            break;
        case SYM_LOCAL_VAR:
            printf("%ld(%%rbp)", -8*(symbol->seq+1+current_function->nparms));
            break;
        case SYM_FUNCTION:
            printf("_%s", symbol->name);
    }
}

static void generate_function_call(node_t *expr)
{
    // First six arguments go into registers
    for (size_t i=0; i<MIN(6, expr->children[1]->n_children); i++)
    {
        generate_expression(expr->children[1]->children[i]);
        printf("\tmovq\t%%rax, %s\n", record[i]);
    }

    // Remaining on the stack
    for (size_t i=6; i<expr->children[1]->n_children; i++)
    {
        generate_expression(expr->children[1]->children[i]);
        printf("\tpushq\t%%rax\n");
    }
    printf("\tcall\t");
    generate_identifier(expr->children[0]);
    printf("\n");
    // Restoration of stack is done at end of function
}


static void
generate_expression ( node_t *expr )
{
    if ( expr->type == IDENTIFIER_DATA )
    {
        printf ( "\tmovq\t" );
        generate_identifier ( expr );
        printf ( ", %%rax\n" );
    }
    else if ( expr->type == NUMBER_DATA )
    {
        printf ( "\tmovq\t$%ld, %%rax\n", *(int64_t *)expr->data );
    }
    else if ( expr->n_children == 1 )
    {
        generate_expression ( expr->children[0] );
        ASM1 ( negq, %rax );
    }
    else if ( expr->n_children == 2 )
    {
        if ( expr->data != NULL )
        {
            switch ( *((char *)expr->data) )
            {
                case '+':
                    generate_expression ( expr->children[0] );
                    ASM1 ( pushq, %rax );
                    generate_expression ( expr->children[1] );
                    ASM2 ( addq, %rax, (%rsp) );
                    ASM1 ( popq, %rax );
                    break;
                case '-':
                    generate_expression ( expr->children[0] );
                    ASM1 ( pushq, %rax );
                    generate_expression ( expr->children[1] );
                    ASM2 ( subq, %rax, (%rsp) );
                    ASM1 ( popq, %rax );
                    break;
                case '*':
                    ASM1 ( pushq, %rdx );
                    generate_expression ( expr->children[1] );
                    ASM1 ( pushq, %rax );
                    generate_expression ( expr->children[0] );
                    ASM0 ( cqo );
                    ASM1 ( imulq, (%rsp) );
                    ASM1 ( popq, %rdx );
                    ASM1 ( popq, %rdx );
                    break;
                case '/':
                    ASM1 ( pushq, %rdx );
                    generate_expression ( expr->children[1] );
                    ASM1 ( pushq, %rax );
                    generate_expression ( expr->children[0] );
                    ASM0 ( cqo );
                    ASM1 ( idivq, (%rsp) );
                    ASM1 ( popq, %rdx );
                    ASM1 ( popq, %rdx );
                    break;
            }
        }
        else
        {
            generate_function_call(expr);
        }
    }
}

static void generate_if_statement(node_t *statement)
{
    node_t *relation = statement->children[0];

    generate_expression(relation->children[0]);
    printf("\tpushq\t%%rax\n");
    generate_expression(relation->children[1]);
    printf("\tcmpq\t%%rax, (%%rsp)\n");

    switch ( *((char *)relation->data) )
    {
        case '>':
            printf("\tjng\tELSE%ld\n", if_labelc);
            break;
        case '<':
            printf("\tjnl\tELSE%ld\n", if_labelc);
            break;
        case '=':
            printf("\tjne\tELSE%ld\n", if_labelc);
            break;

    }
    size_t this_labelc = if_labelc; // Temporary labelc to use after potential nesting
    if_labelc++;
    generate_node(statement->children[1]);
    printf("\tjmp\tENDIF%ld\n", this_labelc);
    printf("\tELSE%ld:\n", this_labelc);
    if (statement->n_children > 2)
    {
        generate_node(statement->children[2]);
    }
    printf("\tENDIF%ld:\n", this_labelc);

    // Pop the result from comparison to not mess up stack
    printf("\tpopq %%rax\n");
}

static void generate_while_statement(node_t *statement)
{
    printf("\tWHILELOOP%ld:\n", while_labelc);
    node_t *relation = statement->children[0];
    generate_expression(relation->children[0]);
    printf("\tpushq\t%%rax\n");
    generate_expression(relation->children[1]);
    printf("\tcmpq\t%%rax, (%%rsp)\n");

    switch ( *((char *)relation->data) )
    {
        case '>':
            printf("\tjng\tENDWHILE%ld\n", while_labelc);
            break;
        case '<':
            printf("\tjnl\tENDWHILE%ld\n", while_labelc);
            break;
        case '=':
            printf("\tjne\tENDWHILE%ld\n", while_labelc);
            break;
        default:
            printf("unknow relation\n");
            break;

    }
    size_t this_labelc = while_labelc; // Temporary labelc to use after potential nesting
    while_labelc++;
    generate_node(statement->children[1]);
    printf("\tpopq %%rax\n");
    printf("\tjmp\tWHILELOOP%ld\n", this_labelc);
    printf("\tENDWHILE%ld:\n", this_labelc);
}

static void
generate_assignment_statement ( node_t *statement )
{
    generate_expression ( statement->children[1] );
    printf ( "\tmovq\t%%rax, " );
    generate_identifier ( statement->children[0] );
    printf ( "\n" );
}


static void
generate_print_statement ( node_t *statement )
{
    for ( size_t i=0; i<statement->n_children; i++ )
    {
        node_t *item = statement->children[i];
        switch ( item->type )
        {
            case STRING_DATA:
                printf ( "\tmovq\t$STR%zu, %%rsi\n", *((size_t *)item->data) );
                ASM2 ( movq, $strout, %rdi );
                ASM2 ( movq, $0, %rax);
                ASM1 ( call, printf );
                break;
            case NUMBER_DATA:
                printf ("\tmovq\t$%ld, %%rsi\n", *((int64_t *)item->data) );
                ASM2 ( movq, $intout, %rdi );
                ASM2 ( movq, $0, %rax);
                ASM1 ( call, printf );
                break;
            case IDENTIFIER_DATA:
                printf ( "\tmovq\t" );
                generate_identifier ( item );
                printf ( ", %%rsi\n" );
                ASM2 ( movq, $intout, %rdi );
                ASM2 ( movq, $0, %rax);
                ASM1 ( call, printf );
                break;
            case EXPRESSION:
                generate_expression ( item );
                ASM2 ( movq, %rax, %rsi );
                ASM2 ( movq, $intout, %rdi );
                ASM2 ( movq, $0, %rax);
                ASM1 ( call, printf );
                break;
        }
    }
    ASM2 ( movq, $'\n', %rdi );
    ASM1 ( call, putchar );
}

static void restore_registers()
{
    // Alignment value
    if ( (tlhash_size(current_function->locals)&1) == 1 )
        puts ( "\taddq $8, %rsp" ); // same as popq, but without saving the value

    // Local vars
    size_t n_local_vars = tlhash_size(current_function->locals) - current_function->nparms;
    for (size_t i=0; i < n_local_vars; i++)
        puts("\taddq $8, %rsp");

    // Parameters
    size_t min_parms = MIN(6, current_function->nparms);
    for ( size_t arg=min_parms; arg > 0; arg-- )
        printf ( "\tpopq\t%s\n", record[arg-1] );
}

static void
generate_node ( node_t *node )
{
    //printf("%s\n", node_string[node->type]);
    switch (node->type)
    {
        case PRINT_STATEMENT:
            generate_print_statement ( node );
            break;
        case ASSIGNMENT_STATEMENT:
            generate_assignment_statement ( node );
            break;
        case RETURN_STATEMENT:
            generate_expression ( node->children[0] );
            restore_registers();
            ASM0 ( leave );
            ASM0 ( ret );
            break;
        case IF_STATEMENT:
            generate_if_statement(node);
            break;
        case WHILE_STATEMENT:
            generate_while_statement(node);
            break;
        case NULL_STATEMENT: // CONTINUE_STATEMENT
            // We only have while-loops
            printf("\tjmp\tWHILELOOP%ld\n", while_labelc-1); 
            break;
        default:
            RECUR(node);
            break;
    }
}


static void
generate_function ( symbol_t *function )
{
    current_function = function;
    printf ( "_%s:\n", function->name );
    ASM1 ( pushq, %rbp );
    ASM2 ( movq, %rsp, %rbp );

    // Save arguments in local stack frame
    for ( size_t arg=1; arg<=MIN(6,function->nparms); arg++ )
            printf ( "\tpushq\t%s\n", record[arg-1] );
    size_t n_local_vars = tlhash_size(function->locals) - function->nparms;
    for (size_t i=0; i < n_local_vars; i++)
        ASM1(pushq, $0);
    // Align stack to 16 bytes if odd # of locals/arguments
    if ( (tlhash_size(function->locals)&1) == 1 )
        puts ( "\tpushq\t$0" );
    generate_node ( function->node );
}


void
generate_program ( void )
{
    size_t n_globals = tlhash_size(global_names);
    symbol_t *global_list[n_globals];
    tlhash_values ( global_names, (void **)&global_list );

    symbol_t *first_function;
    for ( size_t i=0; i<tlhash_size(global_names); i++ )
        if ( global_list[i]->type == SYM_FUNCTION && global_list[i]->seq == 0 )
        {
            first_function = global_list[i];
            break;
        }

    current_function = first_function;
    generate_stringtable();
    generate_global_variables();
    generate_main ( first_function );
    for ( size_t i=0; i<tlhash_size(global_names); i++ )
        if ( global_list[i]->type == SYM_FUNCTION )
            generate_function ( global_list[i] );
}
