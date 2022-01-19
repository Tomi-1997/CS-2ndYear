#ifndef GRAPH_
#define GRAPH_

typedef struct GRAPH_NODE_ *pnode;

typedef struct edge_
{
	    int weight;
	    int destination;
	    struct edge_ *next;
} edge, *pedge;


typedef struct GRAPH_NODE_
{
	    int node_num;
	    pedge edges;
    	    struct GRAPH_NODE_ *next;
} node, *pnode;

// EDGES
char D_cmd(node** head);
char B_cmd(pnode* head);
void delFirstEdge(edge** head);
void clearEdges(edge** edge_head);
void delEdge(edge** edge_head, int id, int deleteAll);
void insertEdge(edge** edge_head, int d, int w);

// NODES
void deleteFirst(node** head);
void deleteAt_byID(node** head, int id);
void clear(node** head);
pnode find(node** head, int id);

char build_graph_cmd(pnode *head);
void insert_node_cmd(pnode *head);
void delete_node_cmd(pnode *head);

// DEBUG CMD
void printGraph_cmd(pnode head);
void printNodes_cmd(pnode head);
void printEdges_cmd(pnode head);

// ALGO
int len(node* head);
int maxId(node* head);
void deleteGraph_cmd(pnode* head);
int shortsPath_cmd(pnode head, int src, int dst);

void swap(int* a, int i, int j);
int min(int x,int y);
int factorial(int k);
int perm(node* head, int* cities, int start, int end);
char TSP_cmd(pnode head);

#endif

