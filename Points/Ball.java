public class Ball extends WorldObject {
    private double r;

    public Ball(int x, int y) {
        super(x, y);
        setR(1);
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getR() {
        return this.r;
    }

    @Override
    public void setX() {
    }

    @Override
    public void setY() {
    }
}
