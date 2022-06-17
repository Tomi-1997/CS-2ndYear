import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;

public class Maze
{
    private boolean[][] myMaze;
    private int size;

    /*Secondary parameters to help compute maze*/
    private int iterations;
    private int timeToCompute;
    private boolean[][] traveled;
    private boolean solvable;
    private boolean solvedAlready;

    public Maze(int size)
    {
        double st = System.currentTimeMillis();
        this.size = size;
        this.solvable = true;
        this.iterations = 0;
        StdDraw.setScale(0, this.size -1);
        this.myMaze = new boolean[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                this.myMaze[i][j] = true;
            }
        }
        this.generateByACoinFlip();
        double et = System.currentTimeMillis();
        this.timeToCompute = (int)(et - st)/1000;
    }

    private void generateByACoinFlip()
    {
        // Between 0.45 to 0.55, could be very slow if chance is 0.45
        // (actually will be slow regardless)
        double chance = (Math.random() * 0.1) + 0.45;

        // for every block, flip a coin if it is passable
        // keep doing it until a legal maze is formed
        solvable = false;
        while(!solvable)
        {
            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++)
                {
                    this.myMaze[i][j] = !(Math.random() > chance);
                }
            }
            this.myMaze[0][0] = true;
            this.myMaze[size-1][size-1] = true;
            this.traveled = new boolean[size][size];
            solvable = isSolvable(0,0);
        }
        solvedAlready = false;
    }

    private boolean isSolvable(int i, int j)
    {
        if (solvedAlready)
        {
            return false;
        }
        this.iterations++;
        if (i == size-1 && j == size-1)
        {
            solvedAlready = true;
            return true;
        }

        // False if out of bounds, traveled here already, or unpassable.
        if (i < 0 || j < 0 || i >= size || j >= size || this.traveled[i][j] || !this.myMaze[i][j])
            return false;

        // Mark as traveled.
        this.traveled[i][j] = true;

        boolean a = false, b = false, c = false, d = false;
        if (j + 1 < size && !this.traveled[i][j+1])
        {
            a = isSolvable(i,j+1);
        }
        if (j > 0 && !this.traveled[i][j-1])
        {
            b = isSolvable(i,j-1);
        }
        if (i + 1 < size && !this.traveled[i+1][j])
        {
            c = isSolvable(i+1,j);
        }
        if (i > 0 && !this.traveled[i-1][j])
        {
            d = isSolvable(i-1,j);
        }
        return a || b || c || d;
    }
    public void solve()
    {
        display();
        this.traveled = new boolean[size][size];
        solve(0,0);
        solvedAlready = false;
    }
    public boolean solve(int i, int j)
    {
        try
        {
            Thread.sleep(10);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        if (solvedAlready)
            return true;

        if (i == size-1 && j == size-1)
        {
            solvedAlready = true;
            return true;
        }

        // False if out of bounds, traveled here already, or unpassable.
        if (i < 0 || j < 0 || i >= size || j >= size || this.traveled[i][j] || !this.myMaze[i][j])
            return false;

        // Mark as traveled.
        this.traveled[i][j] = true;

        if (i != 0 && j != 0)
        {
            StdDraw.setPenColor(Color.gray);
            StdDraw.filledRectangle(i,j,0.5,0.5);
            StdDraw.setPenColor(Color.black);
        }

        boolean a = false, b = false, c = false, d = false;
        if (j + 1 < size && !this.traveled[i][j+1])
        {
            a = solve(i,j+1);
        }
        if (j > 0 && !this.traveled[i][j-1])
        {
            b = solve(i,j-1);
        }
        if (i + 1 < size && !this.traveled[i+1][j])
        {
            c = solve(i+1,j);
        }
        if (i > 0 && !this.traveled[i-1][j])
        {
            d = solve(i-1,j);
        }
        return a || b || c || d;
    }
    public void display()
    {
        double r = 0.5;
        System.out.println("Plotting");
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (this.myMaze[i][j])
                {
                    StdDraw.rectangle(i,j, r, r);
                }
                else
                {
                    StdDraw.filledRectangle(i,j, r, r);
                }
            }
        }
        StdDraw.setPenColor(Color.green);
        StdDraw.filledRectangle(0,0, r, r);
        StdDraw.setPenColor(Color.red);
        StdDraw.filledRectangle(size-1,size-1, r, r);
    }

    public int iterationsTook()
    {
        return this.iterations;
    }

    public int ComputeTimeTook()
    {
        return this.timeToCompute;
    }
    public static void main(String[] args)
    {
        int size = 30;
        Maze m = new Maze(size);
        m.solve();
        //m.display();
        System.out.println(m.iterationsTook());
        System.out.println(m.ComputeTimeTook());

    }


}


