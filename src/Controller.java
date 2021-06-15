import javax.swing.Timer;
import java.util.ArrayList;

public class Controller {
    private static GUI gui;
    public static Runnable simulator;
    public static boolean simRunning = true;
    //todo make this a variable in the constructor?
    public static ArrayList<Body> bodies = new ArrayList<>();
    //todo do the final bit here or in the constructor?

    //parameters
    public static double xLimitMeters = 1000;
    public static double yLimitMeters = xLimitMeters;


    public static double initialVelocityLimit = 10;
    public static int numNodes = 20;

    //pixel to meter ratio, multiple pixel value to get meter distance, divide meters to get pixel distance
    public static double pixelsToMetersRatio = 1;

    //units: seconds per second
    //increase to increase the rate at which time passes
    public static double timeMultiplier = 10;
    ////////////////////////////////////////////////////////


    public static void main(String[] args){
        for (int i = 0; i < numNodes; i++) {
            bodies.add(genRandomBody());
        }


        gui = new GUI();
        simulator = new Simulator();
        Timer timer = new Timer(17, gui.simPanel);
        timer.setRepeats(true);
        timer.start();
        simulator.run();
    }

    public static void toggleSimulation() throws InterruptedException {
        System.out.println("toggled");
        simRunning = !simRunning;
        if(simRunning){
            System.out.println("eeeeeeeeeeeeeeeeeeeee");
            simulator = new Simulator();
            simulator.run();
        }
    }

    public static Body genRandomBody(){
        Body newBody = new Body(Math.random() * xLimitMeters, Math.random() * yLimitMeters);
        newBody.velocityVector.x = (Math.random() * initialVelocityLimit * 2) - initialVelocityLimit;
        newBody.velocityVector.y = (Math.random() * initialVelocityLimit * 2) - initialVelocityLimit;
        return newBody;
    }

}
