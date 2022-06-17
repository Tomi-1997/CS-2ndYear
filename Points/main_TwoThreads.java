// 2D "Simulation" of balls affected by gravity

import com.google.gson.Gson;
import edu.princeton.cs.introcs.StdDraw;
import com.google.gson.*;
import java.awt.Color;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

class main_TwoThreads{

    static boolean printActions = false;
    // StdDraw stuff
    static char buttonToQuit = 'q';
    static Color backgroundColor = Color.black,
            penColor = Color.white;

    static int x_scaleMin = 0, x_scaleMax = 2000,
            y_scaleMin = 0, y_scaleMax = 2000;

    private static final int vecCollideLine = 100;
    static long frames = 1000/60;
    static long calc_frames = 1000/60;

    // Shape stuff
    static int ballNum = 1;
    static int radiusRange = 100, minRadius = 2;
    static boolean cutVelocity = false;

    public static void main(String[] args)
    {
        //String filename = "regularBall.txt";
        Vector<Ball> b = initBallArray(ballNum);
        initStdDraw();

        /* Drawing thread. */
        draw_Thread draw_t = new draw_Thread("draw", b);
        draw_t.start();

        /* Thread responsible to apply gravity, make balls bounce and hit walls.*/
        logical_Thread logical_t = new logical_Thread("apply",b);
        logical_t.start();
        try
        {
            logical_t.join();
            draw_t.join();
        }
        catch (InterruptedException e)
        {
        e.printStackTrace();
        }
    }
    public static class logical_Thread extends Thread
    {
        Vector<Ball> worldObjects;
        public logical_Thread(String name, Vector<Ball> b) {
            super(name);
            this.worldObjects = b;
        }
            public void run(){
            long shuffleTime = 20000L;
            // Init timer to shuffle balls every X seconds
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    double howMuch = 200;
                    System.out.println("Shuffling");
                    for (int i = 0; i <ballNum; i++) {
                        WorldObject w = worldObjects.elementAt(i);
                        moveLeft(w, (Math.random() * howMuch / 5) - howMuch / 10);
                        moveUp(w, Math.random() * howMuch);
                    }
                }
            };
            Timer timer = new Timer("Timer");
            timer.schedule(task, 10000, shuffleTime);

            // Calc next pos for each ball
            while (!keyPressed(buttonToQuit)) {
                int objectIndex = 0;
                for (int i = 0; i < ballNum; i++)
                {
                    Ball currentBall = worldObjects.elementAt(i);
                    collide(i, worldObjects);

                    // make object fall down with speed increasing depending on the radius
                    if (currentBall.getY() > 0)
                    {
                        fall(currentBall, worldObjects.get(objectIndex).getR());
                    }
                    double approx_of_next_yValue = worldObjects.get(objectIndex).getVelocity().getY() + currentBall.getY();
                    // if ball is about to touch ground or too fast to determine if it touches ground then
                    // make it bounce
                    if (aboutToLand(currentBall) || approx_of_next_yValue < -5)
                    {

                        // to prevent an object from splatting on ground from falling too fast
                        if (currentBall.getY() < currentBall.getHeight()*2 + 10) {
                            currentBall.getVelocity().setY(Math.abs(currentBall.getVelocity().getY() * 0.7));
                        }
                    }
                    bounceOffWall(currentBall);
                    objectIndex++;
                    currentBall.update();

                }
                /*Check if shuffle button is pressed*/
                shuffle(worldObjects, 20, 0.9);
                /*Check if empty space is pressed -> if yes, pause*/
                if (StdDraw.mousePressed() && notTouchingButtons(20)) {

                        calc_frames = 1000/20;
//                    freezeAll(worldObjects, cutVelocity);
                }
                else
                {
                    calc_frames = 1000/60;
                }
                pause((long)(calc_frames*1.2));
            }
            System.exit(0);
        }
        }
    public static class draw_Thread extends Thread
    {
        private final Vector<Ball> worldObjects;
        public boolean run = true;
        public draw_Thread(String name, Vector<Ball> b) {
            super(name);
            this.worldObjects = b;
        }
        public void run() {
            int size = worldObjects.size();
            double currentTime = System.currentTimeMillis();
            while (run){
                if (System.currentTimeMillis() - currentTime >= 40)
                {
                    worldObjects.add(defBall());
                    size++;
                    ballNum++;
                    currentTime = System.currentTimeMillis();
                    System.out.println("There are now " + size + " balls");
                }
                //StdDraw.setPenColor(ballColor);
                for (WorldObject w : worldObjects)
            {

//                StdDraw.circle(w.getX(), w.getY(), w.getHeight());
                StdDraw.point(w.getX(),w.getY());
                draw_buttons(20, 0.9);
            }
                StdDraw.show(0);

                pause(frames/2);
            StdDraw.clear(backgroundColor);

            }
        }
}
    public static void loadJson(String filename, Vector<Ball> b)
    {
        String input = "";
        String s;
        try {
            FileReader f = new FileReader(filename);
            BufferedReader readFile = new BufferedReader(f);
            s = readFile.readLine();
            while (s != null) {
                input = input.concat(s);
                s = readFile.readLine();
            }
            f.close();
            readFile.close();
        } catch (IOException e) {
            System.err.println("Wrong file name!");
        }
        Gson gson = new Gson();

        Ball ball = gson.fromJson(input, Ball.class);

        if (ball != null)
            b.add(ball);

        else
        {
            b.add(defBall());
            System.out.println("Error encountered trying to load ball! random ball inserted instead.");
        }
    }
    public static void saveJson(String filename, Ball b)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String ballDesc = gson.toJson(b);
        try {
            FileWriter file = new FileWriter (filename);
            file.write(ballDesc);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static boolean keyPressed(char buttonToQuit)
    {
        String c = buttonToQuit+"";
        String cl = c.toLowerCase();
        String cu = c.toUpperCase();
        return StdDraw.isKeyPressed(cu.charAt(0)) || StdDraw.isKeyPressed(cl.charAt(0));
    }
    public static void bounceOffWall(WorldObject w)
    {
        if (w.getX() < x_scaleMin + x_scaleMax*0.01)
            w.getVelocity().setX(Math.abs(w.getVelocity().getX()));

        if (w.getX() > x_scaleMax - x_scaleMin*0.01)
            w.getVelocity().setX((Math.abs(w.getVelocity().getX())) * -1);
    }
    public static void moveLeft(WorldObject w, double byHowMuch)
    {
        Velocity v = w.getVelocity();
        v.setX(byHowMuch);
        if (printActions)
            System.out.println("Ball pushed right");
    }
    public static void moveUp(WorldObject w, double byHowMuch)
    {
        Velocity v = w.getVelocity();
        v.setY(v.getY() + byHowMuch * 0.4);

        if (printActions)
            System.out.println("Ball pushed upwards");

    }
    public static void fall(WorldObject w, double R)
    {
        // Decrease Y velocity with relation to radius
        w.getVelocity().setY(w.getVelocity().getY() - (R * 0.008));
    }
    public static boolean aboutToLand(WorldObject w)
    {
        double veryCloseToGround = (y_scaleMax+y_scaleMin) * 0.002;
        if ((w.getY() > y_scaleMin+w.getHeight() && w.getY() < veryCloseToGround+w.getHeight() && w.getVelocity().getY() < -5)) {

            if (printActions)
                System.out.println("Ball landed at " + w.getVelocity().getY());
            return true;
        }
        return false;
    }
    private static void freezeAll(Vector<Ball> worldObjects, boolean cutV)
    {
        if (cutV)
            for (WorldObject w: worldObjects)
            {
                w.getVelocity().setY(1);
                w.getVelocity().setX(0);
            }
        else while(true)
            {
                if (!StdDraw.mousePressed()) break;
                /* Pause */
            }
    }
    public synchronized static void shuffle(Vector<Ball> worldObjects, double r, double yAxis)
    {
        double howMuch = 100;
        if (!mouseNotIn(r,yAxis)) {
            for (WorldObject w : worldObjects) {
                moveLeft(w, (Math.random() * howMuch / 10) - howMuch / 20);
                moveUp(w, Math.random() * howMuch/10);
            }
        }
    }
    public static void draw_buttons(double r, double yAxis)
    {
        StdDraw.setPenColor(penColor);
        /*Shuffle button*/
        StdDraw.filledSquare(x_scaleMax * 0.8, y_scaleMax * yAxis, r);
        StdDraw.text(x_scaleMax * 0.9, y_scaleMax * yAxis, "shuffle");
    }
    public static boolean mouseNotIn(double r, double scale)
    {
        // True if mouse is pressed and is outside the button's perimeter this function was given.
        boolean notIn;
        notIn = (StdDraw.mousePressed() && StdDraw.mouseX() > x_scaleMax*0.8 - r && StdDraw.mouseX() < x_scaleMax*0.8 + r)
                && (StdDraw.mouseY() < y_scaleMax*scale + r && StdDraw.mouseY() > y_scaleMax*scale - r);

        return !notIn;
    }
    public static boolean notTouchingButtons(double r)
    {
        return mouseNotIn(r,0.7) && mouseNotIn(r, 0.9 )
                && mouseNotIn(r, 0.8);
    }
    public static void initStdDraw()
    {
        StdDraw.setCanvasSize(600,600);
        StdDraw.show(0);
        StdDraw.setXscale(x_scaleMin, x_scaleMax);
        StdDraw.setYscale(y_scaleMin, y_scaleMax);
        StdDraw.clear(backgroundColor);
        StdDraw.setPenColor(penColor);
        StdDraw.setPenRadius(0.004);
    }
    public static void collide(int i, Vector<Ball> balls)
    {
        double EPS = 20;
        Ball b = balls.elementAt(i);
        if (b.getY() < vecCollideLine)
            return;

        if (Math.abs(b.getVelocity().getX()) < 10 && Math.abs(b.getVelocity().getY()) < 10)
            return;

        for (int j = 0; j < ballNum; j++)
        {
            if (j == i)
                continue;

            Ball ball = balls.get(j);
            double dt = dist(b,ball);
            if ( dt < EPS )
            {
                Velocity v1 = b.getVelocity();
                Velocity v2 = ball.getVelocity();
                Velocity temp = new Velocity();
                temp.setX((v1.getX() + v2.getX()) * 0.5 );
                temp.setY((v1.getY() + v2.getY()) * 0.5 );

                b.setVelocity(temp);
                ball.setVelocity(temp);

//                Velocity temp = b.getVelocity();
//                b.getVelocity().setX(ball.getVelocity().getX());
//                b.getVelocity().setY(ball.getVelocity().getY());
//
//                ball.getVelocity().setX(temp.getX());
//                ball.getVelocity().setY(temp.getY());

            }
        }
    }

    public static double dist(Ball b1, Ball b2)
    {
        double x = b1.getX() - b2.getX();
        x = x * x;
        double y = b1.getY() - b2.getY();
        y = y * y;
        return Math.sqrt(x + y);
    }

    public synchronized static Vector<Ball> initBallArray(int k)
    {
        Vector<Ball> b = new Vector<>();
        for (int i = 0; i < k; i++) {
            b.add(defBall());
        }
        return b;
    }
    public synchronized static Ball defBall()
    {
        Ball b =  new Ball((int) ((x_scaleMin+x_scaleMax)*0.5),-20);
        b.setR(Math.random() * radiusRange + minRadius);
        b.getVelocity().setX((Math.random() * (x_scaleMin+x_scaleMax)*0.034)-(x_scaleMin+x_scaleMax)*0.017);
        b.getVelocity().setY(40);
        b.setHeight(b.getR());
        return b;
    }
    public static void pause(long time)
    {
        if (time < 0) time = 1;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //end
}
