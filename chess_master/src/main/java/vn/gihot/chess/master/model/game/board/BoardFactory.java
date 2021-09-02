package vn.gihot.chess.master.model.game.board;

import vn.gihot.chess.master.model.game.piece.*;
import vn.gihot.chess.master.model.room.GameModeConstants;

public final class BoardFactory {
    private static final BoardFactory instance = new BoardFactory();

    private BoardFactory() {
    }

    public static BoardFactory getInstance() {
        return instance;
    }

    public Board createBoard(String gameMode) {
        switch (gameMode) {
            case GameModeConstants.CLASSIC:
                return new ClassicBoard();
            case GameModeConstants.KING_OF_THE_HILL:
                return new KingOfTheHillBoard();
            case GameModeConstants.THREE_TIME_CHECK:
                return new ThreeCheckBoard();
            default:
                return null;
        }
    }
}
