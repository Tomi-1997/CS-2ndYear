// 2D "Simulation" of balls affected by gravity
 //**OLD VERSION, NEW ONE IS BouncingShapes**//
import com.google.gson.Gson;
import edu.princeton.cs.introcs.StdDraw;
import com.google.gson.*;
import java.awt.Color;
import java.io.*;
import java.util.ArrayList;

public class main {
    static boolean printActions = false, displayInfo = true;

    // StdDraw stuff
    static char buttonToQuit = 'q';
    static Color backgroundColor = Color.black,
            penColor = Color.white;

    static int x_scaleMin = 0, x_scaleMax = 1000,
            y_scaleMin = 0, y_scaleMax = 1000;
    static long frames = 1000/120;

    // Shape stuff
    static int ballLimit = 50;
    static int radiusRange = 20, minRadius = 1;

    public static void main(String[] args) {

        String filename = "regularBall.txt";

        ArrayList<Ball> b = new ArrayList<Ball>();
        loadJson(filename, b);


        for (int i = 0; i< 10; i++)
            b.add(defBall());

        initStdDraw();
        draw(b, frames);

    }

    public static void loadJson(String filename, ArrayList<Ball> b)
    {
        String input = "";
        String s;
        try {
            FileReader f = new FileReader(filename);
            BufferedReader readFile = new BufferedReader(f);
            s = readFile.readLine();
            while (s != null)
            {
                input = input + s;
                s = readFile.readLine();
            }
            f.close();
            readFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Ball ball = gson.fromJson(input, Ball.class);
        b.add(ball);
    }
    public static void saveJson(String filename, Ball b)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String ballDesc="";
        ballDesc = gson.toJson(b);

        try {
            FileWriter file = new FileWriter (filename);
            file.write(ballDesc);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void draw(ArrayList<Ball> worldObjects, long frames) {
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(penColor);
        while (!keyPressed(buttonToQuit)) {
            int objectIndex = 0;
            for (WorldObject w : worldObjects) {

                // make object fall down with speed increasing depending on the radius
                if (w.getY() > 0)
                    fall(w, worldObjects.get(objectIndex).getR());

                // display vector info while moving
                if (displayInfo && w.getY()> 0 && w.getVelocity().getX() != 0)
                    showVelocity(worldObjects, objectIndex);

                StdDraw.circle(w.getX(), w.getY(), worldObjects.get(objectIndex).getR());

                double approxOfNextHeight = worldObjects.get(objectIndex).getVelocity().getY() + w.getY();
                // if ball is about to touch ground or too fast to determine if it touches ground then
                // make it bounce
                if (aboutToLand(w) || approxOfNextHeight < -5) {

                    // to prevent an object from splatting on ground from falling too fast
                    if (w.getY() < w.getHeight()*2 + 10) {
                        double min = Math.min(Math.abs(w.getVelocity().getY() * 0.7), 60);
                        w.getVelocity().setY(Math.abs(w.getVelocity().getY() * 0.7));
                    }
                }
                bounceOffWall(w);
                objectIndex++;
                w.update();
            }

            // Buttons  -->  funcName(array , square radius , place in y axis)
            double boxRadius= 20;
            shuffle(worldObjects, boxRadius, 0.9);
            addBalls((worldObjects), boxRadius, 0.8);
            clearAll(worldObjects, boxRadius, 0.7);

            // print "q to quit"
            quitMessage();

            // if mouse is pressed then : {various actions}
            if (StdDraw.mousePressed() && notTouchingButtons(boxRadius)) {
                // Press text
                StdDraw.text(StdDraw.mouseX(), StdDraw.mouseY() + 10,
                        "mousePress ("+(int)StdDraw.mouseX()+","+ (int)StdDraw.mouseY()+")");
                // freeze balls in place (kill momentum or keep it going)
                boolean cutVelocity = false;
                freezeAll(worldObjects, cutVelocity);
            }

            refresh(frames);
        }
        System.exit(0);
    }

    private static boolean keyPressed(char buttonToQuit) {
        String c = buttonToQuit+"";
        String cl = c.toLowerCase();
        String cu = c.toUpperCase();
        return StdDraw.isKeyPressed((int) cu.charAt(0)) || StdDraw.isKeyPressed((int) cl.charAt(0));
    }

    private static void quitMessage() {
        double x = (x_scaleMax - x_scaleMin)*0.08;
        double y = y_scaleMax;
        StdDraw.text(x,y,"Press "+buttonToQuit+" to quit");
    }

    public static void bounceOffWall(WorldObject w) {
        if (w.getX() < x_scaleMin + x_scaleMax*0.01)
            w.getVelocity().setX(Math.abs(w.getVelocity().getX()));

        if (w.getX() > x_scaleMax - x_scaleMin*0.01)
            w.getVelocity().setX((Math.abs(w.getVelocity().getX())) * -1);
    }

    public static void refresh(long f) {
        try {
            Thread.sleep((f));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StdDraw.clear(Color.black);
    }

    public static void moveRight(WorldObject w, double byHowMuch) {
        Velocity v = w.getVelocity();
        v.setX(v.getX() + byHowMuch);
        if (printActions)
            System.out.println("Ball pushed right");
    }

    public static void moveLeft(WorldObject w, double byHowMuch) {
        Velocity v = w.getVelocity();
        v.setX(v.getX() - byHowMuch);
        if (printActions)
            System.out.println("Ball pushed right");
    }

    public static void moveUp(WorldObject w, double byHowMuch) {
        Velocity v = w.getVelocity();
        v.setY(v.getY() + byHowMuch * 0.4);

        if (printActions)
            System.out.println("Ball pushed upwards");

    }

    public static void fall(WorldObject w, double R) {
        // Decrease Y velocity with relation to radius
        w.getVelocity().setY(w.getVelocity().getY() - (R * 0.008));
    }

    public static boolean aboutToLand(WorldObject w) {
        double veryCloseToGround = (y_scaleMax+y_scaleMin) * 0.002;
        if ((w.getY() > y_scaleMin+w.getHeight() && w.getY() < veryCloseToGround+w.getHeight() && w.getVelocity().getY() < -5)) {

            if (printActions)
                System.out.println("Ball landed at " + w.getVelocity().getY());
            return true;
        }
        return false;
    }

    private static void freezeAll(ArrayList<Ball> worldObjects, boolean cutV) {
        if (cutV)
            for (int i = 0; i < worldObjects.size(); i++)
            {
                worldObjects.get(i).getVelocity().setX(0);
                worldObjects.get(i).getVelocity().setY(1);
            }
        else
        {
            while(StdDraw.mousePressed()) {}
        }
    }

    public static void shuffle(ArrayList<Ball> worldObjects, double r, double yAxis)
    {
        double howMuch = 100;
        StdDraw.setPenColor(penColor);
        StdDraw.filledSquare(x_scaleMax * 0.8, y_scaleMax * yAxis, r);
        StdDraw.text(x_scaleMax * 0.9, y_scaleMax * yAxis, "shuffle");
        if (!mouseNotIn(r,yAxis)) {
            for (WorldObject w : worldObjects) {
                moveLeft(w, (Math.random() * howMuch / 4) - howMuch / 8);
                moveUp(w, Math.random() * howMuch/3);
            }
        }
    }

    public static void addBalls(ArrayList<Ball> worldObjects, double r, double yAxis)
    {
        StdDraw.setPenColor(penColor);
        StdDraw.filledSquare(x_scaleMax * 0.8, y_scaleMax * yAxis, r);
        StdDraw.text(x_scaleMax * 0.9, y_scaleMax * yAxis, "add balls");
        if (worldObjects.size() < ballLimit && !mouseNotIn(r,yAxis)) {
            worldObjects.add(new Ball((int)(Math.random()*(x_scaleMax-x_scaleMin)),
                    (int) (Math.random()*(y_scaleMin-(y_scaleMax*0.01))) ));
            worldObjects.get(worldObjects.size()-1).setR(Math.random()*radiusRange + minRadius);
            worldObjects.get(worldObjects.size()-1).getVelocity().setX((Math.random() * (x_scaleMin+x_scaleMax)*0.016)-(x_scaleMin+x_scaleMax)*0.008);
            worldObjects.get(worldObjects.size()-1).getVelocity().setY(30);
            worldObjects.get(worldObjects.size()-1).setHeight(worldObjects.get(worldObjects.size()-1).getR());
        }

    }
    public static void clearAll(ArrayList<Ball> worldObjects, double r, double yAxis)
    {
        StdDraw.setPenColor(penColor);
        StdDraw.filledSquare(x_scaleMax * 0.8, y_scaleMax * yAxis, r);
        StdDraw.text(x_scaleMax * 0.9, y_scaleMax * yAxis, "remove all");
        if (!mouseNotIn(r,yAxis)) {
            while(worldObjects.size() > 0)
                worldObjects.remove(0);
        }
    }
    public static boolean mouseNotIn(double r, double scale)
    {
        if (StdDraw.mousePressed() && StdDraw.mouseX() > x_scaleMax*0.8 - r && StdDraw.mouseX() < x_scaleMax*0.8 + r &&
                StdDraw.mouseY() < y_scaleMax*scale + r && StdDraw.mouseY() > y_scaleMax*scale - r)
            return false;
        return true;
    }

    public static boolean notTouchingButtons(double r)
    {
        return mouseNotIn(20,0.7) && mouseNotIn(20, 0.9 )
                && mouseNotIn(20, 0.8);
    }
    public static void initStdDraw() {
        StdDraw.setCanvasSize(700,700);
        StdDraw.show();
        StdDraw.setXscale(x_scaleMin, x_scaleMax);
        StdDraw.setYscale(y_scaleMin, y_scaleMax);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
    }

    public static ArrayList<Ball> initBallArray(int k) {
        ArrayList<Ball> b = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            b.add(defBall());
        }
        return b;
    }

    public static Ball defBall() {
        Ball b =  new Ball((int) ((x_scaleMin+x_scaleMax)*0.5),(int)((y_scaleMin+y_scaleMax)*0.3));
        b.setR(Math.random() * radiusRange + minRadius);
        b.getVelocity().setX((Math.random() * (x_scaleMin+x_scaleMax)*0.016)-(x_scaleMin+x_scaleMax)*0.008);
        b.getVelocity().setY(30);
        b.setHeight(b.getR());
        return b;
    }

    public static void showVelocity(ArrayList<Ball> w, int objectIndex)
    {
        StdDraw.text(w.get(objectIndex).getX(),w.get(objectIndex).getY()+w.get(objectIndex).getR() + 20,
                ((int)w.get(objectIndex).getVelocity().getX()+","+(int)w.get(objectIndex).getVelocity().getY()));
    }
    //end
}
