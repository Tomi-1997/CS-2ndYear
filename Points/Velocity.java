public class Velocity {
    private double x;
    private double y;

    public Velocity(double x, double y)
    {
        setX(x);
        setY(y);
    }
    public Velocity()
    {
        setX(0);
        setY(0);
    }
    public double getX() {return x;};
    public double getY() {return y;};
    public void setX(double x){this.x=x;}
    public void setY(double y){this.y=y;}
    @Override
    public String toString(){return "WorldObject.Velocity=("+getX()+","+getY()+")";}
}