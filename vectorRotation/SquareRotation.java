import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.awt.event.KeyEvent;

public class SquareRotation
{
    private final static double center = 1;
    private final static double scale = 0.3;
    private final static Color bg = Color.black;
    private final static Color pen = Color.lightGray;

    public static void main(String[] args)
    {

        double start = System.currentTimeMillis();
        double current;

        StdDraw.setXscale(0, center*2);
        StdDraw.setYscale(0, center*2);
        StdDraw.setPenRadius(0.004);
        StdDraw.setPenColor(pen);
        double theta = Math.PI/180;
        int direction = 1;

        Point a = new Point(center + scale, center + scale);
        Point b = new Point(center - scale, center + scale);
        Point c = new Point(center - scale, center - scale);
        Point d = new Point(center + scale, center - scale);

        Point[] p = new Point[]{a,b,c,d};

        boolean run = true;
        while(run)
        {
            current = System.currentTimeMillis();
            StdDraw.clear(bg);

            // X, Y axis
            StdDraw.line(0,center, center*2, center);
            StdDraw.line(center,0, center, center*2);

            // Our rectangle
            StdDraw.polygon( new double[]{a.x, b.x, c.x, d.x} , new double[]{a.y, b.y, c.y, d.y} );

            // Rotate each point of the rectangle
            for (Point pt: p)
                rotate(pt, theta);

            try
            {
                Thread.sleep(1000/90);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            int sevenSeconds = 7000;
            if (current - start >= sevenSeconds)
            {
                theta = Math.PI/180 * direction;
                direction = direction * -1;
                start = System.currentTimeMillis();
            }

            theta = theta + Math.PI/(360*40) * direction;
            StdDraw.show(0);

            run = !StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE);
        }
        System.exit(0);
    }

    public static void rotate(Point p, double theta) {
        double cos_theta = Math.cos(theta);
        double sin_theta = Math.sin(theta);

        double x,y;
        x = (p.x-center) * cos_theta - (p.y-center) * sin_theta;
        y = (p.x-center) * sin_theta + (p.y-center) * cos_theta;

        p.x = x + center;
        p.y = y + center;
    }

    static class Point {
        double x;
        double y;
        public Point(double x, double y)
        {
            this.x = x;
            this.y = y;
        }
    }
}
