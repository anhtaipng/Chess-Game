package vn.gihot.chess.master.model.game.piece;

import vn.gihot.chess.master.model.exception.InvalidPositionFormatException;
import vn.gihot.chess.master.model.game.board.Board;
import vn.gihot.chess.master.model.game.board.Position;
import vn.gihot.chess.master.model.move.MoveInfo;

import java.lang.Math;
import java.util.HashMap;

public class Bishop extends Piece {


    public Bishop(Position position, Type type, Board board) {
        super(position, type, board);
    }

    @Override
    public String getSymbol() {
        return Piece.BISHOP;
    }

    /*  Return true if the pos is on the NorthWest-SouthEast diagonal
        x
          B
            x
     */
    public boolean onTheFirstDiagonal(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        int selfX = this.position.getX();
        int selfY = this.position.getY();
        return x + y == selfX + selfY;
    }

    /*  Return true if the pos is on the SouthWest-NorthEast diagonal
            x
          B
        x
     */
    public boolean onTheSecondDiagonal(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        int selfX = this.position.getX();
        int selfY = this.position.getY();
        return x + y == selfX + selfY;
    }

    @Override
    public void updateReachableDestinationsFrom(Position pos) {

    }

    /*
        Pos is the position of we need to check if the bishop can reach to
     */
    @Override
    public boolean canReachTo(Position pos) {
        return board.connectedByFirstDiagonal(position, pos) || board.connectedBySecondDiagonal(position, pos);
    }

    @Override
    public void update(Board board, MoveInfo moveInfo, boolean isRedoMove) {
        if(!isRedoMove){
            try{
                if(moveInfo.getStartPosition() == this.position){
                    this.position = moveInfo.getEndPosition();
                }
            } catch (InvalidPositionFormatException e) {
                e.printStackTrace();
            }
        }
    }
}
