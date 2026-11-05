import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GuiNotationLogBoard {
    
    
    JPanel notationLogBoard;

    public JPanel buildNotationLogBoard(JFrame frame, GlobalSettings globalSettings){

        JPanel buildLogBoardContainer = new JPanel();

        buildLogBoardContainer.setBackground(globalSettings.backgroundColor);
        buildLogBoardContainer.setLayout(new GridLayout(25, 1));
        buildLogBoardContainer.setPreferredSize(new Dimension(200,100));
        frame.setSize(globalSettings.frameSizeWidth, globalSettings.frameSizeHeight);

        JButton settings = new JButton();
        settings.setText("settings");
        settings.setForeground(Color.WHITE);

        settings.setBackground(globalSettings.backgroundColor);

        settings.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            GuiSettings guiSettings = new GuiSettings();
            
            frame.getContentPane().removeAll();
            
            frame.getContentPane().add(guiSettings.getSettings(frame, globalSettings, "board"), BorderLayout.CENTER);
            
            SwingUtilities.updateComponentTreeUI(frame);
              
          }
        });

        buildLogBoardContainer.add(settings);
        buildLogBoardContainer.add(buildLogContainer("white", "black", globalSettings));


        for (String[] moves : globalSettings.moveLog){
            buildLogBoardContainer.add(buildLogContainer(moves[0], moves[1], globalSettings));
        }


        
        notationLogBoard = buildLogBoardContainer;
        return buildLogBoardContainer;

        }

    public JPanel buildLogContainer(String moveWhite, String moveBlack, GlobalSettings globalSettings){

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(1, 2));
        container.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, globalSettings.backgroundColor));

        JPanel whiteContainer = new JPanel();
        whiteContainer.add(new JLabel(moveWhite));
        container.add(whiteContainer);

        JPanel blackContainer = new JPanel();
        JLabel text = new JLabel(moveBlack);
        text.setForeground(Color.white);
        blackContainer.add(text);
        blackContainer.setBackground(Color.black);
        blackContainer.setForeground(new Color(255, 255, 255));
        container.add(blackContainer);
        
        return container;

    }

    public String getNotation(ChessPieces chessPiece, int position, int newPosition, ChessPieces[] chessBoard){

        String[] columnNames = { "a", "b", "c", "d", "e", "f", "g", "h" };
        String captureSymbol = ""; // "" = no caputure

        if (chessBoard[newPosition].ChessPiece != "empty"){
        captureSymbol = "x"; 
        }

        String pieceIdentifier = chessPiece.Identifier;
        String newRow = Integer.toString(chessBoard[newPosition].Row);
        String newColumn = columnNames[(newPosition) % 8];

        return (pieceIdentifier + captureSymbol + newColumn + newRow);


    }

  public void updateNotation(JFrame frame, String notation, GlobalSettings globalSettings){

    for (String[] moves : globalSettings.moveLog){
      
      if (moves[0] == null){
        moves[0] = notation;
        break;

      } else if (moves[1] == null){
        moves[1] = notation;
        break;
      }

    } 

    

    if (globalSettings.moveLog[14][1] != null){
      String[][] tmpMoves = {{globalSettings.moveLog[12][0], globalSettings.moveLog[12][1]},{globalSettings.moveLog[13][0], globalSettings.moveLog[13][1]},{globalSettings.moveLog[14][0], globalSettings.moveLog[14][1]}};
      globalSettings.moveLog = new String[15][2];
      globalSettings.moveLog[0] = tmpMoves[0];
      globalSettings.moveLog[1] = tmpMoves[1];
      globalSettings.moveLog[2] = tmpMoves[2];
    }

    frame.remove(notationLogBoard);
    frame.getContentPane().add(buildNotationLogBoard(frame, globalSettings), BorderLayout.EAST);
    SwingUtilities.updateComponentTreeUI(frame);

  }


}
