import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Body {
    public double xMeters;
    public double yMeters;
    //velocity is stored in Miles
    public MyVector velocityVector;
    public double massKG;
    public boolean lockPos;
    public LinkedList<Coordinate> futureCordList = new LinkedList<>();

    public Body(double x, double y){
        this.xMeters = x;
        this.yMeters = y;
        velocityVector = new MyVector(1,1);
        lockPos = false;
        massKG = 10000000;
    }

    public Body(Body parentBody){
        this.xMeters = parentBody.xMeters;
        this.yMeters = parentBody.yMeters;
        this.velocityVector = new MyVector(parentBody.velocityVector);
        this.massKG = parentBody.massKG;
        this.lockPos = parentBody.lockPos;
        this.futureCordList = parentBody.futureCordList;
    }

    public Body(double x, double y, double mass){
        this.xMeters = x;
        this.yMeters = y;
        velocityVector = new MyVector(1,1);
        lockPos = false;
        massKG = mass;
    }



}
