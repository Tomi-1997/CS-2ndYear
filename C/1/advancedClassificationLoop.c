
// Returns how many digits this number has
int dig(int n)
{
	int ans = 1;
	n = n / 10;
	while (n > 0)
	{
		ans = ans + 1;
		n = n / 10;
	}
	return ans;
}

// Parameters (n,p): returns n to the power of p.
int ipow(int n, int p)
{
	int ans = 1;
	while (p > 0)
	{
		ans = ans * n;
		p = p - 1;
	}
	return ans;
}

int isPalindrome(int num)
{
	if (num < 10) return 1; // 1,2,...,9 are already a palindrome.
	int digits = dig(num);
	while (digits > 0)
	{
		// Get left-most digit at first, and then the two left-most digits, and then three left-most digits and so on.., 
		// every time apply mod 10 to the result to get the actual digit needed.
		int left = num / (ipow(10,digits -1));
		left = left % 10;

		int right = num % 10;

		if (left != right) return 0;
		digits = digits - 2;
		num = num / 10;
	}
	return 1; // If not returned until now, that means the number had an odd number of digits.
}
int isArmstrong(int num)
{
	if (num < 10) return 1; // 1 , 2 , ... 9 are single digits numbers to the power of one alrady.
	int digits = dig(num); 
	int ans = 0;
	int temp = num;

	while (temp > 0)
	{
		ans = ans + (ipow(temp%10,digits)); // Add each digit to the power of the number of digits the number has in total.
		temp = temp / 10;
	}	

	if (ans == num) return 1;
	return 0;
}
