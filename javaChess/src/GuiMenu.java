import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;

public class GuiMenu {

    JLabel guiMenu;

    private void buildMenu(JFrame frame, GlobalSettings globalSettings) {
        JLabel menu = new JLabel();
        menu.setLayout(new GridLayout(6, 3));
        menu.setSize(globalSettings.frameSizeWidth, globalSettings.frameSizeHeight);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("./assets/background.png"));
        Image newimg = backgroundImage.getImage().getScaledInstance(globalSettings.frameSizeWidth, globalSettings.frameSizeHeight, java.awt.Image.SCALE_SMOOTH);
        menu.setIcon(new ImageIcon(newimg));

        String[] menuItems = { "New Game", "Continue", "Settings", "Exit" };

        for (String menuEntry : menuItems) {
            JButton button = new JButton(menuEntry);

            button.setForeground(new Color(200, 200, 200));
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            onHoverButton(button);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (menuEntry.equals("New Game")) {
                        createNewGame(frame, menu, globalSettings);
                    } else if (menuEntry.equals("Settings")) {
                        createSettings(frame, globalSettings, menu);
                    } else if (menuEntry.equals("Exit")) {
                        frame.dispose();
                    } else if (menuEntry.equals("Continue")){

                        globalSettings.chessBoard = (ChessPieces[]) (globalSettings.saveFile.getLoadedSettings(globalSettings,  new File("chessBoardSave.txt")));
                        globalSettings.moveLog = (String[][]) (globalSettings.saveFile.getLoadedSettings(globalSettings,  new File("moveLog.txt")));
                        setCurrentPlayer(globalSettings);
                        createNewGame(frame, menu, globalSettings);
                        
                    }
                }
            });
            menu.add(button);
        }
        this.guiMenu = menu;
    }

    public JLabel getGuiMenu(JFrame frame, GlobalSettings globalSettings){
      if (this.guiMenu == null){
        buildMenu( frame, globalSettings);
      }
      return guiMenu;
    }

    private void createSettings(JFrame frame, GlobalSettings globalSettings, JLabel menu){
        GuiSettings guiSettings = new GuiSettings();
        frame.add(guiSettings.getSettings(frame, globalSettings, "menu"), BorderLayout.CENTER);
        frame.remove(menu);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void createOnlineMenu(){



    }

    private void createNewGame(JFrame frame, JLabel menu, GlobalSettings globalSettings) {
        GuiBoard guiBoard = new GuiBoard();
        frame.getContentPane().remove(menu);
        frame.getContentPane().add(guiBoard.buildBoard(frame, globalSettings), BorderLayout.CENTER);
        guiBoard.buildNotationLogBoard(frame, globalSettings);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void onHoverButton(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.white);
            }

            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(200, 200, 200));
            }
        });
    }

    private void setCurrentPlayer(GlobalSettings globalSettings){

        for (String[] moves : globalSettings.moveLog){
      
            if (moves[0] == null){
                globalSettings.currentPlayer.PlayerColor = "white";
                break;
      
            } else if (moves[1] == null){
              
                globalSettings.currentPlayer.PlayerColor = "black";
                break;
            }
      
          } 
    }
}
