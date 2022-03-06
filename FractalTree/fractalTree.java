import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;

public class fractalTree
{
    public static void main(String[] args) {
        int W = 400, H = 400;
        int rec_limit = 10;
        int x = W / 2, y = H / 4;

        StdDraw.setCanvasSize(W,H);
        StdDraw.setXscale(0,W);
        StdDraw.setYscale(0,H);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.lightGray);

        draw(x, y, y, 0, rec_limit);

    }
    public static void sleep(double seconds)
    {
        try {
            Thread.sleep((long) (1000 * seconds));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void draw(int x, int y, int d, int rec_depth, int rec_lim)
    {
        if (rec_depth == rec_lim)
            return;

        StdDraw.line(x, y, x + d, y + d);
        draw(x + d, y + d, d/2, rec_depth + 1, rec_lim);
        StdDraw.line(x, y, x - d, y + d);
        draw(x - d, y + d, d/2, rec_depth + 1, rec_lim);

    }
}
