#include <math.h>
#include <stdio.h>

int isPrime(int num)
{
	int start = 2;
	double x = num/2;
	int end = (int)(x);
	while (start<=end)
	{
		if (num%start==0) return 0;
		start = start + 1;
	}
	return 1;
}
int fact(int num)
{
	if (num < 2) return 1;
	int answer = num;
	num = num - 1;
	while (num > 1)
	{
		answer = answer * num;
		num = num - 1;
	}
	return answer;
}

int isStrong(int num)
{
	int originalNum = num;
	int sum = 0;
	while (num > 0)
	{
		sum = sum + fact(num % 10);
		num = num / 10;
	}
	if (sum == originalNum) return 1;
	return 0;
}
