import java.util.Arrays;

public class P14 {
    public static void main(String[] args)
    {
        double start = System.currentTimeMillis();
        System.out.println(longestCollatzSeq(1000000));
        double end = System.currentTimeMillis();
        System.out.println("Runtime: " + (end - start)/1000 + " seconds.");
    }

    /** Answer for less than 1 mil is 837799 with chain of length 525 **/
    public static int longestCollatzSeq(int upperLimit)
    {
        int ans = 0;
        int[] a = new int[upperLimit];
        Arrays.fill(a, -1);
        a[1] = 1;
        int heavyCalcCounter = 0;
        for (int i = 2; i < a.length; i++)
        {
            if (i % 2 == 0 && a[i/2] != -1)
            {
                a[i] = a[i/2] + 1;
            }
            else if (3*i + 1 <a.length && a[3*i+1]!=-1)
            {
                a[i] = 1 + a[3*i+1];
            }
            else
            {
                long temp = i;
                int steps = 1;
                heavyCalcCounter++;
                while (temp != 1)
                {
                    steps++;
                    if (temp % 2 == 0)
                        temp = temp/2;
                    else
                        temp = 3*temp + 1;
                    if (temp < 0)
                    {
                        System.exit(1);
                    }
                }
                a[i] = steps;
            }
        }
        for (int i = 1; i < a.length; i++) {
            if (a[i] > a[ans])
                ans = i;
        }
        //System.out.println("Times had to calculate collatz seq: "+heavyCalcCounter+" out of "+upperLimit);
        return ans;
    }
}
