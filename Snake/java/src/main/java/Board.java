import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;

public class Board {
    // time between movement refreshing
    private int wait_time = 300; // old is 200
    private int appleCounter = 0;
    private int difficulty;
    private int tiles;
    public Snake s;
    public Apple a;
    public Key k;
    
    private int height;
    private int width;

    public Board(int tiles, int difficulty, int w, int h) {

        // Init board
    	this.height = h;
    	this.width = w;
        setTiles(tiles);
        setDifficulty(difficulty);
        StdDraw.setCanvasSize(h,w);
        StdDraw.setScale(-0.1,tiles+0.1);

        // Init Snake
        this.s = spawnSnake();
        snakeBody first = s.getSnakeBody().get(0);
        s.getSnakeBody().add(new snakeBody(first.getI(), first.getJ()+1));

        // Init key listener
        this.k = new Key(s);
        k.start();
        
        // Init apple
        spawnApple();
    }
    public void drawBoard() {

        // borders
    	StdDraw.setPenRadius(0.005);
        StdDraw.line(0,0,tiles,0);
        StdDraw.line(0,tiles,tiles,tiles);
        StdDraw.line(0,0,0,tiles);
        StdDraw.line(tiles,tiles,tiles,0);


        //Borders and gridlines
        for(int i=0;i< getTiles()+1;i++)
            StdDraw.line(i,0,i,tiles);
        for(int i=0;i< getTiles()+1;i++)
            StdDraw.line(0,i,tiles,i);

    }
    public void spawnApple() {
        int x = randInt(1,tiles-1);
        int y = randInt(1,tiles-1);
        this.a = new Apple(x,y);

    }
    public boolean caughtApple() {
        return s.getHead().getI() == a.getI() && s.getHead().getJ() == a.getJ();
    }
    public Snake spawnSnake() {
        return new Snake(randInt(1,tiles-1),randInt(1,tiles-1));
    }
    /** Returns an integer between z and w including both **/
    private int randInt(int z, int w) {
        return (int) (Math.random()*(w-z) + z);
    }
    public void appleEaten() {
        appleCounter++;
        if (wait_time > 70)
            wait_time = wait_time - 20;
        else if (wait_time > 10)
            wait_time = wait_time - 2;

        // Every five apples shrink \ expand the board
        if (appleCounter % 5 == 0)
        {
            if (difficulty <= 3)
            {
                StdDraw.setPenRadius(StdDraw.getPenRadius()*0.7);
            }
        setTiles(tiles + 2 - difficulty);
        StdDraw.setScale(0,tiles);
        }

        spawnApple();
        s.grow();
    }
    public int getTiles() {
        return tiles;
    }
    public void setTiles(int tiles) {
        this.tiles = tiles;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    public void drawSquare(snakeBody sb, int indx) {
        double x = sb.getI()+0.5;
        double y = sb.getJ()+0.5;
        double width_height = 0.5 - (indx*0.01);
        if (width_height < 0) width_height = 0.05;
        StdDraw.filledRectangle(x,y,width_height,width_height);
    }
    public void play() {
        // Redraw board
        StdDraw.clear(Color.lightGray.darker());
        StdDraw.setPenColor(Color.black);
        drawBoard();

        // Draw apple
        StdDraw.setPenColor(Color.red);
        StdDraw.filledRectangle(a.getI()+0.5,a.getJ()+0.5,0.5,0.5);

        // Draw snake
        StdDraw.setPenColor(Color.GREEN.darker().darker());
        for (int i = 0; i<s.getSnakeBody().size();i++)
        {
            drawSquare(s.getSnakeBody().get(i), s.getSnakeBody().size() - (i+1));
        }

        // H text above the head
        snakeBody last = s.getSnakeBody().get(s.getSnakeBody().size()-1);
        StdDraw.setPenColor(Color.black);
        StdDraw.text(last.getI()+0.5,last.getJ()+0.5,"H");
        StdDraw.setPenColor(Color.GREEN.darker().darker());


        // Check if snake caught an apple
        if (caughtApple())
        {
//            highlightSnake();
            appleEaten();
        }
        StdDraw.show(0);

        update();

    }
    private void highlightSnake() {
        //  For when the snakes just ate the apple
        for (int i = 0; i<4;i++)
        {
            // Draws the snake (at the same spot) four times,
            // magenta --> black --> magenta --> black
            if (i%2 == 0) StdDraw.setPenColor(Color.magenta);
            else StdDraw.setPenColor(Color.black);
            refresh(100);
            int z = 0;
        for (snakeBody sb:this.s.getSnakeBody())
        {
            drawSquare(sb, this.s.getSnakeBody().size() - (z+1));
            z++;
        }
        }

    }

    public void update() {
        refresh();
        s.move();
        if (s.hit(tiles) || s.hitYourself()) done();
        else {
            play();
        }
    }
    //default refresh
    public void refresh(){
        try {
            Thread.sleep(wait_time );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void refresh(int i){
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void done() {
    s.die(appleCounter,this.tiles);
    refresh(2000);
    String[] args = new String[2];
    args[0] = this.width+"";
    args[1] = this.height+"";
    main.main(args);
    }
    @Override
    public String toString() {
        return "there are " + tiles+ " tiles!" +
                " And the snake has length of " + this.s.getSnakeBody().size() + "!";
    }
}
