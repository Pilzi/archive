import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class GuiSettings {

    JPanel guiMenu;
    JPanel colorShowcase;
    JButton[] colorButtonArr;
    String lastPanel;
    
    private void buildSettings(JFrame frame, GlobalSettings globalSettings){

        guiMenu = new JPanel();
        guiMenu.setLayout(new GridLayout(5, 1));
        guiMenu.setBackground(new Color(33, 33, 33));

        for (int tileNum = 0; tileNum <= 4; tileNum++){
            JPanel gridLayoutTile = new JPanel();
            
            gridLayoutTile.setBackground(globalSettings.backgroundColor);

            if (tileNum == 0){
                JLabel headline = getHeadline();
                fontSizeListener(frame, headline, globalSettings, true);
                gridLayoutTile.add(headline);
            }

            if (tileNum == 1){
                String[] resulutionArr = {"600px", "800px", "1200px"};
                gridLayoutTile.add(buildSetting( frame,"Resolution", globalSettings, resulutionArr));
                
            }
            if (tileNum == 2){
                String[] resulutionArr = {"Green", "Purple", "Yellow"};
                gridLayoutTile.add(buildSetting( frame,"Board color", globalSettings, resulutionArr));
                
            }
            if (tileNum == 3){
                gridLayoutTile.add(getColorShowcase(globalSettings));
                
                colorShowcase = gridLayoutTile;
                
            }
            if (tileNum == 4 ){
                String[] resulutionArr = {"Close"};
                gridLayoutTile.add(buildSetting( frame,"exit", globalSettings, resulutionArr));
                
            }


            

            guiMenu.add(gridLayoutTile);
        }

        
        
    

    }

    private void fontSizeListener(JFrame frame, Component component, GlobalSettings globalSettings, boolean headline){
        frame.getContentPane().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                component.setFont(new Font("Arial", Font.PLAIN, headline ? (int) (globalSettings.frameSizeHeight*0.037):(int) (globalSettings.frameSizeHeight*0.03)));
                
                SwingUtilities.updateComponentTreeUI(frame);
            }
        });
    }

    private JPanel getColorShowcase(GlobalSettings globalSettings){

        JPanel showcaseBoard = new JPanel();
        showcaseBoard.setBackground(globalSettings.backgroundColor);
        showcaseBoard.setLayout(new GridLayout(6, 6));
        showcaseBoard.setBorder(new EmptyBorder(0,0,0,0));
        int tileSize = (int) ((globalSettings.frameSizeHeight*0.145));
        showcaseBoard.setPreferredSize(new Dimension(tileSize, tileSize));

        Color color = globalSettings.tilesColors[0];
        for (int row = 6; row >= 1; row--) {
            color = color == globalSettings.tilesColors[0] ? globalSettings.tilesColors[1] : globalSettings.tilesColors[0];
        
            for (int tile = 1; tile <= 6; tile++) {
                JButton showcaseTile = new JButton();
                
                showcaseTile.setBorderPainted(false);
            
                showcaseTile.setBackground(color);
                showcaseBoard.add(showcaseTile);
                color = color == globalSettings.tilesColors[0] ? globalSettings.tilesColors[1]:globalSettings.tilesColors[0];
            }
        }
        
        return showcaseBoard;
    }

    public JLabel getHeadline(){

        JLabel headline = new JLabel("Settings");
        headline.setForeground(Color.white);
        headline.setFont(new Font("Serif", Font.BOLD, 27));
        
        return headline;
    }

    public JPanel buildSetting(JFrame frame,String setting,  GlobalSettings globalSettings, String[] arr){

        

        JLabel headline = new JLabel(setting == "exit" ? "":setting);
        headline.setFont(new Font("Arial", Font.BOLD, (int) (globalSettings.frameSizeHeight*0.037)));
        fontSizeListener(frame, headline, globalSettings, true);
        headline.setForeground(Color.YELLOW);
        headline.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel resolutionBody = new JPanel();
        resolutionBody.setLayout(new GridLayout(3, 1));
        

        resolutionBody.setBackground(globalSettings.backgroundColor);
      
        

        resolutionBody.add(headline);
        resolutionBody.add(new JLabel());
        resolutionBody.add(getSelectionBox(arr, setting, globalSettings, frame));

        return resolutionBody;
    }



    public JPanel getSettings(JFrame frame, GlobalSettings globalSettings, String p){

        this.lastPanel = p;
        if (this.guiMenu == null){
            buildSettings(frame, globalSettings);
        }

        return guiMenu;
    }

    public JPanel getSelectionBox (String[] arr, String setting, GlobalSettings globalSettings, JFrame frame){

        JPanel selectionBox = new JPanel();
        selectionBox.setBackground(globalSettings.backgroundColor);
        selectionBox.setAlignmentX(SwingConstants.CENTER);
        selectionBox.setAlignmentY(0);

        for (String entry : arr){
            
            JButton entryButton = new JButton();
            entryButton.setText(entry);
            entryButton.setForeground(new Color(200, 200, 200));
            entryButton.setFont(new Font("Arial", Font.PLAIN, (int) (globalSettings.frameSizeHeight*0.03)));
            entryButton.setBackground(globalSettings.backgroundColor);
            entryButton.setForeground(Integer.toString(globalSettings.frameSizeHeight).equals(entryButton.getText().split("px")[0]) ? Color.YELLOW: new Color(200, 200, 200));
            entryButton.setForeground(isBoardColor(globalSettings, entry) && setting == "Board color" ? Color.YELLOW:entryButton.getForeground());
            entryButton.setFocusable(false);
            entryButton.setRolloverEnabled(false);
            entryButton.setBorder(new EmptyBorder(0,0,0,0));
            
            
            entryButton.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    entryButton.setForeground(entryButton.getForeground().equals(new Color(200, 200, 200)) ?  Color.white:new Color(255,255,0, 140));
                }
    
                public void mouseExited(MouseEvent e) {
                    entryButton.setForeground(entryButton.getForeground().equals(new Color(255,255,0, 140)) ? Color.YELLOW: new Color(200, 200, 200));
                }
            });

            frame.getContentPane().addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    
                    entryButton.setForeground(Integer.toString(globalSettings.frameSizeHeight).equals(entryButton.getText().split("px")[0]) ? Color.YELLOW: entryButton.getForeground());
                    
                    SwingUtilities.updateComponentTreeUI(frame);
                }
            });
            setListener(entryButton, setting, globalSettings, frame, selectionBox);
            selectionBox.add(entryButton);
            if (entry != arr[arr.length - 1]){
                selectionBox.add(Box.createHorizontalStrut(20));
            }
            
        }

        return selectionBox;
    }

    public boolean isBoardColor(GlobalSettings globalSettings,String color){
       
                        
                        if (globalSettings.tilesColors[0].equals(new Color(135, 113, 177)) && color == "Purple" ){
                            return true;
                        }
                         else if (globalSettings.tilesColors[0].equals(new Color(238, 238, 210)) && color == "Green" ){
                            return true;
                      
                        } else if (globalSettings.tilesColors[0].equals(new Color(184,139,74)) && color == "Yellow" ){
                            return true;
                        }
                        return false;
                    }
    


    private void setListener(JButton button, String setting, GlobalSettings globalSettings, JFrame frame, JPanel selectionBox){


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (setting) {
                    case "Resolution":
                        globalSettings.frameSizeHeight = Integer.parseInt(button.getText().split("px")[0]);
                        globalSettings.frameSizeWidth = (int) (globalSettings.frameSizeHeight * globalSettings.frameSizeWidthMultiplier);
                        frame.setSize(new Dimension(globalSettings.frameSizeWidth, globalSettings.frameSizeHeight));
                        globalSettings.saveFile.saveFile(new Object[] {globalSettings.frameSizeHeight, globalSettings.frameSizeWidth},  new File("resolution.txt"));
                        break;
                
                    case "Board color":
                        if (button.getText().equals("Purple") ){
                            globalSettings.tilesColors[0] = new Color(135, 113, 177);
                            globalSettings.tilesColors[1] = new Color(185, 192, 213);
                        } else if (button.getText().equals("Green") ){
                            globalSettings.tilesColors[0] =  new Color(238, 238, 210);
                            globalSettings.tilesColors[1] =  new Color(186, 202, 68);
                        } else if (button.getText().equals("Yellow") ){
                            globalSettings.tilesColors[0] =  new Color(184,139,74);
                            globalSettings.tilesColors[1] =  new Color( 227,193,111);
                        }
                        globalSettings.saveFile.saveFile(globalSettings.tilesColors,  new File("color.txt"));
                        button.setForeground(isBoardColor(globalSettings, button.getText()) && setting == "Board color" ? new Color(255,255,0, 140):button.getForeground());
                        break;
                    case "exit": 
                        frame.getContentPane().removeAll();
                        GuiMenu guiMenu = new GuiMenu(); 
                        GuiBoard guiBoard = new GuiBoard();
            
                        if (lastPanel.equals("board")){
                            frame.getContentPane().add(guiBoard.buildBoard(frame, globalSettings), BorderLayout.CENTER);
                            guiBoard.buildNotationLogBoard(frame, globalSettings);
                        } else {
                            frame.getContentPane().add(guiMenu.getGuiMenu(frame, globalSettings), BorderLayout.CENTER);
                        }

                        
                        break;
                }

                colorShowcase.removeAll();
                        
                colorShowcase.add(getColorShowcase(globalSettings));
                
                
                for (Component button : selectionBox.getComponents()){
                    button.setForeground(button.getForeground().equals(new Color(255,255,0, 140)) ? new Color(255,255,0, 140): new Color(200, 200, 200));
                    
                    fontSizeListener(frame, button, globalSettings, false);
                }
                
                SwingUtilities.updateComponentTreeUI(frame);
            }
        });
    
    }

}
