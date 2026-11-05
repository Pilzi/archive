import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GuiChangePiece {

    GlobalSettings globalSettings;
    JFrame frame;

    public GuiChangePiece(GlobalSettings globalSettings, JFrame frame){
        this.globalSettings = globalSettings;
        this.frame = frame;
    }
    
    public void createChangeGui(int pos){
        
       
        JLabel changePieceGui = new JLabel(); 
        changePieceGui.setLayout(new GridLayout(10, 1));

        for (int i = 0; i < 10; i++){
            JPanel gridTile = new JPanel();
            gridTile.setLayout(new GridBagLayout());
            
            gridTile.setOpaque(true);
           
            

            setBackgroundTransparent(gridTile);
            if (i == 5){
                
                gridTile.add(createButton( frame.getHeight()/12, returnPieceImage( pos, "queen"), "queen", pos));
                gridTile.add(createButton(  frame.getHeight()/12,  returnPieceImage( pos,"bishop"), "bishop", pos));
                gridTile.add(createButton(  frame.getHeight()/12, returnPieceImage( pos,"rook"), "rook", pos));
                gridTile.add(createButton(  frame.getHeight()/12, returnPieceImage( pos,"knight"), "knight", pos));
                
            }
            
            changePieceGui.add(gridTile);
        }

        
        frame.setGlassPane(changePieceGui);
        frame.getGlassPane().setVisible(true);
        
        SwingUtilities.updateComponentTreeUI(frame);
        

    }

    public void setBackgroundTransparent(JPanel tile){
        
        tile.setBackground(new Color(0, 0, 0, 200));
    }

    public JButton createButton( int size, String path, String chessPiece, int pos){
        JButton button = new JButton();

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource(path));
        Image newimg = backgroundImage.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(newimg);

        button.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1, false));

        button.setBackground(new Color(33, 33, 33));
        button.setIcon(icon);

        addButtonListener( button, chessPiece, pos);


        return button;


    }

    

    public String returnPieceImage( int pos, String ChessPiece){
        String path = "";

        switch (ChessPiece) {
            case "queen":
                path = globalSettings.chessBoard[pos].Color.equals("white") ? "./assets/white-queen.png":"./assets/black-queen.png";
                
                break;
            
            case "bishop":
                path = globalSettings.chessBoard[pos].Color.equals("white") ? "./assets/white-bishop.png":"./assets/black-bishop.png";
                break;
            
            case "rook":
                path = globalSettings.chessBoard[pos].Color.equals("white") ? "./assets/white-rook.png":"./assets/black-rook.png";
                break;

            case "knight":
                path = globalSettings.chessBoard[pos].Color.equals("white") ? "./assets/white-knight.png":"./assets/black-knight.png";
                break;
        }
        
        return path;
    }

    private void addButtonListener( JButton button, String chessPiece, int pos){

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(chessPiece){
                    case "queen":
                        replacePawn( pos, "Q", returnPieceImage(pos, chessPiece), chessPiece);
                        
                        break;
                    case "bishop":
                        replacePawn( pos, "B", returnPieceImage(pos, chessPiece), chessPiece);
                        break;
                    case "rook":
                    replacePawn( pos, "R", returnPieceImage(pos, chessPiece), chessPiece);
                        break;
                    case "knight":
                        replacePawn( pos, "N", returnPieceImage(pos, chessPiece), chessPiece);
                        break;

                }

                frame.setGlassPane(new JPanel());
                frame.getGlassPane().setVisible(false);
                updateGui();
            }
        });
    }

    private void replacePawn( int pos, String identifier, String imagePath, String ChessPiece){
    
        ChessPieces piece = new ChessPieces();
        
        piece.Color = globalSettings.chessBoard[pos].Color;
        piece.Identifier = identifier;
        piece.Position = pos;
        piece.ChessPiece = ChessPiece;
        piece.image = imagePath;
        piece.Row = globalSettings.chessBoard[pos].Row;
        globalSettings.chessBoard[pos] = null;
        globalSettings.chessBoard[pos] = piece;
        
    }


    private void updateGui(){
        GuiBoard guiBoard = new GuiBoard();
        frame.getContentPane().removeAll();
        frame.getContentPane().add(guiBoard.buildBoard(frame, globalSettings), BorderLayout.CENTER);
        guiBoard.buildNotationLogBoard(frame, globalSettings);
        SwingUtilities.updateComponentTreeUI(frame);
    }




}
