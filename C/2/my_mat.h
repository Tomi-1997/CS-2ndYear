extern int a[10][10];

// This function will init A0, A1, ... A9 for which A9 will hold the shortest path between i and j
int init_array_of_mat();
int min(int x, int y);
int input_array();
int print_array();
int is_reachable(int i, int j);
int shortest_path(int i, int j);
