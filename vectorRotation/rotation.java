import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class rotation
{
    private final static double center = 1;
    private final static double scale = 0.3;

    public static void main(String[] args) 
    {
        StdDraw.setXscale(0, center*2);
        StdDraw.setYscale(0, center*2);
        double[] xy = {center + scale, center + scale};
        double theta = Math.PI/180;

        boolean run = true;
        while(run)
        {
            StdDraw.clear();
            // X Y
            StdDraw.line(0,center, center*2, center);
            StdDraw.line(center,0, center, center*2);

            // Start of vector
            StdDraw.circle(center,center, 0.01);
            
            // End of vector + vector
            StdDraw.line(center,center, xy[0], xy[1]);
            StdDraw.circle(xy[0],xy[1], 0.01);
            rotate(xy, theta);
            
            try 
            {
                Thread.sleep(1000/60);
            } 
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void rotate(double[] vec, double theta) 
    {
        double cos_theta = Math.cos(theta);
        double sin_theta = Math.sin(theta);

        double x,y;
        x = (vec[0]-center) * cos_theta - (vec[1]-center) * sin_theta;
        y = (vec[0]-center) * sin_theta + (vec[1]-center) * cos_theta;

        vec[0] = x + center;
        vec[1] = y + center;
    }
}
