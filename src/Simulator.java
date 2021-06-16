import java.util.LinkedList;
import java.util.List;

public class Simulator implements Runnable {
    private int futurePathDepth = 5000;

    private double gravityConstant = 6.67430e-11;
    //todo tweak this value

    public void run() {
        while (true) {
            if (Controller.simRunning) {
                calcFrameVelocityChange(Controller.bodies);
                calcFrameLocationChange(Controller.bodies, false);
                Controller.hasMoved = true;
            }else {
                if (Controller.computeFuturePath && Controller.hasMoved) {
                    LinkedList<Body> copyLinkedList = new LinkedList<>();
                    for (Body body : Controller.bodies) {
                        Body copyBody = new Body(body);
                        copyLinkedList.add(copyBody);
                        if(copyBody.futureCordList.size() > 0){
                            copyBody.futureCordList.clear();
                        }
                    }
                    for (int i = 0; i < futurePathDepth; i++) {
                        calcFrameVelocityChange(copyLinkedList);
                        calcFrameLocationChange(copyLinkedList, true);

                    }
                    Controller.newPath = true;
                    Controller.hasMoved = false;
                }
            }


            try {
                Thread.sleep(3, 333333);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void calcFrameVelocityChange(List<Body> bodyList) {
        for (Body calcBody : bodyList) {
            for (Body otherBody : bodyList) {
                if (calcBody != otherBody) {
                    MyVector accelVector = calcAcceleration(calcBody, otherBody);
                    calcBody.velocityVector.x -= calcAccelWithUnits(accelVector.x);
                    calcBody.velocityVector.y -= calcAccelWithUnits(accelVector.y);
                }
            }
        }
    }


    private void calcFrameLocationChange(List<Body> bodyList, boolean simulate) {
        for (Body body : bodyList) {
            if (!body.lockPos) {
                body.xMeters += calcAccelWithUnits(body.velocityVector.x);
                body.yMeters += calcAccelWithUnits(body.velocityVector.y);
                if (simulate) {
                    //System.out.println("eep");
                    body.futureCordList.add(new Coordinate(body.xMeters, body.yMeters));
                    //System.out.println(body.xMeters);
                }
            }
        }
    }

    private double calcDistance(Body body1, Body body2) {
        double xDistance = Math.pow(body1.xMeters - body2.xMeters, 2);
        double yDistance = Math.pow(body1.yMeters - body2.yMeters, 2);
        return Math.sqrt(xDistance + yDistance);
    }

    private MyVector calcAcceleration(Body body1, Body body2) {
        double totalMass = body1.massKG * body2.massKG;
        double distanceSquared = Math.pow(calcDistance(body1, body2), 2);
        double totalAcceleration = gravityConstant * (totalMass / distanceSquared);
        totalAcceleration /= body1.massKG;
        MyVector betweenPointVector = new MyVector(body1, body2);
        MyVector accelVector = new MyVector(betweenPointVector.unitX * totalAcceleration, betweenPointVector.unitY * totalAcceleration);
        return accelVector;

    }

    private double calcAccelWithUnits(double acceleration) {
        return acceleration * 0.003333333 * Controller.timeMultiplier;
    }

}
