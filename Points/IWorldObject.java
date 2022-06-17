public interface IWorldObject {
    int getX();
    int getY();
    void setX();
    void setY();
    void gravity();
    void advanceVelocity();
    void slowDown();
    void update();
}