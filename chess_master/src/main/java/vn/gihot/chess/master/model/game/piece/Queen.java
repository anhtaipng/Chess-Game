package vn.gihot.chess.master.model.game.piece;

import vn.gihot.chess.master.model.exception.InvalidPositionFormatException;
import vn.gihot.chess.master.model.game.board.Board;
import vn.gihot.chess.master.model.game.board.BoardHelper;
import vn.gihot.chess.master.model.game.board.Position;
import vn.gihot.chess.master.model.move.MoveInfo;

public class Queen extends Piece{

    public Queen(Position position, Type type,Board board) {
        super(position, type,board);
    }

    @Override
    public String getSymbol() {
        return "Q";
    }

    @Override
    public void updateReachableDestinationsFrom(Position pos) {

    }

    @Override
    public boolean canReachTo(Position pos) {
        return board.connectedByFirstDiagonal(this.position, pos) || board.connectedBySecondDiagonal(position, pos) ||
                board.connectedByHorizontalLine(position,pos) ||board.connectedByVerticalLine(position,pos);
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
