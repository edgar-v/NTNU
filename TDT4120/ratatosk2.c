#include<stdio.h>

struct node
{
    struct node * childs[10];
    int ratatosk;
};

void main()
{   
    char type[4];
    int nNodes;
    struct node nodes[1000];
    int i;
    scanf("%s", type);
    scanf("%d", &nNodes);
    for (i = 0; i <= nNodes; i++)
    {
        struct node newNode;
        newNode.ratatosk = 0;
        nodes[i] = newNode;
    }
    int startNode;
    int rataNode;
    scanf("%d", &startNode);
    scanf("%d", &rataNode);
    nodes[rataNode].ratatosk = 1;
    printf("%d\n", nNodes);
}
