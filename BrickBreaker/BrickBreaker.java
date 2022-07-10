import edu.princeton.cs.introcs.StdDraw;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
// Sound effects from https://mixkit.co/free-sound-effects/hit/

public class BrickBreaker
{
    Level currentLevel;
    Level nextLevel;
    boolean run = true;
    public static final double EPS = 0.05;
    public static final double CENTER = 0.5;
    int points = 0;
    public static Color[] COLORS = {Color.black, Color.lightGray, Color.pink, Color.orange,
    Color.RED, Color.cyan, Color.yellow, Color.green};

    public BrickBreaker(Level currL, Level nextL)
    {
        currentLevel = currL;
        nextLevel = nextL;

        Thread d = new Thread(this::draw);
        Thread p = new Thread(this::play);

        d.start();
        p.start();
    }

    void draw()
    {
        while (run)
        {
            StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            currentLevel.b.draw();
            currentLevel.p.draw();

            double x = CENTER * 0.1;
            double y = CENTER * 1.8;
            for(Block b : currentLevel.blocks)
            {
                if (b.hp > 0)
                    b.draw(x, y);
                x += CENTER * 0.2;
                if ( x >= 1 )
                {
                    x = CENTER * 0.1;
                    y -= CENTER * 0.1;
                }
            }

            sleep();
            StdDraw.show(0);

            if (run)
            StdDraw.clear(StdDraw.BLACK);
        }
        System.out.println(Thread.currentThread().getName() + " [Draw] Terminated");
    }

    private void sleep()
    {
        try {
            Thread.sleep(1000/ 60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleep(int seconds)
    {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void play()
    {
        Ball ball = currentLevel.b;
        while (run)
        {
            ball.update();
            currentLevel.p.update();

            if (ball.posY <= 0 - 2 * EPS) // Ball out of bounds
            {
                run = false;
                sleep(1);

                System.out.println("Ball dropped");
                StdDraw.setPenColor(Color.white);
                Font f = StdDraw.getFont();
                f = f.deriveFont((float) (f.getSize() * 2.0));
                StdDraw.setFont(f);
                StdDraw.text(CENTER, CENTER, "the fuck bro?!");
                f = f.deriveFont((float) (f.getSize() * 0.5));
                StdDraw.setFont(f);
                StdDraw.text(CENTER, CENTER * 0.8, "press r to restart");
                while (!StdDraw.isKeyPressed(KeyEvent.VK_R)) {
                    Thread.onSpinWait();
                }
                resetOrAdv(true);
            }

//            if (ball.posY < currentLevel.p.posY - EPS)
//                continue;

            if (Math.abs(ball.posY - ball.r - currentLevel.p.posY + currentLevel.p.height) < EPS * 0.002)
            {
                if (ball.posX >= currentLevel.p.posX - currentLevel.p.width*1.1
                && ball.posX <= currentLevel.p.posX + currentLevel.p.width*1.1)
                {
                    ball.vel.y *= -1;
                    ball.vel.x = (ball.posX - currentLevel.p.posX) * EPS * 2;

                    hitSound("player_hit.wav");
                }
            }

            double x = CENTER * 0.1;
            double y = CENTER * 1.8;
            for(Block b : currentLevel.blocks)
            {
                if (b.hp > 0 && ball.isIn(ball.r, x, y, 0.04, 0.015 ))
                {
                    b.hp--;
                    hitSound("block_hit.wav");
                    if (ball.posX >= x - 0.04 && ball.posX <= x + 0.04)
                        ball.vel.y *= -1;
                    else
                        ball.vel.x *= -1;

                    b.col = COLORS[b.hp];

                    if (b.hp == 0)
                        points++;

                    if (points == currentLevel.blocks.length)
                    {
                        run = false;
                        hitSound("yay.wav");
                        StdDraw.show();
                        sleep(1);

                        StdDraw.clear(Color.BLACK);
                        System.out.println("Level complete");
                        StdDraw.setPenColor(Color.white);
                        Font f = StdDraw.getFont();
                        f = f.deriveFont((float) (f.getSize() * 2.0));
                        StdDraw.setFont(f);
                        StdDraw.text(CENTER, CENTER, "Nice!");
                        f = f.deriveFont((float) (f.getSize() * 0.5));
                        StdDraw.setFont(f);
                        StdDraw.text(CENTER, CENTER * 0.8, "press r to advance");
                        while (!StdDraw.isKeyPressed(KeyEvent.VK_R)) {
                            Thread.onSpinWait();
                        }
                        resetOrAdv(false);
                    }
                    break;
                }

                x += CENTER * 0.2;
                if ( x >= 1 )
                {
                    x = CENTER * 0.1;
                    y -= CENTER * 0.1;
                }
            }
            sleep();
        }
        System.out.println(Thread.currentThread().getName() + " [Play] Terminated");
    }

    private void hitSound(String sound)
    {
        // Thank you https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
        try {
            // Open an audio input stream.
            URL url = this.getClass().getClassLoader().getResource(sound);
            assert url != null;
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    private void resetOrAdv(boolean restart)
    {
        if (!restart)
        {
            currentLevel.diff++;
        }

        int rand;
        for (int i = 0; i < currentLevel.blocks.length; i++)
        {
            if (!restart && Math.random() * 2 > 1)
                rand = 1;
            else
                rand = 0;
            if (currentLevel.diff + rand< BrickBreaker.COLORS.length)
                currentLevel.blocks[i] = new Block(currentLevel.blocks[i].s_hp + rand, BrickBreaker.COLORS[currentLevel.blocks[i].s_hp + rand]);
            else
                currentLevel.blocks[i] = new Block(currentLevel.blocks[i].s_hp + rand, Color.lightGray);
        }

        run = true;
        points = 0;
        currentLevel.b.posY = CENTER;
        currentLevel.b.posX = currentLevel.p.posX;
        currentLevel.b.vel.x = 0.001 - Math.random()*0.002;

        Thread d = new Thread(this::draw);
        d.start();
    }

    public static void main(String[] args)
    {
        double startingPointX = 0.5;
        double startingPointY = 0.1;
        double startingSpeed = 0.008;
        double playerWidth = 0.1;
        Player p = new Player(startingPointX, startingPointY, playerWidth);
        Level l = new Level(p, 50, 5, startingSpeed);

        new BrickBreaker(l, null);
    }
}

class Level
{
    Player p;
    Ball b;
    Block[] blocks;
    int diff;
    double speed;

    public Level(Player pl, int blockNum, int blockDifficulty, double speed)
    {
        p = pl;
        blocks = new Block[blockNum];
        diff = blockDifficulty;
        this.speed = speed;
        b = new Ball(0.5, 0.1, 0.01, speed);
        for (int i = 0; i < blockNum; i++)
        {
            if (blockDifficulty < BrickBreaker.COLORS.length)
                blocks[i] = new Block(blockDifficulty, BrickBreaker.COLORS[diff]);
            else
                blocks[i] = new Block(blockDifficulty, Color.lightGray);
        }
    }
}

class Block
{
    int hp, s_hp;
    Color col;
    public Block(int h, Color c)
    {
        hp = h;
        s_hp = h;
        col = c;
    }

    public void draw(double x, double y)
    {
        StdDraw.setPenColor(col);
        StdDraw.filledRectangle(x, y, 0.04, 0.015);
    }

}

class Player
{
    double posX, posY, width, height = 0.01;
    public Player(double x, double y, double w)
    {
        posX = x;
        posY = y;
        width = w;
    }

    public void draw()
    {
        StdDraw.filledRectangle(posX, posY, width, height);
    }
    public void update()
    {
        posX = StdDraw.mouseX();
//        System.out.println("X " + StdDraw.mouseX());
//        System.out.println("Y " + StdDraw.mouseY());
    }
}

class Ball
{
    double posX, posY, r;
    Velocity vel;
    public Ball(double x, double y, double radius, double speed)
    {
        posX = x;
        posY = y;
        r = radius;
        vel = new Velocity(0.002, speed);
    }

    public void draw()
    {
        StdDraw.filledCircle(posX, posY, r * 0.9);
    }
    public void update()
    {
        posX += vel.x;
        posY += vel.y;

        if (posX <= 0 - BrickBreaker.EPS || posX >= 1 + BrickBreaker.EPS)
        {
            vel.x *= -1;
        }
        if (posY >= 1 + BrickBreaker.EPS)
        {
            vel.y *= -1;
        }


    }

    public boolean isIn(double r, double x, double y, double halfW, double halfH)
    {
        double eps = BrickBreaker.EPS * 0.005;
        return posX - r < x + halfW + eps && posX + r > x - halfW - eps &&
                posY + r > y - halfH + eps && posY - r < y + halfH - eps;
    }
}

class Velocity
{
    double x;
    double y;

    public Velocity(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    public void flip()
    {
        this.x = -x;
        this.y = -y;
    }
}
