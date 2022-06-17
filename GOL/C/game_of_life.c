#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Amount of cells is N * N
// for this N full screen is recommended
#define N 55

// delay in milliseconds between each new generation
#define DELAY 17

char canvas[N][N];

void delay(int seconds)
{
	int milli_s = 1000 * seconds;
	clock_t start = clock();
	while (clock() < start + milli_s)
	{
	}
}

void fill()
{
	for(int i = 0; i < N; i++)
	{
		for(int j = 0; j < N; j++)
		{
			canvas[i][j] = '.';
		}
		printf("\n");
	}
}

void show()
{
	for(int i = 0; i < N; i++)
	{
		for(int j = 0; j < N; j++)
		{
			printf(" %c ", canvas[i][j]);
		}
		printf("\n");
	}
}

void clear()
{
	for(int i = 0; i < N; i++)
		printf("\033[2J");
}

int get_nei(int x, int y)
{
	int n = 0;
	for(int i = x - 1; i <= x + 1; i++)
	{
		for (int j = y - 1; j <= y + 1; j++)
		{
			if (i == x && j == y)
				continue;

			if ( i >= 0 && i < N && j >= 0 && j < N)
			{
				if (canvas[i][j] == '*')
				{
					n++;
				}
			}
		}
	}
	return n;
}

void update()
{
	char temp[N][N];
	// n as in number of neighbours a cell has
	int n = 0;

	for (int i = 0; i < N; i++)
	{
		for (int j = 0; j < N; j++)
		{
			n = get_nei(i,j);

			if (canvas[i][j] == '*' && n <= 3 && n>= 2)
				temp[i][j] = '*';

			else if (canvas[i][j] == '.' && n == 3)
				temp[i][j] = '*';
			else
				temp[i][j] = '.';
		}
	}

	for(int i = 0; i < N; i++)
	{
		for(int j = 0; j < N; j++)
		{
			canvas[i][j] = temp[i][j];
		}
	}
}

int main()
{	
	fill();
	
	// initialize with some alive cells
	for (int i = 0; i < 7 * N; i++)
	{		
		int x = rand() % (N-1);
		int y = rand() % (N-1);
		canvas[x][y] = '*';
		canvas[x+1][y] = '*';
	}
	
	while(1)
	{
		clear();
		show();
		delay(DELAY);
		update();

	}
	return 0;
}

