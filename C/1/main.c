#include <stdio.h>
#include <stdlib.h>
#include "NumClass.h"

int main(int argc, char *argv[])
{
	if (argc < 3) {
	printf("Not enough arguments recieved! program will terminate.\n");
	return 0;
	}

	// Valid arg number:
	int start = atoi(argv[1]);
	int end = atoi(argv[2]);

	printf("The Armstrong numbers are:");
	for (int i = start; i < end; i++)
	{
		if (isArmstrong(i))
			printf(" %d",i);
	}

	printf("\nThe Palindromes are:");
	for (int i = start; i < end; i++)
	{
		if (isPalindrome(i))
			printf(" %d",i);
	}
	printf("\nThe Prime numbers are:");
	for (int i = start; i < end; i++)
	{
		if (isPrime(i))
			printf(" %d", i );
	}
	printf("\nThe Strong numbers are:");
	for (int i = start; i < end; i++)
	{
		if(isStrong(i))
			printf(" %d",i);
	}

	//printf("\nProgram ran until the end.\n");
	return 0;

}
