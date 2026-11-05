import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.Arrays;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class GuiBoard {

    GuiNotationLogBoard guiNotationLogBoard;
    ChessPieces[] StoreMoveTemp = new ChessPieces[2];
    JButton[] tilesArr = new JButton[64];
    boolean checkmate = false;
    
    public JPanel buildBoard(JFrame frame, GlobalSettings globalSettings){

        JPanel chessBoardPanel = new JPanel();
        chessBoardPanel.setLayout(new GridLayout(8, 8));

        

        Color tileColor = globalSettings.tilesColors[0];

        for (int row = 8; row >= 1; row--) {
          tileColor = tileColor == globalSettings.tilesColors[0] ? globalSettings.tilesColors[1] : globalSettings.tilesColors[0];
          for (int tile = 1; tile <= 8; tile++) {

              tileColor = tileColor == globalSettings.tilesColors[0] ? globalSettings.tilesColors[1] : globalSettings.tilesColors[0];
              
              JButton button = new JButton(""); 

              button.setFocusable(false);
              button.setRolloverEnabled(false);

              button.setForeground(new Color(61, 50, 122));

              button.setLayout(new BorderLayout());

              if (row == 1) {

              addColumnName(button, tile);

              }

              if (tile == 1) {

                addRowName(button, row);

              }

              button.setHorizontalAlignment(SwingConstants.CENTER);

              button.setBorder(BorderFactory.createEmptyBorder(3, 6, 3, 6));

              if (!(globalSettings.chessBoard[((tile) + ((row - 1) * 8)) - 1].image.equals("none"))) {
                
                ImageIcon pieceImage = new ImageIcon(getClass().getResource(globalSettings.chessBoard[((tile) + ((row - 1) * 8)) - 1].image));
                addImage(button, globalSettings, pieceImage);

              }

              button.setBackground(tileColor);
              ChessPieces chessPiece = globalSettings.chessBoard[((tile) + ((row - 1) * 8)) - 1];

              button.addActionListener(e ->
              {   
                  ChessPiecesController chessPiecesController = new ChessPiecesController();
                  
                  if (StoreMoveTemp[0] != null){
                    highlightTiles(StoreMoveTemp[0], false, globalSettings);
                  }
                  if (chessPiece.ChessPiece != "empty" && globalSettings.currentPlayer.PlayerColor.equals(chessPiece.Color) ){
                      
                      
                      StoreMoveTemp[0] = chessPiece;
                      highlightTiles(chessPiece, true, globalSettings);
                      
                  } if (chessPiece.ChessPiece.equals("empty") || !(globalSettings.currentPlayer.PlayerColor.equals(chessPiece.Color)) ){
                      StoreMoveTemp[1] = chessPiece;
                  
                      
                  if ((StoreMoveTemp[0] == null) && (StoreMoveTemp[1] != null) ){
                      
                      StoreMoveTemp = new  ChessPieces[2];
                      }
                  
                  if (StoreMoveTemp[0] != null && StoreMoveTemp[1] != null) {
                      validateMove(frame, chessPiecesController, globalSettings);
                      StoreMoveTemp = new  ChessPieces[2];
                      
                      
                  }
                  }
                  
                  
              });
              tilesArr[((tile) + ((row - 1) * 8)) - 1] = button;

              
              button.setBorderPainted(false);
              
              chessBoardPanel.add(button);
            }
    }

    chessBoardPanel.setBackground(globalSettings.backgroundColor);
    
    return chessBoardPanel;

  }

  public void addImage(JButton button, GlobalSettings globalSettings, ImageIcon pieceImage){

      
      button.setIcon(pieceImage);

      Image image = pieceImage.getImage();

      int pieceImageSize = (int)(globalSettings.frameSizeHeight * 0.115);

      Image newimg = image.getScaledInstance(pieceImageSize, pieceImageSize, java.awt.Image.SCALE_SMOOTH);
      pieceImage = new ImageIcon(newimg);

      button.setIcon(pieceImage);

  }

  public void addColumnName(JButton button, int tile){

    String[] columnNames = {
      "A",
      "B",
      "C",
      "D",
      "E",
      "F",
      "G",
      "H"
      };

    JLabel textLabel = new JLabel(columnNames[tile - 1]);

    textLabel.setForeground(new Color(167, 66, 255));

    textLabel.setVerticalAlignment(SwingConstants.BOTTOM);

    button.add(textLabel, BorderLayout.EAST);
  }

  public void addRowName(JButton button, int row){

      JLabel textLabel = new JLabel(Integer.toString(row));

      textLabel.setForeground(new Color(167, 66, 255));

      textLabel.setVerticalAlignment(SwingConstants.TOP);

      button.add(textLabel, BorderLayout.WEST);
  }

  public void buildNotationLogBoard(JFrame frame, GlobalSettings globalSettings){
    guiNotationLogBoard = new GuiNotationLogBoard();
    
    frame.getContentPane().add(guiNotationLogBoard.buildNotationLogBoard(frame, globalSettings), BorderLayout.EAST);
  }


  public void highlightTiles(ChessPieces chessPiece, boolean highligh, GlobalSettings globalSettings) {
    
    

    for (Integer coordinate : chessPiece.getValidCordinates(globalSettings.chessBoard, new ChessPiecesController())){


      
      if (coordinate <= -1) {
          break;
      }
      
      if (highligh){
        JPanel shadow = new RoundedPanel(180, 0.22);
        
       shadow.setBackground(new Color(0,0,0,100));
       if (!(globalSettings.chessBoard[coordinate].ChessPiece.equals("empty"))){
        this.tilesArr[coordinate].setBackground(Color.RED);
       } else {
        this.tilesArr[coordinate].add(shadow);
       }
        
       
        
        
      } else {
         double row = globalSettings.chessBoard[coordinate].Row;

        try {
          this.tilesArr[coordinate].remove(this.tilesArr[coordinate].getComponentCount() - 1);
        }
        catch(Exception e) {
           // no highlight to remove
        }

        


         if ( Math.floor(row) % 2 == 0){

          if (((coordinate + 1) % 8) % 2 == 0) {
            this.tilesArr[coordinate].setBackground(globalSettings.tilesColors[1]);
          } 
           else {
            this.tilesArr[coordinate].setBackground(globalSettings.tilesColors[0]);
          }

         } else {
          if (((coordinate + 1) % 8) % 2 == 0) {
            this.tilesArr[coordinate].setBackground(globalSettings.tilesColors[0]);
          } else {
            this.tilesArr[coordinate].setBackground(globalSettings.tilesColors[1]);
          }
         }
      }
      SwingUtilities.updateComponentTreeUI(this.tilesArr[coordinate]);
    }

  }

  
  public boolean validateMove(JFrame frame, ChessPiecesController chessPiecesController, GlobalSettings globalSettings) {
    boolean checkmate = false;
    if (this.StoreMoveTemp[0] != null && this.StoreMoveTemp[1] != null) {

      if (chessPiecesController.isValidMove(this.StoreMoveTemp[0].Position, this.StoreMoveTemp[1].Position, globalSettings.chessBoard) && !(checkmate)) {
        if (globalSettings.chessBoard[this.StoreMoveTemp[1].Position].ChessPiece.equals("king")){
           checkmate = true;
        }
        guiNotationLogBoard.updateNotation(frame, guiNotationLogBoard.getNotation(this.StoreMoveTemp[0], this.StoreMoveTemp[0].Position, this.StoreMoveTemp[1].Position, globalSettings.chessBoard), globalSettings);
        chessPiecesController.movePiece(this.StoreMoveTemp[0].Position, this.StoreMoveTemp[1].Position, globalSettings);
        
        

        if (globalSettings.chessBoard[this.StoreMoveTemp[1].Position].ChessPiece.equals("pawn") && (globalSettings.chessBoard[this.StoreMoveTemp[1].Position].Row == 1 || globalSettings.chessBoard[this.StoreMoveTemp[1].Position].Row == 8)){
          GuiChangePiece guiChangePiece = new GuiChangePiece(globalSettings, frame);
          guiChangePiece.createChangeGui(  this.StoreMoveTemp[1].Position);
      }
      if (!(checkmate)){
        globalSettings.saveFile.saveFile(globalSettings.chessBoard,  new File("chessBoardSave.txt"));
        globalSettings.saveFile.saveFile(globalSettings.moveLog,  new File("moveLog.txt"));
      }
        

        if (globalSettings.chessBoard[this.StoreMoveTemp[1].Position].ChessPiece.equals("king") || ( this.StoreMoveTemp[1].Position - this.StoreMoveTemp[0].Position) > 1){
          
          castling(globalSettings);          

      }

        updateGui();
        globalSettings.currentPlayer.changePlayerColor();

        if (checkmate) {
          GuiWinnMenu guiWinnMenu = new GuiWinnMenu();
          guiWinnMenu.buildWinnMenu(frame, this.StoreMoveTemp[1].Color, globalSettings);
          
        }

        
        
        
        
        return true;
      }
      
    }
    return false;
  }

  public void castling(GlobalSettings globalSettings){

    ChessPiecesController chessPiecesController = new ChessPiecesController();
    int[] rookPosition = new int[2];
    Arrays.fill(rookPosition, -1);

    switch (this.StoreMoveTemp[1].Position){
        case 1:
        case 57:
            rookPosition[0] = this.StoreMoveTemp[1].Position-1;
            rookPosition[1] = this.StoreMoveTemp[1].Position+1;
            break;
        case 6:
        case 62:
            rookPosition[1] = this.StoreMoveTemp[1].Position-1;
            rookPosition[0] = this.StoreMoveTemp[1].Position+1;
            break;
        

    }
      if (rookPosition[0] != -1){
        chessPiecesController.movePiece( rookPosition[0], rookPosition[1], globalSettings);
        this.tilesArr[rookPosition[1]].setIcon(this.tilesArr[rookPosition[0]].getIcon());
        this.tilesArr[rookPosition[0]].setIcon(null);
      }
        
  }

  public void updateGui() {
    this.tilesArr[this.StoreMoveTemp[1].Position].setIcon(this.tilesArr[this.StoreMoveTemp[0].Position].getIcon());
    this.tilesArr[this.StoreMoveTemp[0].Position].setIcon(null);
  }

  



}

