#include "graph.h"
#include <stdlib.h>
#include <stddef.h>
#include <stdio.h>
#include <limits.h>
#include <stdbool.h>

#define debug 0

int tsp_answer = -1;

// Typed A
char build_graph_cmd(pnode *head)
{
	int num_of_nodes = 0;
	scanf("%d", &num_of_nodes);


	// INIT NODES
	pnode* temp = head;
	for (int i = 0;i < num_of_nodes; ++i)
	{
		node* new_node = (node*) malloc (sizeof(node));
		if (new_node == NULL)
		{
			puts("Malloc failure at build graph");
			return -1;
		}

		new_node->node_num = i;
		new_node->edges = NULL;
		new_node->next = NULL;

		*temp = new_node;
		temp = &new_node->next;
	}
	char c = 0;
	int source = -1;
	int dest = -1;
	int weight = -1;
	// SCANF EDGES
	
	scanf(" %c", &c);
	while (c == 'n')
	{
		// TRY TO SCAN INTEGER, IF SCAN IS CHARACTER != n, WE ARE DONE HERE
		// IF IT IS N, WE WORK ON NEW NODE
		if (scanf(" %d", &source) == 1)
		{
			while (1) 
			{
				if (scanf(" %d", &dest) == 0)
				{
					c = getchar();
					break;
				}

				if (scanf(" %d", &weight) == 0)
				{
					c = getchar();
					break;
				}

				if (source == dest)
					continue;
	
				insertEdge(&find(head,source)->edges, dest, weight);
			}
		}
		else
		{
			c = getchar();
		}
	}
	// RETURN INPUT BUFFER if it is not n
	return c;
}

// INPUT NEW NODE - IF ALREADY EXISTS, RESET IT
char B_cmd(pnode* head)
{
	int src = -1, dest = -1, weight = -1;
	if (scanf("%d", &src) == 0)
		return getchar();

	if (debug)
		printf("updating node %d\n", src);

	int found = 0;
	pnode temp = *head;
	while (temp != NULL)
	{	
		// FIRST CLEAR ALL OUTGOING EDGES
		if (temp->node_num == src)
		{
			found = 1;
			clearEdges(&temp->edges);
			// THEN INSERT NEW EDGES
			while(1)
			{
				if (scanf("%d",&dest) == 0)
					return getchar();
				if (scanf("%d", &weight) == 0)
					return getchar();
				insertEdge(&temp->edges,dest,weight);
			}
		}
		temp = temp->next;
	}

	if (found == 0)
	{
		node* newNode = (node*) malloc (sizeof(node));
		if (newNode == NULL)
			return -1;

		newNode->node_num = src;
		newNode->edges = NULL;
		newNode->next = NULL;
		
		newNode->next = *head;
		*head = newNode;

		while(1)
		{
			if (scanf("%d", &dest) == 0)
				return getchar();
			if (scanf("%d", &weight) == 0)
				return getchar();
			insertEdge(&(newNode->edges),dest,weight);
		}
	}

	return getchar();
}

char D_cmd(node** head)
{
	int src = -1;
	if (scanf("%d", &src) == 0)
		return getchar();

	node* temp = *head;
	while (temp != NULL)
	{
		// DELETE ALL INGOING EDGES
		if (temp->node_num != src && temp->edges != NULL)
		{
			edge* prev = temp->edges;
			edge* current = prev->next;

			while(current != NULL)
			{
				if (current->destination == src)
				{
					prev->next = current->next;
					free(current);
				}
				else
				{
					prev = prev->next;
				}
				current = prev->next;
			}
			current = temp->edges;
			if (current != NULL && current->destination == src)
			{
				delFirstEdge(&temp->edges);
			}
		}
		// DELETE ALL OUTGOING EDGES
		else
			clearEdges(&temp->edges);
		temp = temp->next;
	}
	// DEL NODE
	node* prevNode = *head;
	node* currentNode = prevNode->next;

	while (currentNode != NULL)
	{
		if (currentNode->node_num == src)
		{
			prevNode->next = currentNode->next;
			free(currentNode);
		}
		else
		{
			prevNode = prevNode->next;
		}
		currentNode = prevNode->next;
	}
	
	if (*head != NULL && (*head)->node_num == src)
		{
		deleteFirst(head);
		}
	return 0;
}

void delFirstEdge(edge** head)
{
	if (*head == NULL) 
		return;
	edge* temp = *head;
	*head = temp->next;
	free(temp);
}

// DEL ALL OUTGOING
void clearEdges(edge** head)
{
	while (*head != NULL)
		delFirstEdge(head);
}
void insertEdge(edge** edge_head, int d, int w)
{
	edge* e = (edge*) malloc(sizeof(edge));
	if (e == NULL)
		{
			puts("Malloc failure at edge insertion");
			return;
		}


	e->weight = w;
	e->destination = d;
	e->next = (*edge_head);

	(*edge_head) = e;

	if (debug)
		puts("built edge!");

}

void deleteFirst(node** head)
{	
	if (*head == NULL)
		return;
	node* temp = *head;
	*head = temp->next;
	free(temp);
}

void clear(node** head)
{
	while (*head != NULL)
		deleteFirst(head);
	return;
}

// finds node by ID and returns a pointer to it.
// if not found, creates one and returns a pointer to it
pnode find(node** head, int id)
{
	pnode current = *head;

	if (current != NULL && current->node_num == id)
		return  current;

	if (current != NULL)
	while (current->next != NULL)
	{
		if (current->next->node_num == id)
			return  current->next;

		current = current->next;
	}

	node* new_node = (node*) malloc (sizeof(node));
	if (new_node == NULL)
	{
		puts("Malloc failure at find");
		return 0;
	}

	new_node->node_num = id;
	new_node->edges = NULL;
	new_node->next = NULL;

	if (current == NULL)
		*head = new_node;
	else
		current->next = new_node;

	return new_node;
}



void deleteAt_byID(node** head, int id)
{
	// 0 Elements
	if (*head == NULL)
		return;

	node* prev = *head;
	node* current = prev->next;

	// 1 Element
	if (current == NULL)
	{
		if (prev->node_num == id)
			deleteFirst(head);
		return;
	}

	// Atleast 2 elements
	while (current != NULL)
	{
		if (current->node_num == id)
		{
			prev->next = current->next;
			free(current);
		}
		else
		{
			prev = prev->next;
			current = current->next;
		}

	}
	return;
}
void insert_node_cmd(pnode *head)
{

}

void delete_node_cmd(pnode *head)
{

}

void printNodes_cmd(pnode head)
{
	node* temp = head;
	printf("\n[");
	while (temp != NULL)
	{	
		printf("%d",temp->node_num);
		temp = temp->next;
		if (temp != NULL)
			printf(" --> ");
	}
	printf("]\n");
}

void printGraph_cmd(pnode head)
{
	printNodes_cmd(head);
	printEdges_cmd(head);
}

void printEdges_cmd(pnode head)
{
	node* temp = head;
	while (temp != NULL)
	{
		printf("\n\nNode %d has outgoing edges to:\n", temp->node_num);
		pedge temp_edges = temp->edges;
		while (temp_edges != NULL)
		{
			printf("Edge to %d with weight %d\n", temp_edges->destination, temp_edges->weight);
			temp_edges = temp_edges->next;
		}
		temp = temp->next;
	}
}

void deleteGraph_cmd(pnode* head)
{
	node* temp = *head;
	while (temp != NULL)
	{
		clearEdges(&temp->edges);
		temp = temp->next;
	}
	clear(head);
}

// actual amount of nodes in graph
int len(node* head)
{
	if (head == NULL)
		return 0;
	return 1 + len(head->next);
}

// returns the id of the node with highest id number
int maxId(node* head)
{
	if (head == NULL)
		return 0;
	int max = head->node_num;
	head = head->next;

	while(head != NULL)
	{
		if (head->node_num > max)
			max = head->node_num;
		head = head->next;
	}
	return max + 1;
}

int shortsPath_cmd(pnode head, int src, int dst)
{
	int max_num = maxId(head);
	int node_num = len(head);

	if (max_num > node_num)
		node_num = max_num;

	int* parents = (int*) malloc (sizeof(int) * node_num);
	if (parents == NULL)
	{
		puts("shortest path malloc failure");
		return -1;
	}
	int* travel_cost = (int*) malloc (sizeof(int) * node_num);
	if (travel_cost == NULL)
	{
		puts("shortest path malloc failure");
		free(parents);
		return -1;
	}
	bool* is_visited = (bool*) malloc(sizeof(bool) * node_num);
	if (is_visited == NULL)
	{
		puts("shortest path malloc failure");
		free(parents);
		free(travel_cost);
		return -1;
	}
	
	for (int i = 0; i < node_num; ++i)
	{
		parents[i] = -1;
		travel_cost[i] = INT_MAX;
		is_visited[i] = false;
	}
	travel_cost[src] = 0;
	
	//node* currNode = find(&head, src);
	node* currNode = NULL;

	// find src, if not found return -1
	node* temp = head;
	while (temp != NULL)
	{
		if (temp->node_num == src)
		{
			currNode = temp;
			break;
		}
		temp = temp->next;
	}
	if (currNode == NULL)
		{
			free(parents);
			free(travel_cost);
			free(is_visited);
			return -1;
		}

	edge* currEdgeList = currNode->edges;

	while (1)
	{
		int s = currNode->node_num;
		edge* temp_edge = currEdgeList;

		// Relax each edge coming out of current node
		while (temp_edge != NULL)
		{
			int d = temp_edge->destination;
			int w = temp_edge->weight;

			if (travel_cost[d] == INT_MAX)
				{
					travel_cost[d] = travel_cost[s] + w;
					parents[d] = s;
				}

			else if (travel_cost[s] + w < travel_cost[d])
				{
					travel_cost[d] = travel_cost[s] + w;
					parents[d] = s;		
				}
			temp_edge = temp_edge->next;
		}

		// set node to visited and find minimal next node which is not visited
		is_visited[s] = true;

		if (debug)
		{
			printf("curr node %d with edges:\n", currNode->node_num);
			for(int i= 0;i<node_num; ++i)
				if (travel_cost[i] != INT_MAX && i != s)
					printf("to %d with cost of %d \n", i, travel_cost[i]);
			printf("\n");
		}	

		// Continue relaxtion with minimal node that is connected to current
		int next = -1;
		for(int i = 0;i<node_num; ++i)
		{
			if (travel_cost[i] != INT_MAX && is_visited[i] == false)
			{
				if (next == -1)
					next = i;
				if (travel_cost[i] < travel_cost[next])
					next = i;
			}
		}

		if (next == -1)
			break;

//		currNode = find(&head, next);
		temp = head;
		while (head != NULL)
			{		
				if (temp->node_num == next)
				{
					currNode = temp;
					break;
				}
				temp = temp->next;
			}
		if (currNode == NULL)
			break;
		currEdgeList = currNode->edges;
	}

	int answer = -1;
	if (travel_cost[dst] < INT_MAX) 
	{
		answer = travel_cost[dst];
	}

	free(parents);
	free(travel_cost);
	free(is_visited);
	return answer;
}

int min(int x, int y)
{
	if (y == -1)
		return x;
	if (x == -1) 
		return y;
	if (x < y)
		return x;
	return y;
}

void swap(int* a,int i,int j)
{
	int temp = a[i];
	a[i] = a[j];
	a[j] = temp;
}
void print(int* a, int length)
{
	printf("Current perm:");
	for (int i = 0; i < length; i++)
		       printf("%d ", a[i]);
	puts("");
		       
}
int path(node* head, int* a, int length)
{
	int sum = 0;
	for(int i = 0; i < length - 1; i++)
	{
		int curr = shortsPath_cmd(head, a[i], a[i+1]);
		if (curr < 0)
			return -1;
		sum = sum + curr;
	}
	return sum;
}
int perm(node* head, int* cities, int start, int end)
{
	if (start == end)
	{
		if (debug)
		{
			print(cities, end);
			printf("current sp: %d \n", path(head, cities, end));
		}
		tsp_answer = min(path(head, cities, end), tsp_answer);
		return 0;
	}

	for(int i = start; i < end ; i++)
	{
		swap(cities, i , start);
		perm(head, cities, start + 1, end);
		swap(cities, i, start);
	}
}

char TSP_cmd(pnode head)
{
	tsp_answer = -1;
	int k = 0;
	if (scanf("%d", &k) != 1) 
		return getchar();

	if (k > 9)
		return 0;

	int* cities = (int*) malloc (sizeof(int)*k);
	if (cities == NULL)
	{
		puts("malloc failure at TSP");
		return 0;
	}

	// scan cities \ nodes to travel to
	for(int i = 0; i < k; ++i)
		scanf("%d", &cities[i]);

	perm(head, cities, 0, k);
	int ans = tsp_answer;
	printf("TSP shortest path: %d \n", ans);
	free (cities);
	return 0;
}
