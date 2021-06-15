public class Body {
    public double xMeters;
    public double yMeters;
    //velocity is stored in Miles
    public MyVector velocityVector;
    public double massKG;

    public Body(double x, double y){
        this.xMeters = x;
        this.yMeters = y;
        velocityVector = new MyVector(1,1);
        massKG = 10000;
    }

}
