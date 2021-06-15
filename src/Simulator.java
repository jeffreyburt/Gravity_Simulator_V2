public class Simulator implements Runnable {

    private final double gravityConstant = 6.67430 * Math.pow(10, -1);
    //todo tweak this value

    public void run() {
        while (Controller.simRunning) {
            for (Body calcBody : Controller.bodies) {
                for (Body otherBody : Controller.bodies) {
                    if (calcBody != otherBody) {
                        MyVector accelVector = calcAcceleration(calcBody, otherBody);
                        //todo can change this to +=
                        calcBody.velocityVector.x -= calcAccelWithUnits(accelVector.x);
                        calcBody.velocityVector.y -= calcAccelWithUnits(accelVector.y);
                    }
                }
            }

            for(Body body: Controller.bodies){
                body.xMeters += calcAccelWithUnits(body.velocityVector.x);
                body.yMeters += calcAccelWithUnits(body.velocityVector.y);
            }

            try {
                Thread.sleep(3, 333333);
            } catch (InterruptedException e) {
                e.printStackTrace();
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

    private double calcAccelWithUnits(double acceleration){
        return acceleration * 0.003333333 * Controller.timeMultiplier;
    }

}
