package vn.gihot.chess.master.model.game.board;

import vn.gihot.chess.master.model.move.MoveInfo;

public class ThreeCheckBoard extends Board{
    @Override
    public void processMove(MoveInfo move) {

    }

    @Override
    public boolean checkLegalMove(MoveInfo move) {
        return false;
    }

    @Override
    public void redoMove(MoveInfo move) {

    }

    @Override
    public void initBoard() {

    }

    @Override
    public GameEndType checkEndGame() {
        return null;
    }

    @Override
    public void clearBoard() {

    }
}
