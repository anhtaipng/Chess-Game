package vn.gihot.chess.master.model.game.piece;

import vn.gihot.chess.master.model.exception.InvalidPositionFormatException;
import vn.gihot.chess.master.model.game.board.Board;
import vn.gihot.chess.master.model.game.board.Position;
import vn.gihot.chess.master.model.move.MoveInfo;

public class Knight extends Piece{

    public Knight(Position position, Type type,Board board) {
        super(position, type,board);
    }

    @Override
    public String getSymbol() {
        return "N";
    }

    @Override
    public void updateReachableDestinationsFrom(Position pos) {

    }

    @Override
    public boolean canReachTo(Position pos) {
        return false;
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
