import edu.princeton.cs.introcs.StdDraw;
import java.awt.event.KeyEvent;

public class Key extends Thread
{
    Snake s;
    boolean run = true;
    int delayInListening = 20;
    public Key(Snake s)
    {
        this.s = s;
    }
    @Override
    public void run() {
    	snakeBody prev = s.getSnakeBody().get(s.size()-2);
        while(run) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_UP) && ((s.size() == 1) || (s.getHead().getJ()+1 != prev.getJ())))
                s.set_lastMove('U');
            else if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) && ((s.size() == 1) || (s.getHead().getI()+1 != prev.getI())))
                s.set_lastMove('R');
            else if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN) && ((s.size() == 1) || (s.getHead().getJ()-1 != prev.getJ())))
                s.set_lastMove('D');
            else if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT) && ((s.size() == 1) || (s.getHead().getI()-1 != prev.getI())))
                s.set_lastMove('L');
            try {
                Thread.sleep(delayInListening);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
