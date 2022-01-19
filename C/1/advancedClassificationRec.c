int dig(int num)
{
	int ans = 1;
	num = num / 10;
	while (num > 0)
	{
		ans = ans + 1;
		num = num / 10;
	}
	return ans;
}
// return n to the power of p
int ipow(int n, int p)
{
	if (n == 0) return 0;
	if (n == 1) return 1;
	if (p == 1) return n;
	if (p == 0) return 1;

	int ans = 1;
	while (p>0)
	{
		ans = ans * n;
		p = p - 1;
	}
	return ans;
}

// Every call, returns left most digit to the power of total digits + remaining digits.
int isArmstrongRec(int num, int pow)
{
	if (num == 0) return 0;
	if (num == 1) return 1;
	return ipow((num % 10),pow) + isArmstrongRec(num/10,pow);
}
int isArmstrong(int num)
{
	if (num == isArmstrongRec(num,dig(num))) return 1;
	else return 0;
}

// Returns same number, but backwards. if it a palindrome it should return as the same number.
int isPalindromeRec(int num, int digitsLeft)
{
	if (digitsLeft == 1) return num;
	return num % 10 * ipow(10, digitsLeft -1) + isPalindromeRec(num / 10, digitsLeft - 1);
}
int isPalindrome(int num)
{
	int backwards = isPalindromeRec(num, dig(num));
	if (num == backwards) return 1;
	else return 0;
}
