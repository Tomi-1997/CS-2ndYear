#include "graph.h"
#include <stdio.h>
#include <stdlib.h>


#define debug 0

int main(int argc, char* argv[])
{
	node* head = NULL;
	char current = 0;
	int run = 1;
	
	//current = getc(stdin);
	run = scanf(" %s",  &current);
	while ( run )
	{
		if (run == EOF || current == '\n' || current == 0 || current == ' ')
			break;

		switch (current)
		{
			case 'A':
				if (debug) 
					puts("deleting previous graph if any");
				deleteGraph_cmd(&head);
				if (debug)
					puts("Building graph");
				current = build_graph_cmd(&head);

				if (debug)
					printGraph_cmd(head);
				break;

			case 'B':
				if (debug)
					puts("Inserting or updating new node");
				current = B_cmd(&head);

				if (debug)
					printGraph_cmd(head);

				if (current == 0)
					run = scanf(" %c", &current);
				break;

			case 'D':
				if (debug)
					puts("Insert node to delete:");
				current = D_cmd(&head);
				if (debug)
					printGraph_cmd(head);

				if (current == 0)
					run = scanf(" %c", &current);
				break;

			case 'S':
				if (debug)
					puts("Calculating shortest path");

				int src,dst;
				if (scanf("%d %d", &src,&dst) == 2)
				{
					printf("Dijsktra shortest path: %d \n" 
							,shortsPath_cmd(head, src, dst));
				}
				run = scanf(" %c", &current);
				break;

			case 'T':
				if (debug)
					puts("Calculating TSP");
				current = TSP_cmd(head);
				run = scanf(" %c", &current);
				break;

			case 'X':
				printGraph_cmd(head);
				scanf(" %c", &current);
				break;

			default:
				run = 0;
				break;
		}
	}
	deleteGraph_cmd(&head);
//	puts("reached end");
	return 0;
}
