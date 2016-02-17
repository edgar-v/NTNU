%{
#include <vslc.h>
int yylineno;
%}
%option noyywrap
%option array
%option yylineno

%%

    /* Whitespace */
[ \n\t\r\v]+

    /* Comments */
\/\/.*

FUNC                    { return FUNC; }
BEGIN                   { return OPENBLOCK; }
END                     { return CLOSEBLOCK; }
RETURN                  { return RETURN; }
PRINT                   { return PRINT; }
CONTINUE                { return CONTINUE; }
IF                      { return IF; }
THEN                    { return THEN; }
ELSE                    { return ELSE; }
WHILE                   { return WHILE; }
DO                      { return DO; }
VAR                     { return VAR; }

    /* Strings */
\"(\\.|[^\\"])*\"       { return STRING; }

    /* Identifiers */
[a-zA-Z_][a-zA-Z0-9_]*  { return IDENTIFIER; }

    /* Digits */
[0-9]+                  { return NUMBER; }

    /* Operators */

:=                      { return ASSIGN; }
\+                      { return '+'; }
\-                      { return '-'; }
\*                      { return '*'; }
\/                      { return '/'; }
\<                      { return '<'; }
\>                      { return '>'; }
=                       { return '='; }


    /* Punctuation */
"("                     { return '('; }
")"                     { return ')'; }
"["                     { return '['; }
"]"                     { return ']'; }
","                     { return ','; }
";"                     { return ';'; }

.                       { printf("unknown: %s\n", yytext); }


%%
