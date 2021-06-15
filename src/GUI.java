import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI{
    public SimPanel simPanel;
    private ControlPanel controlPanel;


    //parameters
    private int bodyPixelSize = 30;

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

    }

    public class SimPanel extends JPanel implements MouseMotionListener, MouseListener, ActionListener {

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            drawBodies(g);

        }

        public void drawFrame(){
            this.repaint();
        }

        private void drawBodies(Graphics g){
            for(Body body: Controller.bodies){
                drawCenterCircle(body.xMeters / Controller.pixelsToMetersRatio, body.yMeters / Controller.pixelsToMetersRatio,
                        bodyPixelSize, g);
            }
        }

        private void drawCenterCircle(double x, double y, int diameterPixels, Graphics g){
            g.setColor(Color.RED);
            g.fillOval((int)(x + ((double) diameterPixels * 0.5)),(int)(y + ((double) diameterPixels * 0.5)), diameterPixels, diameterPixels );
        }




        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.repaint();
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
