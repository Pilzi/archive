import java.util.Arrays;

public class ChessPiecesController {

    public boolean isValidMove(int position, int newPosition, ChessPieces[] Board) {
        switch (Board[position].ChessPiece) {
            case "empty":
                return false;
            case "pawn":
                return isNewPositionValid(getValidCoordinatesPawn(position, Board), newPosition); 
            case "bishop":
                return isNewPositionValid(getValidCoordinatesBishop(position, Board), newPosition); 
            case "knight":
                return isNewPositionValid(getValidCoordinatesKnight(position, Board), newPosition); 
            case "rook":
                return isNewPositionValid(getValidCoordinatesRook(position, Board), newPosition); 
            case "queen": 
                return isNewPositionValid(getValidCoordinatesQueen(position, Board), newPosition);
            case "king":
                return isNewPositionValid(getValidCoordinatesKing(position, Board), newPosition); 
            default:
                return false;
        }
    }

    public boolean isNewPositionValid(Integer[] validCoordinates, int newPosition) {
        return Arrays.asList(validCoordinates).contains(newPosition);
    }

    public void movePiece(int position, int newPosition, GlobalSettings globalSettings ) {
    
        globalSettings.chessBoard[newPosition].Color = globalSettings.chessBoard[position].Color;
        globalSettings.chessBoard[newPosition].ChessPiece = globalSettings.chessBoard[position].ChessPiece;
        globalSettings.chessBoard[newPosition].Value = globalSettings.chessBoard[position].Value;
        globalSettings.chessBoard[newPosition].image = globalSettings.chessBoard[position].image;
        globalSettings.chessBoard[newPosition].Identifier = globalSettings.chessBoard[position].Identifier;
        globalSettings.chessBoard[newPosition].MoveCount++;

        globalSettings.chessBoard[position].Position = globalSettings.chessBoard[position].Position;
        globalSettings.chessBoard[position].Row = globalSettings.chessBoard[position].Row;
        globalSettings.chessBoard[position].Color = null;
        globalSettings.chessBoard[position].ChessPiece = "empty";
        globalSettings.chessBoard[position].Value = 0;
        globalSettings.chessBoard[position].Identifier = "";
        globalSettings.chessBoard[position].image = "none";
    }

    public Integer[] getValidCoordinatesDiagonally(int position, ChessPieces[] Board) {
        int[] candidateCoordinates = {9, -9, 7, -7};
        Integer[] validCoordinates = new Integer[64];
        Integer[] leftBorderTiles = {0, 8, 16, 24, 32, 40, 48, 56};
        Integer[] rightBorderTiles = {7, 15, 23, 31, 39, 47, 55, 63};

        Arrays.fill(validCoordinates, -1);

        int pointer = 0;

        for (int step : candidateCoordinates) {
            int tempPosition = position;
            
            while (true) {
                if ((step == 9 || step == -7) && Arrays.asList(rightBorderTiles).contains(tempPosition)) {
                    break;
                }
                if ((step == -9 || step == 7) && Arrays.asList(leftBorderTiles).contains(tempPosition)) {
                    break;
                }

                tempPosition += step;
                if (tempPosition > 63 || tempPosition < 0) {
                    break;
                } 
                
                if (isValidTile(position, tempPosition, Board)) {
                    validCoordinates[pointer] = tempPosition;
                    ++pointer;
                    if (Board[tempPosition].Color != Board[position].Color && !Board[tempPosition].ChessPiece.equals("empty")) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        
        return validCoordinates;
    }

    public Integer[] getValidCoordinatesHorizontallyAndVertically(int position, ChessPieces[] Board) {
        int[] candidateCoordinates = {8, -8, 1, -1};
        Integer[] validCoordinates = new Integer[64];
        Integer[] leftBorderTiles = {0, 8, 16, 24, 32, 40, 48, 56};
        Integer[] rightBorderTiles = {7, 15, 23, 31, 39, 47, 55, 63};

        int pointer = 0;

        Arrays.fill(validCoordinates, -1);

        for (int step : candidateCoordinates) {
            int tempPosition = position;
            
            while (true) {
                if (step == 1 && Arrays.asList(rightBorderTiles).contains(tempPosition)) {
                    break;
                }
                if (step == -1 && Arrays.asList(leftBorderTiles).contains(tempPosition)) {
                    break;
                }

                tempPosition += step;
                if (tempPosition > 63 || tempPosition < 0) {
                    break;
                } 
                
                if (isValidTile(position, tempPosition, Board)) {
                    validCoordinates[pointer] = tempPosition;
                    ++pointer;
                    if (Board[tempPosition].Color != Board[position].Color && !Board[tempPosition].ChessPiece.equals("empty")) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        
        return validCoordinates;
    }

    public boolean isValidTile(int position, int newPosition, ChessPieces[] Board) {
        return Board[newPosition].Color != Board[position].Color;
    }

    public Integer[] getValidCoordinatesPawn(int position, ChessPieces[] Board) {
        int[] candidateCoordinatesWhite = {8, 7, 9, 16};
        int[] candidateCoordinatesBlack = {-8, -7, -9, -16};
        int[] candidateCoordinates =  Board[position].Color.equals("white") ? candidateCoordinatesWhite:candidateCoordinatesBlack;
        Integer[] validCoordinates = new Integer[64];
        Integer[] leftBorderTiles = {0, 8, 16, 24, 32, 40, 48, 56};
        Integer[] rightBorderTiles = {7, 15, 23, 31, 39, 47, 55, 63};
        
        int pointer = 0;
        Arrays.fill(validCoordinates, -1);

        for (int step : candidateCoordinates) {
            int tempPosition = position;
            
            if (step == candidateCoordinates[0] && !Board[tempPosition + step].ChessPiece.equals("empty")){
                continue;
            }
            if (step == candidateCoordinates[1]  && (Arrays.asList(Board[position].Color.equals("white") ? rightBorderTiles: leftBorderTiles).contains(tempPosition + step) ||  Arrays.asList(Board[position].Color.equals("white") ? leftBorderTiles:rightBorderTiles).contains(position) || Board[tempPosition + step].ChessPiece.equals("empty"))) {
                continue;
            }
            if (step == candidateCoordinates[2]  && (Arrays.asList(Board[position].Color.equals("white") ? leftBorderTiles:rightBorderTiles).contains(tempPosition + step) || Arrays.asList(Board[position].Color.equals("white") ? rightBorderTiles:leftBorderTiles).contains(position) || Board[tempPosition + step].ChessPiece.equals("empty"))) {
                continue;
            }
            if (step == candidateCoordinates[3]  && Board[tempPosition].Row != (Board[tempPosition].Color.equals("white") ? 2:7)){
                continue;
            }
            
            tempPosition += step;

            
            if (tempPosition > 63 || tempPosition < 0) {
                
                continue;
            } 

            if (isValidTile(position, tempPosition, Board)) {
                validCoordinates[pointer] = tempPosition;
                pointer++;
            }
        }
        return validCoordinates;
    }


    public Integer[] getValidCoordinatesBishop(int position, ChessPieces[] Board) {
        return getValidCoordinatesDiagonally(position, Board);
    }

    public Integer[] getValidCoordinatesRook(int position, ChessPieces[] Board) {
        return getValidCoordinatesHorizontallyAndVertically(position, Board);
    }

    public Integer[] getValidCoordinatesKnight(int position, ChessPieces[] Board) {
        int[] candidateCoordinates = {6, -6, 15, -15, 17, -17, 10, -10};
        Integer[] validCoordinates = new Integer[64];
        Integer[] leftBorderTiles = {0, 8, 16, 24, 32, 40, 48, 56};
        Integer[] rightBorderTiles = {7, 15, 23, 31, 39, 47, 55, 63};
        
        int pointer = 0;
        Arrays.fill(validCoordinates, -1);

        for (int step : candidateCoordinates) {
            int tempPosition = position;
            
            if ((step == -10 || step == -17 || step == 15 || step == 6) && (Arrays.asList(rightBorderTiles).contains(tempPosition + step) || Arrays.asList(leftBorderTiles).contains(tempPosition))) {
                continue;
            }
            if ((step == 10 || step == 17 || step == -15 || step == -6) && (Arrays.asList(leftBorderTiles).contains(tempPosition + step) || Arrays.asList(rightBorderTiles).contains(tempPosition))) {
                continue;
            }

            tempPosition += step;
            if (tempPosition > 63 || tempPosition < 0) {
                continue;
            } 

            if (isValidTile(position, tempPosition, Board)) {
                validCoordinates[pointer] = tempPosition;
                pointer++;
            }
        }
        return validCoordinates;
    }

    public Integer[] getValidCoordinatesQueen(int position, ChessPieces[] Board) {
        Integer[] diagonalArray = getValidCoordinatesDiagonally(position, Board);
        Integer[] horizontalArray = getValidCoordinatesHorizontallyAndVertically(position, Board);

        int diagonalPointer = 0;
        int horizontalPointer = 0;

        while (horizontalArray[horizontalPointer] != -1) {
            horizontalPointer++;
        }

        while (diagonalArray[diagonalPointer] != -1) {
            horizontalArray[horizontalPointer] = diagonalArray[diagonalPointer];
            horizontalPointer++;
            diagonalPointer++;
        }
        return horizontalArray;
    }

    public Integer[] getValidCoordinatesKing(int position, ChessPieces[] Board) {
        int[] candidateCoordinates = {1, -1, 8, -8, 7, -7, 9, -9};
        Integer[] validCoordinates = new Integer[64];
        Integer[] leftBorderTiles = {0, 8, 16, 24, 32, 40, 48, 56};
        Integer[] rightBorderTiles = {7, 15, 23, 31, 39, 47, 55, 63};
        
        Arrays.fill(validCoordinates, -1);
        int pointer = 0;

        for (int step : candidateCoordinates) {
            int tempPosition = position;
            
            if ((step == -1 || step == 7 || step == -9) && Arrays.asList(leftBorderTiles).contains(tempPosition)) {
                continue;
            }
            if ((step == 1 || step == -7 || step == 9) && Arrays.asList(rightBorderTiles).contains(tempPosition)) {
                continue;
            }

            tempPosition += step;
            if (tempPosition > 63 || tempPosition < 0) {
                continue;
            } 

            
            

            if (isValidTile(position, tempPosition, Board)) {
                validCoordinates[pointer] = tempPosition;
                pointer++;
            }                     
        }
        validCoordinates[pointer] = castling(Board[position].Row, Board);
        return validCoordinates;
    }

    public int castling(int row, ChessPieces[] chessBoard){

        if (chessBoard[0].ChessPiece.equals("rook") && pieceMoved(chessBoard[0]) && pieceMoved(chessBoard[4])){
            if (chessBoard[0].Color.equals("white") && chessBoard[1].ChessPiece.equals("empty") && chessBoard[2].ChessPiece.equals("empty") && (chessBoard[3].ChessPiece.equals("empty"))){
                return 1;
            }

        } if (chessBoard[7].ChessPiece.equals("rook") && pieceMoved(chessBoard[7]) && pieceMoved(chessBoard[4])){
            if (chessBoard[7].Color.equals("white") && chessBoard[6].ChessPiece.equals("empty") && (chessBoard[5].ChessPiece.equals("empty"))){
                return 6;
            }
        }if (chessBoard[56].ChessPiece.equals("rook") && pieceMoved(chessBoard[56]) && pieceMoved(chessBoard[60])){
            if (chessBoard[56].Color.equals("black") &&  chessBoard[57].ChessPiece.equals("empty") && chessBoard[58].ChessPiece.equals("empty") && (chessBoard[59].ChessPiece.equals("empty"))){
                return 57;
            }

        } if (chessBoard[63].ChessPiece.equals("rook") && pieceMoved(chessBoard[63]) && pieceMoved(chessBoard[60])){
            if (chessBoard[63].Color.equals("black") && chessBoard[62].ChessPiece.equals("empty") && (chessBoard[61].ChessPiece.equals("empty"))){
                return 62;
            }
        }
        return -1;
    }


    private boolean pieceMoved(ChessPieces chessPiece) {
        if (chessPiece.MoveCount > 0){
            return false;
        }
        return true;
    }
}
