#include "mat.hpp"
#include <string>
#include <iostream>
#include <stdlib.h>
#include <stdexcept>

std::string ariel::mat(int i, int j, char c1, char c2)
{
	if (i <= 0 || j <= 0 || i % 2 == 0 || j % 2 == 0)
	{
		throw std::invalid_argument("Number exception.");
	}
	int c1i = c1;
	int c2i = c2;
	const int minValidASCII = 33;
	const int maxValidASCII = 126;

	if (c1i < minValidASCII || c1i > maxValidASCII || c2i < minValidASCII || c2i > maxValidASCII)
	{
		throw std::invalid_argument("Symbol exception.");
	}

	char* mat = (char*) malloc (sizeof(char) * i * j);
	if (mat == NULL)
	{
		return "Malloc error";
	}
	
	// init matrice with c2
	for (int k = 0; k < i*j; k++)
	{
		mat[k] = c2;
	}
	
	// Edge case
	if (i == 1 || j == 1)
	{
		for (int k = 0; k < i*j; k++)
		{
			mat[k] = c1;
		}
	}

	else
	{
		// each time, paint a m x n matrice with only the edges
		// next iteration paint a (m-2) x (n-2) matrice
		// and then (m-4) x (n-4) and so on

		for (int k = 0; k < i; k = k + 2)
		{
			// top and bottom
			for (int l = k; l < i - k; l++)
			{
				int top = l + k*i;
				int bot = (i*j) - top - 1;
				mat[top] = c1;
				mat[bot] = c1;
			}
	
			// left
			for (int l = k*(i+1); l < i * (j-k); l = l + i)
			{	
				mat[l] = c1;
			}
			
			// right
			for (int l = (i * j) - 1 - (i*k) - k; l >= k*i - k; l = l - i)
			{
				mat[l] = c1;
			}
		}	
	}
	std::string myRug;
	// matrice to string
	for (int k = 0; k < i*j; k++)
	{
		char current = mat[k];
		if(k != 0 && k % i == 0)
		{
			myRug += "\n";
		}
			myRug += current;
	}
	free(mat);
	return myRug;
}
