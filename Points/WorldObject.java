public abstract class WorldObject implements IWorldObject
{
    public static int G = 1;
    public static double friction = 0.7;
    protected int x;
    protected int y;
    protected Velocity velocity;
    protected double height;

    public WorldObject(int x, int y) {
        setX(x);
        setY(y);
        setVelocity(new Velocity(0,0));
        height = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
            this.x = x;
    }

    public void setY(int y) {
            this.y = y;
    }

    public Velocity getVelocity() { return velocity; }
    public void setVelocity(Velocity v) { this.velocity = v; }

    public void setHeight(double h)
    {
        this.height = h;
    }

    public double getHeight()
    {
        return this.height;
    }

    public void gravity() {
        if (getY() > 0)
        {
            Velocity v = getVelocity();
            // Decrease height
            double dragDown = v.getY() - WorldObject.G*0.2;
            v.setY(dragDown);
        }
        else if (getY() <= 0)
        {
            Velocity v = getVelocity();
            // Object at ground with no upward velocity
            if (v.getY() < 0 && v.getY() > -5) v.setY(0);
        }
    }

    public void advanceVelocity() {
        setX((int)(getX() + getVelocity().getX()));
        setY((int)(getY() + getVelocity().getY()));
    }

    public void slowDown() {
        Velocity v = getVelocity();
        // slows down vertical speed
        if (getY() < getHeight()/2)
            v.setX(v.getX()*friction);
    }

    public void update() {
        gravity();
        advanceVelocity();
        slowDown();
    }
}