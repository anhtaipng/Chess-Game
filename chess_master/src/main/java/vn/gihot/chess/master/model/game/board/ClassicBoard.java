package vn.gihot.chess.master.model.game.board;

import vn.gihot.chess.master.model.game.piece.*;
// This class is not complete
public class ClassicBoard extends Board{

    public ClassicBoard(){
        initBoard();
    }
    @Override
    public void initBoard() {
        // Adding Pawns
        for(int i=1;i<=8;i++){
            // Adding Black Pawns
            Position whitePos = new Position(i,2);
            Pawn whitePawn = new Pawn(whitePos,this, Type.WHITE);
            whitePieces.add(whitePawn);
            this.occupiedPositions.put(whitePos,whitePawn);

            // Adding White Pawns
            Position blackPos = new Position(i,7);
            Pawn blackPawn = new Pawn(blackPos,this, Type.BLACK);
            blackPieces.add(blackPawn);
            this.occupiedPositions.put(blackPos,blackPawn);
        }
        // Adding Rooks
        Position posR1 = new Position(1,1);
        Rook rook1 = new Rook(posR1,this,Type.WHITE);
        this.occupiedPositions.put(posR1, rook1);
        whitePieces.add(rook1);

        Position posR2 = new Position(8, 1);
        Rook rook2 = new Rook(posR2,this,Type.WHITE);
        this.occupiedPositions.put(posR2, rook2);
        whitePieces.add(rook2);

        Position posR3 = new Position(1, 8);
        Rook rook3 = new Rook(posR3, this, Type.BLACK);
        this.occupiedPositions.put(posR3, rook3);
        blackPieces.add(rook3);

        Position posR4 = new Position(8, 8);
        Rook rook4 = new Rook(posR4, this, Type.BLACK);
        this.occupiedPositions.put(posR4, rook4);
        blackPieces.add(rook4);

        // Adding Bishop
        Position posB1 = new Position(3,1);
        Bishop bishop1 = new Bishop(posB1,this,Type.WHITE);
        this.occupiedPositions.put(posB1, bishop1);
        whitePieces.add(bishop1);

        Position posB2 = new Position(6, 1);
        Bishop bishop2 = new Bishop(posB2,this,Type.WHITE);
        this.occupiedPositions.put(posB2, bishop2);
        whitePieces.add(bishop2);

        Position posB3 = new Position(3, 8);
        Bishop bishop3 = new Bishop(posB3, this, Type.BLACK);
        this.occupiedPositions.put(posB3, bishop3);
        blackPieces.add(bishop3);

        Position posB4 = new Position(6, 8);
        Bishop bishop4 = new Bishop(posB4, this, Type.BLACK);
        this.occupiedPositions.put(posB4, bishop4);
        blackPieces.add(bishop4);

        // Adding Knight
        Position posK1 = new Position(2,1);
        Knight knight1 = new Knight(posK1,this,Type.WHITE);
        this.occupiedPositions.put(posK1, knight1);
        whitePieces.add(knight1);

        Position posK2 = new Position(7, 1);
        Knight knight2 = new Knight(posK2,this,Type.WHITE);
        this.occupiedPositions.put(posK2, knight2);
        whitePieces.add(knight2);

        Position posK3 = new Position(2, 8);
        Knight knight3 = new Knight(posK3, this, Type.BLACK);
        this.occupiedPositions.put(posK3, knight3);
        blackPieces.add(knight3);

        Position posK4 = new Position(7, 8);
        Knight knight4 = new Knight(posK4, this, Type.BLACK);
        this.occupiedPositions.put(posK4, knight4);
        blackPieces.add(knight4);

        // Adding King & Queen
        Position kingPos1 = new Position(7, 1);
        King king1 = new King(kingPos1,this,Type.WHITE);
        this.occupiedPositions.put(kingPos1, king1);
        whitePieces.add(knight2);

        Position kingPos2 = new Position(2, 8);
        King king2 = new King(kingPos2, this, Type.BLACK);
        this.occupiedPositions.put(kingPos2, king2);
        blackPieces.add(knight3);


        Position queenPos1 = new Position(7, 1);
        Queen queen1 = new Queen(queenPos1,this,Type.WHITE);
        this.occupiedPositions.put(queenPos1, queen1);
        whitePieces.add(knight2);

        Position queenPos2 = new Position(2, 8);
        Queen queen2 = new Queen(queenPos2, this, Type.BLACK);
        this.occupiedPositions.put(queenPos2, queen2);
        blackPieces.add(knight3);

    }

    @Override
    public GameEndType checkEndGame() {
        // Check for stale mate
        // Check for three_time repetition
        // Check for white check mate
        // Check for black check mate
        return GameEndType.NOT_END_YET;
    }
}
