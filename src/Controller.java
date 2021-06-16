import javax.swing.Timer;
import java.security.PublicKey;
import java.util.ArrayList;

public class Controller {
    private static GUI gui;
    public static Runnable simulator;
    public static Thread simulatorThread;
    public static boolean simRunning = true;
    //todo make this a variable in the constructor?
    public static ArrayList<Body> bodies = new ArrayList<>();
    //todo do the final bit here or in the constructor?
    public static boolean computeFuturePath = true;
    public static boolean hasMoved = false;
    public static boolean newPath = false;

    //parameters
    public static double xLimitMeters = 5e8;
    public static double yLimitMeters = xLimitMeters;


    public static double initialVelocityLimit = 0;
    public static int numNodes = 0;

    //pixel to meter ratio, multiple pixel value to get meter distance, divide meters to get pixel distance
    public static double pixelsToMetersRatio = 480590;

    //units: seconds per second
    //increase to increase the rate at which time passes
    public static double timeMultiplier = 150000;
    ////////////////////////////////////////////////////////


    public static void main(String[] args){
        for (int i = 0; i < numNodes; i++) {
            bodies.add(genRandomBody());
        }

        Body earth = new Body(400295176, 240295176, 5.972e24);
        earth.lockPos = true;
        bodies.add(earth);

        Body moon = new Body(earth.xMeters - 240295176, earth.yMeters, 7.348e22);
        moon.velocityVector.y = 1000;
        bodies.add(moon);

//        Body moon2 = new Body(earth.xMeters + 240295176, earth.yMeters, 7.348e22);
//        moon2.velocityVector.y = -1000;
//        bodies.add(moon2);

        gui = new GUI();
        simulator = new Simulator();
        simulatorThread = new Thread(simulator);
        Timer timer = new Timer(17, gui.simPanel);
        timer.setRepeats(true);
        timer.start();
        simulatorThread.start();
    }

    public static void toggleSimulation() throws InterruptedException {
        System.out.println("toggled");
        simRunning = !simRunning;
    }

    public static Body genRandomBody(){
        Body newBody = new Body(Math.random() * xLimitMeters, Math.random() * yLimitMeters);
        newBody.velocityVector.x = (Math.random() * initialVelocityLimit * 2) - initialVelocityLimit;
        newBody.velocityVector.y = (Math.random() * initialVelocityLimit * 2) - initialVelocityLimit;
        return newBody;
    }

}
