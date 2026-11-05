import java.awt.Color;

public class GlobalSettings {

    Color backgroundColor = new Color(40, 44, 52); 
    int frameSizeWidth = 1000;
    int frameSizeHeight = 800;
    double frameSizeWidthMultiplier = 1.25;
    Player currentPlayer = new Player("white");
    Color[] tilesColors = { new Color(238, 238, 210), new Color(186, 202, 68) }; // { new Color(238, 238, 210), new Color(186, 202, 68) }
    ChessPieces[] chessBoard;
    SaveFile saveFile = new SaveFile();
    String[][] moveLog = new String[15][2];
    
}
