import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class GUI{
    public SimPanel simPanel;
    private ControlPanel controlPanel;


    //parameters
    private int bodyPixelSize = 30;

    private double mouseIncrementPercent = 0.001;


    private Body selectedBody = null;

    public GUI(){
        JFrame mainFrame = new JFrame("gravity sim");
        JPanel mainPanel = new JPanel(new BorderLayout());
        simPanel = new SimPanel();
        controlPanel = new ControlPanel();

        mainPanel.add(simPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        simPanel.setPreferredSize(new Dimension(1900, 1000));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.addMouseListener(simPanel);
        mainFrame.addMouseMotionListener(simPanel);
        mainFrame.addMouseWheelListener(simPanel);

    }

    public class SimPanel extends JPanel implements MouseMotionListener, MouseListener, ActionListener, MouseWheelListener {

        private BufferedImage futurePathImage;


        public void paintComponent(Graphics g){
            super.paintComponent(g);


            if(Controller.computeFuturePath && !Controller.simRunning){
                if(Controller.newPath){
                    futurePathImage  = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D imageGraphics = futurePathImage.createGraphics();
                    imageGraphics.setColor(Color.LIGHT_GRAY);
                    imageGraphics.fillRect(0,0,getWidth(), getHeight());
                    imageGraphics.setColor(Color.BLACK);
                    for(Body body: Controller.bodies){
                        if(body.futureCordList.size() > 0) {
                            Coordinate firstCord = body.futureCordList.getFirst();
                            for (Coordinate coordinate : body.futureCordList) {
                                imageGraphics.drawLine((int) (firstCord.x / Controller.pixelsToMetersRatio),
                                        (int) (firstCord.y / Controller.pixelsToMetersRatio),
                                        (int) (coordinate.x / Controller.pixelsToMetersRatio),
                                        (int) (coordinate.y / Controller.pixelsToMetersRatio));
                                firstCord = coordinate;
                            }
                        }
                    }
                    Controller.newPath = false;
                }
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(futurePathImage, 0, 0, null);
            }
            drawBodies(g);

        }


        private void drawBodies(Graphics g){
            for(Body body: Controller.bodies){
                     drawCenterCircle(body.xMeters / Controller.pixelsToMetersRatio, body.yMeters / Controller.pixelsToMetersRatio,
                            bodyPixelSize, g);
            }
            if(selectedBody != null){
                drawCenterCircle(selectedBody.xMeters / Controller.pixelsToMetersRatio, selectedBody.yMeters / Controller.pixelsToMetersRatio,
                        bodyPixelSize, g);
            }
        }

        private void drawCenterCircle(double x, double y, int diameterPixels, Graphics g){
            g.setColor(Color.RED);
            g.fillOval((int)(x - ((double) diameterPixels * 0.5)),(int)(y - ((double) diameterPixels * 0.5)), diameterPixels, diameterPixels );
        }




        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            Controller.simRunning = false;
            Body closestBody = null;
            double minDistance = Double.MAX_VALUE;
            for(Body body : Controller.bodies){
                double distance = calcDistance(body, e);
                if(distance <= minDistance){
                    closestBody = body;
                }
            }
            selectedBody = closestBody;

        }
        private double calcDistance(Body body, MouseEvent e) {
            double xDistance = Math.pow(body.xMeters - (e.getX() * Controller.pixelsToMetersRatio), 2);
            double yDistance = Math.pow(body.yMeters - (e.getY() * Controller.pixelsToMetersRatio), 2);
            return Math.sqrt(xDistance + yDistance);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("released");
            selectedBody = null;
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if(selectedBody != null){
                selectedBody.xMeters = e.getX() * Controller.pixelsToMetersRatio;
                selectedBody.yMeters = e.getY() * Controller.pixelsToMetersRatio;
                Controller.hasMoved = true;
            }

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.repaint();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            //todo implement later
        }
    }

    public class ControlPanel extends JPanel {
        ToggleSimButton toggleSimButton;

        public ControlPanel(){
            toggleSimButton = new ToggleSimButton();
            this.add(toggleSimButton);
            this.setVisible(true);
        }

    }

    public class ToggleSimButton extends JButton implements ActionListener{

        public ToggleSimButton(){
            super("Toggle Simulation");
            addActionListener(this);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("buttoned");
            try {
                Controller.toggleSimulation();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

}
