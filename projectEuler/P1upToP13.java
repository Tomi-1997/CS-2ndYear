package P1TO13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class P1upToP13 {
    public static void main(String[] args)
    {
        double start = System.currentTimeMillis();



        double end = System.currentTimeMillis();
        System.out.println("Runtime: " + (end - start)/1000 + " seconds.");
    }

    /*
   copy text of large sum to a text file and call functions with the file's name.
     */
    public static void largeSum(String filename)
    {
        char[] endResult = new char[60];
        Arrays.fill(endResult, '0');

        try
        {
            FileReader fr = new FileReader(filename);
            BufferedReader bf = new BufferedReader(fr);
            String line = bf.readLine();

            while (line != null)
            {
                char[] currLine = line.toCharArray();
                int carry = 0;
                int j = endResult.length-1;
                for (int i = currLine.length-1; i >= 0 && j>=0; i--, j--)
                {
                    int num1 = Integer.parseInt(endResult[j]+"");
                    int num2 = Integer.parseInt(currLine[i]+"");
                    int result = num1+num2+carry;
                    if (result < 10)
                    {
                        endResult[j] = (char)(result+'0');
                        carry = 0;
                    }
                    else
                    {
                        endResult[j] = (char)(((result)%10)+'0');
                        carry = 1;
                    }
                }
                while (carry > 0 && j>=0)
                {
                    int num1 = Integer.parseInt(endResult[j]+"");
                    int result = num1+carry;
                    if (result < 10)
                    {
                        endResult[j] = (char)(result+'0');
                        carry = 0;
                    }
                    else
                    {
                        endResult[j] = (char)(((result)%10)+'0');
                    }
                    j--;
                }
                line = bf.readLine();
            }
            int zeros = 0;
            for (int i = 0; i < endResult.length; i++) {
                if (endResult[i] != '0')
                    break;
                else zeros++;
            }

            System.out.print("Result: ");
            for (int i = zeros; i < endResult.length;i++)
            {
                System.out.print(endResult[i]);
            }
            System.out.println();
            System.out.println("Digits: "+ (endResult.length-zeros));


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static int howManyFactors(long n)
    {
        int ans = 1;
        if (n == 1) return ans;

        for (long i = 2; i<= n/2; i++)
        {
            if (n % i == 0)
                ans++;
        }
        return ans + 1;
    }
    public static long largestProductInGrid(String str)
    {
        long bestAns = 1;
        String[] numbersStr = str.split(" ");
        int[][] numbersInt = new int[20][20];
        int k = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                numbersInt[i][j] = Integer.parseInt(numbersStr[k]);
                k++;
            }
        }

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                int currentAns = 1;
                // right
                if (j < 17)
                {
                    currentAns = numbersInt[i][j] * numbersInt[i][j+1] * numbersInt[i][j+2] * numbersInt[i][j+3];
                }
                if (currentAns > bestAns)
                    bestAns = currentAns;
                currentAns = 1;
                // down
                if (i < 17)
                {
                    currentAns = numbersInt[i][j] * numbersInt[i+1][j] * numbersInt[i+2][j] * numbersInt[i+3][j];
                }
                if (currentAns > bestAns)
                    bestAns = currentAns;
                // right down
                if (i < 17 && j < 17)
                {
                    currentAns = numbersInt[i][j] * numbersInt[i+1][j+1] * numbersInt[i+2][j+2] * numbersInt[i+3][j+3];
                }
                if (currentAns > bestAns)
                    bestAns = currentAns;
                // down left
                if (i < 17 && j > 2)
                {
                    currentAns = numbersInt[i][j] * numbersInt[i+1][j-1] * numbersInt[i+2][j-2] * numbersInt[i+3][j-3];
                }
                if (currentAns > bestAns)
                    bestAns = currentAns;
            }
        }
        return bestAns;
    }
    public static long sumOfPrimeUpTo(int maxPrime)
    {
        int primesCounter = 1;
        long ans = 2;
        for (int i = 3; i < maxPrime; i = i + 2) {
            if (isPrime(i))
            {
                primesCounter++;
                ans += i;
            }
        }
        System.out.println("Primes up to "+maxPrime+": "+primesCounter);
        return ans;
    }
    public static int productOfTriplet(int sumOfTriplet)
    {
        for (int i = 1; i < sumOfTriplet; i++) {
            for (int j = i; j < sumOfTriplet; j++) {
                for (int k = j; k < sumOfTriplet; k++) {
                    if (i*i + j*j == k*k && i + j + k == sumOfTriplet)
                    {
                        System.out.println("i="+i+",j="+j+",k="+k);
                        System.out.println(i*j*k);
                    }

                }
            }

        }
        return 1;
    }
    public static long largestProductInNum(String num)
    {
        char[] numCh = num.replaceAll("\n", "").toCharArray();
        int digits = 13;
        long maxProduct = 0;
        for (int i = 0; i < numCh.length - digits; i++)
        {
            long currentProduct = 1;
            for (int j = 0; j < digits; j++)
            {
                int currChar = Integer.parseInt(numCh[i + j]+"");
                if (currChar == 0)
                    break;
                currentProduct *= currChar;
            }
            if (currentProduct > maxProduct)
                maxProduct = currentProduct;
        }
        return maxProduct;
    }
    public static int getNthPrime(int n)
    {
        if (n < 0)
            return -1;
        int[] primes = new int[n];
        primes[0] = 2;
        int i = 1;
        int num = 3;
        while (i < n)
        {
            if (isPrime(num))
            {
                primes[i] = num;
                i++;
            }
            num = num + 2;
        }
        return primes[n-1];
    }
    public static int sumSquareDiff(int start, int end)
    {
        int sumEachIsSquared = 0;
        int sum = (start + end)*(end/2);
        for (int i = start; i <= end ; i++) {
            sumEachIsSquared += i*i;
        }
        return (sum*sum) - sumEachIsSquared;
    }
    public static long getFactorial(int num)
    {
        if (num == 1 || num == 0) return 1;
        return num * getFactorial(num-1);
    }
    public static int largestPalindromeWithNDigits(int digits)
    {
        int max = 0;
        int maxi = 0;
        int maxj = 0;
        // from 999 to 100 (if digits is 3)
        for (int i = TenToThe(digits) - 1; i >= TenToThe(digits-1) ; i--) {
            for (int j = TenToThe(digits) - 1; j >= TenToThe(digits-1) ; j--) {
                int current = i*j;
                if (isPalindrome(current) && current > max)
                {
                    max = current;
                    maxi = i;
                    maxj = j;
                }
            }
        }
        System.out.println("i="+maxi+",j="+maxj);
        return max;
    }
    private static boolean isPalindrome(int current)
    {
        char[] number = (current +"").toCharArray();
        int start = 0;
        int end = number.length - 1;
        while (start < end)
        {
            if (number[start] != number[end])
                return false;
            start++;
            end--;
        }
        return true;
    }
    private static int TenToThe(int digits)
    {
        if (digits == 0)
            return 1;
        return 10 * TenToThe(digits - 1);
    }
    public static int fibSumOfEven(int upperBound)
    {
        int sum = 0;
        ArrayList<Integer> fib = new ArrayList<>();
        fib.add(1);
        fib.add(1);
        int i = 2;
        int current = fib.get(0) + fib.get(1);
        while (current < upperBound)
        {
            fib.add(current);
            i++;
            if (current % 2 == 0)
                sum += current;
            current = fib.get(i - 2) + fib.get(i - 1);
        }
        return sum;
    }
    /**
     * @param num natural number
     * @return halo halo mishtara
     */
    public static int getLargestPrimeFactor(long num)
    {
        for (int i = (int) Math.sqrt(num); i >= 2 ; i--) {
            if (num % i == 0 && isPrime(i))
                return i;
        }
        return -1;
    }
    private static boolean isPrime(int num)
    {
        if (num == 2 || num == 3) return true;
        if (num % 2 == 0 || num % 3 == 0 || num == 1) return false;
        for (int i = 5; i <= Math.sqrt(num); i++)
        {
            if (num % i == 0) return false;
        }
        return true;
    }
}
