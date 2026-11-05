import java.io.Serializable;

public class ChessBoard implements Serializable{


    ChessPieces[] chessBoard = new ChessPieces[64];
    


    private void createChessBoard(){

        
        ChessPieces piece = new ChessPieces();

        for (int row = 1; row <= 8; row++){
            for (int tile = 1; tile <= 8; tile++){

                piece = new ChessPieces();
                piece.Row = row;
                piece.image = "none";
                
                if (row <= 2){
                    piece.Color = "white";
                } else if (row >= 7){
                    piece.Color = "black";
                } 
                
                if (row == 1 || row == 8) {
                    if (tile == 1 || tile == 8){
                        piece.rook();
                        piece.image = piece.Color == "white" ? "./assets/white-rook.png":"./assets/black-rook.png";
                    }
                    if (tile == 2 || tile == 7){
                        piece.knight();
                        piece.image = piece.Color == "white" ? "./assets/white-knight.png":"./assets/black-knight.png";
                        
                        
                        
                    }
                    if (tile == 3 || tile == 6){
                        piece.bishop();
                        piece.image = piece.Color == "white" ? "./assets/white-bishop.png":"./assets/black-bishop.png";
                    }
                    if (tile == 4){
                        piece.queen();
                        piece.image = piece.Color == "white" ? "./assets/white-queen.png":"./assets/black-queen.png";
                    }
                    if (tile == 5){
                        piece.king();
                        piece.image = piece.Color == "white" ? "./assets/white-king.png":"./assets/black-king.png";
                    }
                }
                if (row == 2 || row == 7){
                    piece.pawn();
                    piece.image = piece.Color == "white" ?  "./assets/white-pawn.png" : "./assets/black-pawn.png";
                } 
                if (row > 2 && row < 7) {
                    piece.empty();
                }
                this.chessBoard[((tile) + ((row - 1) * 8)) - 1] = piece;
                piece.Position = ((tile) + ((row - 1) * 8)) - 1;
                
            }
        }
        
        
    }


    public ChessPieces[] getChessBoard(){
        if (this.chessBoard[0] == null){
            createChessBoard();
        }

        return this.chessBoard;
    }

    


}
 