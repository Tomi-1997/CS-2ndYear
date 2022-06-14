import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class Pomodoro
{
    private final Color workColor = Color.red.darker();
    private final Color restColor = Color.green.darker();
    private static final int DELAY = 1000;
    protected int work, rest;
    public Pomodoro(int workMinutes, int restMinutes)
    {
        int w = (int) (0.09 * Toolkit.getDefaultToolkit().getScreenSize().width);
        int h = (int) (0.4 * Toolkit.getDefaultToolkit().getScreenSize().height);
        work = workMinutes * 60;
        rest = restMinutes * 60;

        StdDraw.setCanvasSize(w, h);
        StdDraw.setPenRadius(0.01);
        StdDraw.clear(Color.black);
        start();
    }
    public void start()
    {
        // INIT
        StdDraw.setScale(0 , rest);
        StdDraw.setPenColor(workColor);
        for (int i = 0; i < rest + 1; i++)
        {
            StdDraw.line(0, rest - i, rest, rest - i);
        }
        while (true)
        {
            StdDraw.setScale(0 , work );
            StdDraw.setPenColor(restColor);
            for (int i = 0; i < work; i++)
            {
                StdDraw.line(0, work - i, work, work - i);
                delay();
            }
            Toolkit.getDefaultToolkit().beep();
            StdDraw.setScale(0 , rest);
            StdDraw.setPenColor(workColor);
            for (int i = 0; i < rest + 1; i++)
            {
                StdDraw.line(0, rest - i, rest, rest - i);
                delay();
            }
            Toolkit.getDefaultToolkit().beep();
        }
    }

    public static void delay()
    {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        int WORK = 25, REST = 5;
        try
        {
            WORK = Integer.parseInt(args[0]);
            REST = Integer.parseInt(args[1]);

            if (REST > WORK)
            {
                int temp = WORK;
                WORK = REST;
                REST = temp;
            }
        }
        catch (Exception e)
        {

        }
        Pomodoro p = new Pomodoro(WORK, REST);
        p.start();
    }

}
