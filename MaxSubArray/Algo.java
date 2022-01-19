import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;

/** Visual representation for Kadane's Algorithm for the maximum sub array problem **/
public class Algo {
    static int N = 10; // Array length
    static int RANGE = 10; // Value of elements belongs to { -RANGE, ... , -1 , 0 , 1 , ...  , RANGE }
    static double SCALE = 0.5; // StdDraw canvas size is (screen.width * SCALE, screen.height * SCALE)

     static int x_max = 10, x_min = 0;
     static int y_max = 10, y_min = 0;

    public static void main(String[]args)
    {
        int[] a = new int[N];
        init(a, RANGE);
        init_Std(a);
        for (int i = 0; i < 5; i=i++)
        {
            draw(a);
            StdDraw.clear(Color.BLACK);
            init(a, RANGE);
        }
    }
    private static void init_Std(int[] a)
    {
        int len = a.length;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        StdDraw.setCanvasSize((int)(width * SCALE), (int)(height * SCALE));
        StdDraw.clear(Color.black);
    }
    private static void print(int[] a)
    {
        System.out.print("[  ");
        for (int i: a)
        {
            System.out.print(i + "  ");
        }
        System.out.println("]");

    }
    private static int randInt(int x)
    {
        double ans = Math.random()*2*x - x;
        return (int) (ans);
    }
    private static void init(int[] a, int range)
    {
        for (int i = 0; i < a.length; i++)
        {
            a[i] = randInt(range);
        }
    }
    private static void draw(int[] a)
    {
        StdDraw.setXscale(x_min, a.length);
        StdDraw.setYscale(y_min, y_max);
        StdDraw.setPenColor(Color.lightGray);
        double y_mid = (y_max - y_min) * 0.5;
        double x_mid = (x_max - x_min) * 0.5;
        for (int i = 0; i < a.length;i++)
        {
            StdDraw.text(i + 0.51 , y_mid * 1.5 , "" + a[i] + " ");
        }

        StdDraw.text(x_min + 0.5 , y_mid * 1 , "Current:");
        StdDraw.text(x_min + 0.5 , y_mid * 0.5 , "Best:");

        get_max_sub_array(a);
    }
    private static int get_max_sub_array(int[] a)
    {
        int current = 0;
        int best = 0;
        for (int i = 0; i < a.length; i++)
        {
            draw_arrow(i, Color.lightGray);
            current = max ( 0 , current + a[i] );
            StdDraw.text(x_min + 2 , (y_max - y_min) * 0.5 , "" + current);
            best = max ( best , current );
            StdDraw.text(x_min + 2 , (y_max - y_min) * 0.25, "" + best);
            wait(1500);
            draw_arrow(i, Color.BLACK);
            if ( i < a.length - 1)
                del_text();
        }
        return best;
    }
    private static void del_text()
    {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(x_min + 2.1 , (y_max - y_min) * 0.25 , 0.5 , 4 );
    }
    private static void draw_arrow(int i, Color cl)
    {
        StdDraw.setPenColor(cl);
        if (StdDraw.getPenColor() == Color.BLACK)
            StdDraw.setPenRadius(0.02);
        else
            StdDraw.setPenRadius(0.005);

        StdDraw.line(i + 0.5, (y_max - y_min) * 0.85 , i + 0.5 , (y_max - y_min) * 0.95);
        StdDraw.line(i + 0.5, (y_max - y_min) * 0.85 , i + 0.60 , (y_max - y_min) * 0.89);
        StdDraw.line(i + 0.5, (y_max - y_min) * 0.85 , i + 0.40 , (y_max - y_min) * 0.89);

    }
    private static int max(int x, int y)
    {
        if ( x > y )
            return x;
        return y;
    }
    private static void wait(int i){
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
