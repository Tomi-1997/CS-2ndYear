import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;

public class drawContainer {

    private double start_time;
    private int counter;
    long wait_time = 10;
    Color penColor;
    Color backGColor;
    int[] elemHolder;

    int x_scale, y_scale;

    /**Parameters: (your array to be drawn, pen color, background color)**/
    public drawContainer(int[] a, Color p, Color bg) {
        elemHolder = a;
        this.penColor = p;
        this.backGColor = bg;
        start_time = System.currentTimeMillis();
        counter = 0;
        init();
    }

    private void init() {
        int max = max(this.elemHolder);
        // X-width by the number of elements, Y-height by the maximum value
        // assuming all elements are naturals.
        x_scale = this.len()-1;
        y_scale = max;
        if (x_scale == 0) x_scale = 1;
        StdDraw.setXscale(0,x_scale);
        StdDraw.setYscale(0,y_scale);
        StdDraw.clear(this.backGColor);
        StdDraw.setPenColor(this.penColor);

    }

    private void drawTimer() {
        double dt = System.currentTimeMillis() - this.start_time;
        int accumulated = (int) (dt/1000);
        /** display time accumulated at the top left **/
        StdDraw.text(x_scale*0.01,y_scale*0.99,accumulated+"",counter*10);
    }

    private int max(int[] elemHolder) {
        int max = 0;
        for (int i = 0; i < this.len(); i++)
        {
            if (elemHolder[i] > max)
                max = elemHolder[i];
        }
        return max;
    }

    private int len() {
        return this.elemHolder.length;
    }
    public void drawContainer() {
        for (int i = 0; i<len(); i++)
        {
            draw(i, this.elemHolder[i]);
        }
        /** no real reason for counter **/
        counter++;
    }

    public void update() {
        refresh(wait_time);
        StdDraw.clear(this.backGColor);
        this.drawTimer();
        this.drawContainer();
    }

    /** draws rectangle with parameters (x,y) **/
    private void draw(int x, int y) {
        double width = 4.95/this.len();
        StdDraw.filledRectangle(x,0,width,y);
    }
    public void del(int x, int y) {
        StdDraw.setPenColor(backGColor);
        draw(x,y);
        StdDraw.setPenColor(penColor);
    }
    public static void refresh(long f) {
        try {
            Thread.sleep((f));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**Green elements are okay, bad elements are bad**/
    public boolean drawCheck() {
        StdDraw.setPenColor(Color.GREEN);
        draw(0,elemHolder[0]);
        StdDraw.setPenColor(this.penColor);
        refresh(wait_time);
        for (int i = 1; i<this.len();i++)
        {
            if (this.elemHolder[i] < this.elemHolder[i-1])
            {
                StdDraw.setPenColor(Color.red);
                draw(i,elemHolder[i]);
                StdDraw.setPenColor(this.penColor);
                return false;
            }
            else
            {
                StdDraw.setPenColor(Color.GREEN);
                draw(i,elemHolder[i]);
                StdDraw.setPenColor(this.penColor);
            }
                refresh(wait_time);
        }
        return true;
    }
}
