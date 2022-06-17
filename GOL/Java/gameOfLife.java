import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.awt.event.KeyEvent;


public class gameOfLife {
    private static final int ROWS = 1000;
    private static final int COLS = 1000;
    private static final Color PEN = Color.lightGray;
    private static final Color BG = Color.BLACK;
    private static final int buttonToPause = KeyEvent.VK_Q;

    public static void main(String[] args)
    {
        boolean[][] grid = new boolean[ROWS][COLS];
        boolean run = true;

        /* init StdDraw */
        StdDraw.setCanvasSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.7)
                , (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.9));
        StdDraw.setXscale(0,ROWS);
        StdDraw.setYscale(0,COLS);
        StdDraw.clear(Color.black);

        /* init grid line */
        // set a random number of groups to be alive at generation 0, each group is a square of random size.
        int aliveAtStart = (int) (Math.random() * ROWS);
        for (int i = 0; i < aliveAtStart; i++) {

            int x,y;
            x = (int) (Math.random() * Math.min(ROWS, COLS));
            y = (int) (Math.random() * Math.min(ROWS, COLS));
            while (grid[x][y])
            {
                x = (int) (Math.random() * Math.min(ROWS, COLS));
                y = (int) (Math.random() * Math.min(ROWS, COLS));
            }

            int blocSize = 50;
            for (int j = 0; j < (int)(Math.random() * blocSize); j++)
            {
                for (int k = 0; k < (int)(Math.random() * blocSize); k++)
                {
                    grid[(x+j)%ROWS][(y+k)%COLS] = true;

                }
            }
        }

        /* */
        while (run)
        {
            /* check for quit button */
            if (StdDraw.isKeyPressed(buttonToPause))
            {
                run = false;
            }

            /* draw alive cells */
            StdDraw.show(0);
            StdDraw.clear(BG);
            StdDraw.setPenColor(PEN);
            for (int i = 0; i < ROWS; i++)
            {
                for (int j = 0; j < COLS; j++)
                {
                    if (grid[i][j])
                    {
                        StdDraw.point(i,j);
                    }
                }
            }

            /* update for next generation */
            boolean[][] temp = new boolean[ROWS][COLS];
            for (int i = 0; i < ROWS; i++)
            {
                for (int j = 0; j < COLS; j++)
                {
                    int nei = sumOfNei(grid, i, j);
                    if (grid[i][j] && (nei == 2 || nei == 3)) // Alive, and has 2 or 3 neighbours.
                        temp[i][j] = true;
                    else temp[i][j] = !grid[i][j] && nei == 3; // Dead, and has 3 neighbours.
                }
            }

            for (int i = 0; i < ROWS; i++)
                System.arraycopy(temp[i], 0, grid[i], 0, COLS);

            //sleep(100);
        }
    }

    public static void sleep(int k)
    {
        try {
            Thread.sleep(k);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static int sumOfNei(boolean[][] grid, int n, int m)
    {
        int ans = 0;
        for (int i = n - 1; i <=  n + 1; i++)
        {
            for (int j = m - 1; j <= m + 1 ; j++)
            {
                if (i >= 0 && i < ROWS && j >= 0 && j < COLS
                            && !(i == n && j == m))
                {
                    if (grid[i][j])
                    {
                        ans++;
                    }
                }
            }
        }
        return ans;
    }

}
