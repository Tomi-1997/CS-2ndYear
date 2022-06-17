import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;

public class main {
    static int tiles = 10;
    static int difficulty = 0; // after catching a few apples: above 2 shrinks board, below expands it
    public static void main(String[]args)
    {    	
        StdDraw.clear(Color.lightGray.darker());
        StdDraw.setPenColor(Color.black);
        int height = 500, width = 500; // should be same value, unless you want it to look stretched

        if (args.length == 1)
        {
        	String s = args[0].toLowerCase();
        	if (s.contains("big"))
        	{
        		height = 700;
        		width = 700;
        	}
        	else if (s.contains("huge"))
        	{
        		height = 900;
        		width = 900;
        	}
        }
        if (args.length == 2)
        {
        	try 
        	{
        		height = Integer.parseInt(args[0]);
        		width = Integer.parseInt(args[1]);
        	}
        	catch (NumberFormatException e)
        	{
				System.out.println("Number exception, continued anyway");
        	}
        }
        Board myBoard = new Board(tiles, difficulty, width, height);
        myBoard.drawBoard();
        myBoard.play();
    }

}
