import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    int radius;
    double multipliyer;

    public RoundedPanel(int radius, double multipliyer){
        this.radius = radius;
        
        this.multipliyer = multipliyer;
    }


    @Override
     protected void paintComponent(Graphics g) {
        this.setOpaque(false);
        super.paintComponent(g);
        
        Dimension arcs = new Dimension(radius, radius); //Border corners arcs {width,height}, change this to whatever you want
        int width = (int) (getWidth() * multipliyer);
        int height = (int) (getHeight() * multipliyer);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        graphics.setColor(getBackground());
		graphics.fillRoundRect((this.getHeight()/2) - height/2, (this.getWidth()/2) - width/2, width-1, height-1, arcs.width, arcs.height);
     }
    
}