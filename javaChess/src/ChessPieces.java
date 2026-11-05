import java.io.Serializable;
import java.util.Arrays;

public class ChessPieces implements Serializable {

    
    String ChessPiece;
    int Value;
    String Color;
    int Position;
    int Row;
    String Identifier;
    String image; 
    int MoveCount = 0;
    
    private static final long serialVersionUID = 1L;
    

    

    public void empty(){
        
        this.ChessPiece = "empty";
        this.Value = 0; 
        this.Identifier = ""; 
    }

    public void pawn(){   
        this.ChessPiece = "pawn";
        this.Value = 1;  
        this.Identifier = "P";
        
        
    }

    public void bishop(){
        this.ChessPiece = "bishop"; 
        this.Value = 2;
        this.Identifier = "B";
    }

    public void knight(){
        this.ChessPiece = "knight"; 
        this.Value = 3;
        this.Identifier = "N";
    }

    public void rook(){
        this.ChessPiece = "rook"; 
        this.Value = 4;
        this.Identifier = "R";
    }

    public void queen(){
        this.ChessPiece = "queen"; 
        this.Value = 5;
        this.Identifier = "Q";
    }

    public void king(){
        this.ChessPiece = "king"; 
        this.Value = 6;
        this.Identifier = "K";
    }

    public Integer[] getValidCordinates (ChessPieces[] Board, ChessPiecesController chessPiecesController) {

        Integer[] chesspieceDoesNotExist = new Integer[2];


        Arrays.fill(chesspieceDoesNotExist, -1);

        switch(this.ChessPiece){
            case "empty":
                return chessPiecesController.getValidCoordinatesKnight(this.Position, Board); 
            case "pawn":
                return chessPiecesController.getValidCoordinatesPawn(this.Position, Board); 
            case "bishop":
                return chessPiecesController.getValidCoordinatesBishop(this.Position, Board); 
            case "knight":
                return chessPiecesController.getValidCoordinatesKnight(this.Position, Board); 
            case "rook":
                return chessPiecesController.getValidCoordinatesRook(this.Position, Board); 
            case "queen": 
                return chessPiecesController.getValidCoordinatesQueen(this.Position, Board); 
            case "king":
                return chessPiecesController.getValidCoordinatesKing(this.Position, Board); 
        } 
         
         return chesspieceDoesNotExist;
    }
    
    

}
