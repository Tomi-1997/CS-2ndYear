import java.awt.*;

public class bogoSort {
    public static void main(String[]args)
    {
        int wait_time = 2048;
        int[] a = new int[10];
        init(a);

        drawContainer dc = new drawContainer(a, Color.white, Color.black);
        dc.drawContainer();

        boolean done = false;
        while(!done) {
            shuffle(a);
            drawContainer.refresh(wait_time);
            dc.update();
            done = dc.drawCheck();

            if (wait_time > 1)
                wait_time = wait_time / 2;
        }
    }

    private static void init(int[] a) {
        for (int i = 0; i < a.length; i++)
        {
            a[i] = i + 1;
        }
    }

    private static void shuffle(int[] a) {
        for (int i = 0; i < a.length; i++)
        {
            int randomPos = random(0,a.length);
            swap(a, i, randomPos);
        }
    }

    private static int random(int from, int to) {
        double d = Math.random() * to;
        d = d - from;
        return (int) d ;
    }

    private static void swap(int[] a, int i, int j)
    {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static void printA(int[] a)
    {
        for (int i = 0; i < a.length; i++)
        {
            System.out.print(a[i]+" ");
        }
    }

    private boolean isSorted(int[] a)
    {
        for (int i = 0; i<a.length-1;i++)
        {
            if (a[i+1] < a[i]) return false;
        }
        return true;
    }
}
