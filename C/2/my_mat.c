#include <stdio.h>
#include <stdlib.h>
#include "my_mat.h"

#define N 10
extern int a[N][N];
int arr_matrices[N][N][N];

// Print matrice line by line mainly for testing
int print_array()
{
	for (int i = 0; i < N; i++)
	{
		for (int j = 0; j < N; j++)
		{
			printf("%d ",arr_matrices[9][i][j]);
		}
		printf("\n");
	}
	return 0;
}

// Get input from user and insert in array.
int input_array()
{
	for (int i = 0; i < N; i++)
	{
		for (int j = 0; j < N; j++)
		{
			scanf("%d", &a[i][j]);
		}
	}
	init_array_of_mat();
	return 0;
}

// Make 10 matrices, each one holds minimal path from i to j based on previous matrice
int init_array_of_mat()
{

	int i_to_k = 0;
	int k_to_j = 0;
	int indirect_path = 0;
	int direct_path = 0;
	int reachable_notd = 1;
	int reachable_d = 1;
	int last_loop = 0;

	// Let input array be A0 
	// and calculate A1 , ... A10
	for (int i = 0; i < N; i++)
	{
		for (int j = 0; j < N;j++)
		{
			arr_matrices[0][i][j] = a[i][j];
		}
	}

	// Calculate A1, A2 , ... A10
	// Based on the Floyd Warshall algorithm
	for (int k = 1; k <= N; k++)
	{
		if (last_loop == 1)
			break;

		for (int i = 0; i < N;i++)
		{
			for (int j = 0; j < N; j++)
			{

				// This k == N code block is intedned for the last loop,
				// to run it again and to check indirect path from (i,N-1) to (N-1,j)
				// if not for this if block then there would not be last check for (i,9) (9,j)
				// and some optimal paths would be wrong.
				if ( k == N || last_loop )
				{
					k = N - 1;
					i_to_k = arr_matrices[k][i][k];
					k_to_j = arr_matrices[k][k][j];
					last_loop = 1;
				}
				else
				{
					i_to_k = arr_matrices[k-1][i][k-1];
					k_to_j = arr_matrices[k-1][k-1][j];
				}
			
				indirect_path = i_to_k + k_to_j;
				direct_path = arr_matrices[k-1][i][j];

				reachable_notd = 1;
				if (i_to_k == 0 || k_to_j == 0)
					reachable_notd = 0;

				reachable_d = 1;
				if (direct_path == 0)
					reachable_d = 0;

				if ( i == j )
				{
					arr_matrices[k][i][j] = 0;
					continue;
				}

				if (reachable_d == 0 && reachable_notd == 1)
				{
					arr_matrices[k][i][j] = indirect_path;
					continue;
				}

				if (reachable_d == 1 && reachable_notd == 0)
				{
					arr_matrices[k][i][j] = direct_path;
					continue;
				}
				
				if (reachable_d == 1 && reachable_notd == 1)
				{
					arr_matrices[k][i][j] = min (direct_path , indirect_path);
				}

			}
		}
	}
	// Now shortest path between i,j should be the value at A9 at pos i.j
	return 0;
}

int min(int x, int y)
{
	if ( y > x )
		return x;
	return y;
}

int shortest_path(int i, int j)
{
	int x = arr_matrices[N-1][i][j];
	if ( x == 0 )
		return -1;
	return x;
}

int is_reachable(int i, int j)
{
	if (shortest_path(i,j) == -1 )
		return 0;
	return 1;
}
