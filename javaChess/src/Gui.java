import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import javax.swing.* ;

public class Gui {


  public void setupGui(Player player, ChessPieces[] chessBoard){

    
    GlobalSettings globalSettings = new GlobalSettings();
    globalSettings.chessBoard = chessBoard;
    loadGlobalSettings(globalSettings);

    GuiFrame guiFrame = new GuiFrame();
    GuiMenu guiMenu = new GuiMenu();
    JFrame frame = guiFrame.buildFrame(globalSettings);
    
    UIManager.put("Button.select", new Color(0,0,0, 15)); // on press button effect

    frame.getContentPane().add(guiMenu.getGuiMenu(frame, globalSettings), BorderLayout.CENTER);
    frame.setVisible(true);

  }

  private void loadGlobalSettings(GlobalSettings globalSettings) {

    if (globalSettings.saveFile.doesFileExist("resolution.txt")) {
      globalSettings.frameSizeHeight = (int) (globalSettings.saveFile.getLoadedSettings(globalSettings,  new File("resolution.txt"))[0]);
      globalSettings.frameSizeWidth = (int) (globalSettings.saveFile.getLoadedSettings(globalSettings,  new File("resolution.txt"))[1]);
    } 
    if (globalSettings.saveFile.doesFileExist("color.txt")) {
      globalSettings.tilesColors = (Color[]) (globalSettings.saveFile.getLoadedSettings(globalSettings,  new File("color.txt")));
    }

  }

}