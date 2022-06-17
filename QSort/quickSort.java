import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.time.Clock;
import java.util.ArrayList;

/***
 * Implementation of quick sort with a general number of threads.
 * Each thread will sort a sub array and at the end they will be merged.
 */

public class quickSort {
    final static int N = 1000;  // array size (also sets the scale for StdDraw)
    final static int P = 1000; // pause time between texts and new arrays
    static int T_CURRENT = 1; // Current threads running
    static int T_END = 6; // Max number of threads (not including main)

    final static Color bg_background = Color.black,
                    pen_color = Color.lightGray;
    static final int width = 1000;
    final static int height = 600;

    public static void main(String[] args) throws InterruptedException
    {
        // INITIALIZE
        double start_time = 0, end_time = 0, diff = 0;
        int[] a = get_arr();
        init_StdDraw(width,height);
        draw(a);
        pause(P);

        // DRAW QUICK SORT WITH T_CURRENT THREADS
        for (; T_CURRENT <= T_END ; T_CURRENT++)
        {
            shuffle(a);
            pause(P);

            System.out.print("\nFinish order: ");
            ArrayList<int[]> sub_arr = new ArrayList<>();
            ArrayList<Qs_thread> thread_list = new ArrayList<>();

            // INIT SUB ARRAYS FOR EACH THREAD
            for (int i = 0; i < T_CURRENT; i ++)
            {
                int start = (N/T_CURRENT) * (i);
                int end = (N/T_CURRENT) * (i+1);

                if (i == T_CURRENT -1)
                    end = N;

                sub_arr.add(get_sub(a, start, end));
            }
            // INIT THREADS AND ASSIGN EACH THREAD HIS OWN SUB ARRAY
            for (int i = 0; i < T_CURRENT; i++)
            {
                thread_list.add(new Qs_thread(i + "", sub_arr.get(i)));
                thread_list.get(i).setName(i+"");
                thread_list.get(i).start();
            }
            start_time = System.currentTimeMillis();
            // MAKE SURE THEY RUN SIMULTANEOUSLY
            for (int i = 0; i < T_CURRENT; i++)
            {
                thread_list.get(i).join();
            }
            // IMMERSIVE PAUSE
            //pause(P);
            // MERGE (important comment)
            a = merge(sub_arr);

            end_time = System.currentTimeMillis();
            diff = (end_time - start_time)/1000;
            System.out.print(" Runtime: " + diff + " seconds");

            pause(P);
            sub_arr.clear();
            thread_list.clear();
        }
        // END
        StdDraw.setPenColor(Color.black);
        StdDraw.filledCircle(N/2.0, N/2.0, N/10.0);
        StdDraw.setPenColor(pen_color);
        StdDraw.circle(N/2.0, N/2.0, N/10.0);
        StdDraw.text(N/2.0, N/2.0, "FIN");
    }
    public static void pause(int time)
    {
        if (time < 0)
            time = 0;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void init_StdDraw(int w, int h)
    {
        StdDraw.setCanvasSize(w,h);
        StdDraw.setScale(0,N);
        StdDraw.clear(bg_background);
        StdDraw.setPenColor(pen_color);
    }
    public synchronized static void draw(int[] a)
    {
        StdDraw.setPenColor(pen_color);
        for (int i = 0; i<a.length; i++)
        {
            StdDraw.line(i,0,i,a[i]);
        }
    }
    public static int[] get_arr()
    {
        // ARRAY OF NATURAL NUMBERS {1,2,3...,N-1,N}
        int[] a = new int[N];
        for (int i = 0; i<N;i++)
            a[i] = i+1;
        return a;
    }
    public static void swap(int[] a, int i, int j, boolean draw,int start)
    {
        if (draw)
        {
            StdDraw.setPenColor(bg_background);
            StdDraw.line(i+start,0,i+start,a[i]);
            StdDraw.line(j+start,0,j+start,a[j]);
            StdDraw.setPenColor(pen_color);
            StdDraw.line(i+start,0, i+start,a[j]);
            StdDraw.line(j+start,0,j+start,a[i]);
        }
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    public static int randInt(int start, int end)
    {
        int ans =(int) (Math.random()*(end-start));
        ans = ans + start;
        return ans;
    }
    public static void shuffle(int[] a)
    {
        for (int i = 0; i < a.length;i++)
        {
            swap(a,i,randInt(0,a.length-1),true,0);
        }
    }
    public static void quickSort(int[] arr, int start, int end)
    {
        if (start < end)
        {
            int piv = partition(arr, start, end);
            quickSort(arr, start, piv - 1);
            quickSort(arr, piv + 1, end);
        }
    }
    private static int partition(int[] arr, int low, int high)
    {
        int thread = 0;
         // FOR THE Ti THREAD DRAW ELEMENTS RELATIVELY TO i
        try
        {
            thread = Integer.parseInt(Thread.currentThread().getName());
            thread = thread * ( N / T_CURRENT);
        }
        catch (NumberFormatException ignored)
        {
        }
        int pivot = arr[high];
        // DRAW PIVOT, GREY IT OR KEEP
//        StdDraw.setPenColor(Color.red);
//        StdDraw.line(high + thread, 0, high + thread, arr[high]);
//        StdDraw.setPenColor(pen_color);
        //
        int i = low - 1;
        for (int j=low; j<high; j++)
        {
            if (arr[j] < pivot)
            {
                i++;
                swap(arr,i,j,true,thread);
            }
        }
        swap(arr,i+1, high,true,thread);
        return i+1;
    }
    public synchronized static int[] get_sub(int[] a,int start,int end)
    {
        int[] b = new int[end - start];
        for (int i = start;i<end;i++)
        {
            b[i - start] = a[i];
        }
        return b;
    }
    private static int[] merge(ArrayList<int[]> list)
    {
        if (list.size() == 0)
            return null;
        if (list.get(0).length == 0)
            return null;

        int[] temp = new int[N];
        // Array of index for each sub-array, so we can iterate them
        int[] indexes = new int[T_CURRENT];
        int min = 0;
        int min_i = 0;
        int t = 0;

        boolean flag = true;
        while (flag)
        {
            flag = false;
            for (int i = 0; i < list.size(); i++)
            {
                if (indexes[i] < list.get(i).length)
                    flag = true;
                else
                    continue;

                if (list.get(i)[indexes[i]] < min)
                {
                    min = list.get(i)[indexes[i]];
                    min_i = i;
                }

            }

            if (!flag)
                break;

            temp[t] = min;
            t++;
            indexes[min_i]++;

            // DRAW

            int tbd = indexes[min_i] - 1;
            tbd = tbd + min_i * N/ T_CURRENT;

            StdDraw.setPenColor(bg_background);
            if (tbd > t)
                StdDraw.line(tbd ,0,tbd,N);
            StdDraw.line(t-1,0, t-1, N);

            StdDraw.setPenColor(pen_color);
            StdDraw.line(t-1,0, t-1, min);

            min = Integer.MAX_VALUE;
        }
        return temp;
    }
    static class Qs_thread extends Thread
    {
        private int[] a;
        public Qs_thread(String name, int[] a) {
            super(name);
            this.a = a;
        }

        @Override
        public void run() {
            try
            {
                quickSort(a, 0, a.length - 1);
                System.out.print((Integer.parseInt(this.getName())+1)+"  ");
            } catch (Exception e)
            {
                System.err.println("Quick sort failed in thread:" + currentThread());
            }
        }

    }
}
