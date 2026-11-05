import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GuiWinnMenu {

    boolean checkmate = false; 

     public void buildWinnMenu(JFrame frame, String WinnerColor, GlobalSettings globalSettings) {
        JLabel glassPane = new JLabel();
        glassPane.setLayout(new GridLayout(3, 3));

        JLabel winnerKing = new JLabel();
        JLabel winnMessage = new JLabel();
        winnMessage.setForeground(new Color(190, 190, 190));
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        winnMessage.setFont(new Font(ge.getAvailableFontFamilyNames()[ge.getAvailableFontFamilyNames().length-1], Font.PLAIN, 20));
        winnMessage.setText(WinnerColor.substring(0, 1).toUpperCase() + WinnerColor.substring(1) + " wins!");

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource(WinnerColor.equals("white") ? "./assets/white-king.png": "./assets/black-king.png"));
        Image newimg = backgroundImage.getImage().getScaledInstance((int) (globalSettings.frameSizeHeight * 0.17), (int) (globalSettings.frameSizeHeight * 0.17), java.awt.Image.SCALE_SMOOTH);
        ImageIcon winnerKingIcon = new ImageIcon(newimg);
        winnerKing.setIcon(winnerKingIcon);

        

        for (int i = 0; i < 9; i++) {
            JPanel p = new JPanel();
            p.setOpaque(true);
            p.setBackground(new Color(0, 0, 0, 200));

            if (i == 4) {
                p.setBackground(new Color(33, 33, 33));

                JPanel menuContainer = new JPanel();
                menuContainer.setBackground(null);

                
                int strut = (int) (globalSettings.frameSizeHeight * 0.18);

                

                p.add(winnMessage);
                p.add(winnerKing);
                p.add(Box.createVerticalStrut(strut));

                JButton toMenuButton = createButton("  Menu  ");
                
                toMenuButton.addActionListener(e ->
            {  
                backToMenu(frame, globalSettings);           
            });

                JButton newGameButton = createButton("New Game");
                newGameButton.addActionListener(e -> {
                    createNewGame(frame, globalSettings);
                });

                menuContainer.add(toMenuButton);
                menuContainer.add(newGameButton);


                p.add(menuContainer);
                p.setOpaque(true);
            }
            glassPane.add(p);
        }

        frame.setGlassPane(glassPane);
        frame.getGlassPane().setVisible(true);
        
}


    private JButton createButton( String name ){

        JButton button = new JButton(name);
        button.setForeground(new Color(200, 200, 200));
        onHoverButton(button);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;

    }

    private void gameReset(JFrame frame, GlobalSettings globalSettings){

        frame.getContentPane().removeAll();
        globalSettings.currentPlayer = new Player("white");
        frame.setGlassPane(new JPanel());
        frame.getGlassPane().setVisible(false);
    }

    private void backToMenu(JFrame frame, GlobalSettings globalSettings){
        GuiMenu guiMenu = new GuiMenu(); 
        globalSettings.chessBoard = new ChessBoard().getChessBoard();
        gameReset(frame, globalSettings);
        frame.getContentPane().add(guiMenu.getGuiMenu(frame, globalSettings), BorderLayout.CENTER);
        
        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void createNewGame(JFrame frame, GlobalSettings globalSettings) {
        
        GuiBoard guiBoard = new GuiBoard();
        globalSettings.chessBoard = new ChessBoard().getChessBoard();
        globalSettings.moveLog = new String[15][2];
        gameReset(frame, globalSettings);
        frame.getContentPane().add(guiBoard.buildBoard(frame, globalSettings), BorderLayout.CENTER);
        guiBoard.buildNotationLogBoard(frame, globalSettings);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void onHoverButton(JButton button){

        button.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
            button.setForeground(Color.white);
            }

            public void mouseExited(MouseEvent e) {
            button.setForeground(new Color(200, 200, 200));
            }
        });
    }
    
}
