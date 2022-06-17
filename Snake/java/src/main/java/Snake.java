import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Snake {

public snakeBody head;
public snakeBody tail;

// this snake is a list of body parts but the one on top is the head.
public ArrayList<snakeBody> snakeBody;

// need the index of the tail to know where to spawn a new body part
private int lastI;
private int lastJ;
private char lastMove;

    public Snake(int x, int y) {
        snakeBody = new ArrayList<>();
        snakeBody.add(new snakeBody(x,y));
        head = snakeBody.get(0);
        tail = snakeBody.get(0);

        lastMove = 'N';
    }
    public void grow() {
        this.getSnakeBody().add(0,new snakeBody(lastI, lastJ));
    }
    public void move() {
        // Get the element on top (the head)
        snakeBody head = snakeBody.get(snakeBody.size()-1);
        if (snakeBody.size() == 1)
        {
            moveHeadOnly();
            return;
        }
        snakeBody prev = snakeBody.get(snakeBody.size()-2);
        snakeBody last = snakeBody.get(0);
        lastI = last.getI();
        lastJ = last.getJ();
            if ((StdDraw.isKeyPressed(KeyEvent.VK_UP) && (head.getJ()+1 != prev.getJ()))){
                moveAlong();
                head.setJ(head.getJ() + 1);
                lastMove = 'U';
            }
            else if ((StdDraw.isKeyPressed(KeyEvent.VK_DOWN) && (head.getJ()-1 != prev.getJ()))){
                moveAlong();
                head.setJ(head.getJ() - 1);
                lastMove = 'D';
            }
            else if ((StdDraw.isKeyPressed(KeyEvent.VK_LEFT) && (head.getI()-1 != prev.getI()))) {
                moveAlong();
                head.setI(head.getI() - 1);
                lastMove = 'L';
            } else if ((StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) && (head.getI()+1 != prev.getI()))){
                moveAlong();
                head.setI(head.getI() + 1);
                lastMove = 'R';
            }
            else defaultMove();
        }

    private void defaultMove() {
        snakeBody head = snakeBody.get(snakeBody.size()-1);
        switch (getLastMove()) {
            case 'U': {
                moveAlong();
                head.setJ(head.getJ() + 1);
                lastMove = 'U';
                break;
            }
            case 'D': {
                moveAlong();
                head.setJ(head.getJ() - 1);
                lastMove = 'D';
                break;
            }
            case 'L': {
                moveAlong();
                head.setI(head.getI() - 1);
                lastMove = 'L';
                break;
            }
            case 'R': {
                moveAlong();
                head.setI(head.getI() + 1);
                lastMove = 'R';
                break;
            }
            default: {
            }
        }
    }

    private int getLastMove() {
        return  this.lastMove;
    }

    private void moveHeadOnly() {
        lastI = head.getI();
        lastJ = head.getJ();
        if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
            head.setJ(head.getJ() + 1);
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
            head.setJ(head.getJ() - 1);
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
            head.setI(head.getI() - 1);
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
            head.setI(head.getI() + 1);
        }
    }

    private void moveAlong() {
        // Except the head, each body parts goes to the (x,y) index of his parent
        if (snakeBody.size()>1)
            for (int i = 0; i< snakeBody.size()-1;i++)
            {
                snakeBody curr = snakeBody.get(i);
                curr.setI(snakeBody.get(i+1).getI());
                curr.setJ(snakeBody.get(i+1).getJ());
            }
    }

    public boolean hit(int tiles) {
        snakeBody head = this.getSnakeBody().get(this.getSnakeBody().size()-1);
        return (head.getI() > tiles - 1) || (head.getI() < 0)
                || (head.getJ() > tiles - 1) || (head.getJ() < 0);
        // returns true if the head is out of bounds
    }

    public boolean hitYourself() {
        // can't hit yourself as a 2D snake when you only have 3 body parts
        if (this.getSnakeBody().size() < 4) return false;
        snakeBody theHead = this.getSnakeBody().get(this.getSnakeBody().size()-1);
        // check if the new (x,y) of the head collides with any body part.
        for (int i = 0; i<this.getSnakeBody().size()-1;i++)
        {
            snakeBody curr = this.getSnakeBody().get(i);
            if (theHead.getI() == curr.getI() && theHead.getJ() == curr.getJ())
                return true;
        }
        return false;
    }

    // this won't show if StdDraw.show() is called
    public void die(int applesEaten, int tiles) {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.lightGray);
        if (applesEaten > 1)
            StdDraw.text(tiles/2.0,tiles/2.0, "you died with just " +applesEaten +" apples eaten!");
        else
            StdDraw.text(tiles/2.0,tiles/2.0, "you died!");
    }
    public snakeBody getHead() {
        return this.getSnakeBody().get(this.getSnakeBody().size()-1);
    }
    public ArrayList<snakeBody> getSnakeBody() {
        return this.snakeBody;
    }

    public int size()
    {
    	return this.snakeBody.size();
    }

    // Listen to key press from Thread
    public void set_lastMove(char c)
    {
    	// should be either U,L,D,R
    	this.lastMove = c;
    }

// End of class
}
