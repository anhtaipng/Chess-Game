package vn.gihot.chess.master.model.game.piece;

import vn.gihot.chess.master.model.exception.InvalidPositionFormatException;
import vn.gihot.chess.master.model.game.board.Board;
import vn.gihot.chess.master.model.game.board.BoardHelper;
import vn.gihot.chess.master.model.game.board.Position;
import vn.gihot.chess.master.model.move.MoveInfo;
import vn.gihot.chess.master.utilclass.Pair;

import java.util.ArrayList;


public class King extends Piece {

    private boolean isChecked;

    public boolean isMoved() {
        return isMoved;
    }

    public void setMoved(boolean moved) {
        isMoved = moved;
    }

    private boolean isMoved;

    public King(Position position, Type type, Board board) {
        super(position, type, board);
        isChecked = false;
        isMoved = false;
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    @Override
    public void updateReachableDestinationsFrom(Position pos) {
        this.reachableDestinations.clear();

    }

    @Override
    public boolean canReachTo(Position pos) {
        int desX = pos.getX();
        int dexY = pos.getY();
        int myX = position.getX();
        int myY = position.getY();
    }

    public void castle(Position targetPosition) {
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public ArrayList<Position> getReachablePosition(){
        int curX = position.getX();
        int curY = position.getY();
        ArrayList<Position> res = new ArrayList<>();
        for (Pair<Integer,Integer> ele : BoardHelper.kingMoveDiffArray) {
            int newX = curX + ele.first;
            int newY = curY + ele.second;
            if ((newX >= 1 && newX <= 8) && (newY >=1 && newY<=8)) {
                res.add(BoardHelper.getPosFromCoord(newX, newY));
            }
        }
        return  res;
    }
    // This should update if the king is checked
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
            for (Piece p : board.getAlivePieceListFromType(BoardHelper.getEnemyRoyalty(this.getType()))) {
                if (p.canReachTo(this.position)) {
                    isChecked = true;
                    break;
                }
            }
        }
    }
}
