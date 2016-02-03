#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include <ctype.h>

#include "pencil.h"


char transition_chars[10][3] = {
    {'t', 'm', 'd'},
    {'u', '\0', '\0'},
    {'r', '\0', '\0'},
    {'n', '\0', '\0'},
    {'o', '\0', '\0'},
    {'b', '\0', '\0'},
    {'e', '\0', '\0'},
    {'r', '\0', '\0'},
    {'a', '\0', '\0'},
    {'w', '\0', '\0'}
};

int transition_index[10][3] = {
    {1, 4, 7},
    {2, 0, 0},
    {3, 0, 0},
    {10, 0, 0},
    {5, 0, 0},
    {6, 0, 0},
    {10, 0, 0},
    {8, 0, 0},
    {9, 0, 0},
    {10, 0, 0}
};
/*
 * This function is called before anything else, to initialize the
 * state machine. It is certainly possible to create implementations
 * which don't require any initialization, so just leave this blank if
 * you don't need it.
 */
void init_transtab ( void )
{
}


/*
 * Return the next token from reading the given input stream.
 * The words to be recognized are 'turn', 'draw' and 'move',
 * while the returned tokens may be TURN, DRAW, MOVE or END (as
 * enumerated in 'pencil.h').
 */
command_t next ( FILE *input )
{
    int index = 0;
    int next_char;
    do {
        next_char = fgetc(input);
        int in_table = 0;
        for (int i = 0; i < 3; ++i) {
            if (transition_chars[index][i] == next_char) {
                index = transition_index[index][i];
                in_table = 1;
                break;
            }
        }
        if (in_table == 0)
            index = 0;

        if (index == 10) {
            switch (next_char) {
                case 'n':
                    return TURN;
                    break;
                case 'e':
                    return MOVE;
                    break;
                case 'w':
                    return DRAW;
            }
        }

    } while (next_char != EOF);
    return END;
}
