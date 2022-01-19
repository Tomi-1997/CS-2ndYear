import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class SquareRotation {
    private final static double center = 1;
    private final static double scale = 0.3;

    public static void main(String[] args) {

        double start = System.currentTimeMillis();
        double current;

        StdDraw.setXscale(0, center*2);
        StdDraw.setYscale(0, center*2);
        StdDraw.setPenRadius(0.004);
        double theta = Math.PI/180;
        int dir = 1;

        Point a = new Point(center + scale, center + scale);
        Point b = new Point(center - scale, center + scale);
        Point c = new Point(center - scale, center - scale);
        Point d = new Point(center + scale, center - scale);

        Point[] p = new Point[]{a,b,c,d};

        boolean run = true;
        while(run)
        {
            current = System.currentTimeMillis();
            StdDraw.clear();
            // X Y axis
            StdDraw.line(0,center, center*2, center);
            StdDraw.line(center,0, center, center*2);

            // Rectangle
            StdDraw.polygon( new double[]{a.x, b.x, c.x, d.x} , new double[]{a.y, b.y, c.y, d.y} );

            // Rotate each point
            for (Point pt: p)
                rotate(pt, theta);

            try {
                Thread.sleep(1000/90);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (current - start >= 7000)
            {
                theta = Math.PI/180 * dir;
                dir = dir * -1;
                start = System.currentTimeMillis();
            }

            theta = theta + Math.PI/(360*30) * dir;
        }
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
