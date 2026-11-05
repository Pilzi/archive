import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {

        ChessBoard chessBoard = new ChessBoard();
        
        Player currentPlayer = new Player("white");

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/Handjet-Medium.ttf")));
        } catch (IOException|FontFormatException e) {
            
        }

        Gui gui = new Gui();
        
        gui.setupGui(currentPlayer, chessBoard.getChessBoard());
    }
}
 