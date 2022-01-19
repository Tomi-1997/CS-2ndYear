#include <stdio.h>
#include <stdlib.h>
#include "my_mat.h"

#define N 10
int a[N][N];

int main(int argc, char* argv[])
{
	int num1 = 0;
	int num2 = 0;
	// Initialize array to zeroes:
	for (int i = 0; i <N; i++)
	{
		for (int j = 0; j <N; j++)
		{
		a[i][j] = 0;	
		}
	}

	char c;
	scanf(" %c", &c);

	while ( c != 'D')
	{

	// Get input for Matrice here:
	if ( c == 'A')
	{
		input_array();
	}

	// True if there a path between V1 and V2
	if ( c == 'B')
	{
		scanf("%d", &num1);
		scanf("%d", &num2);
		if (is_reachable(num1,num2) == 1)
			printf("True\n");
		else
			printf("False\n");
	}

	// Shortest path between V1 and V2 here:
	//
	if ( c == 'C')
	{
		scanf("%d", &num1);
		scanf("%d", &num2);
		printf("%d\n", shortest_path(num1,num2));
	}

	// Scan for new letter, cotinue if it's not 'D'
	scanf(" %c", &c);
	}
	return 0;
}
