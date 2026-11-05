import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class GuiFrame {

    JFrame frame;

    public JFrame buildFrame(GlobalSettings globalSettings){

        this.frame = new JFrame("Chess");
        this.frame.setLayout(new BorderLayout());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(new Dimension(globalSettings.frameSizeWidth, globalSettings.frameSizeHeight));
        this.frame.setResizable(false);
        this.frame.getContentPane().setBackground(globalSettings.backgroundColor);
        this.frame.setVisible(true);
    
        return frame;
      }

}
