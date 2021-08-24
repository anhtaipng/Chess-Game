package vn.gihot.chess.master.model.game.board;

import vn.gihot.chess.master.model.game.piece.Piece;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Board {
    protected ArrayList<Piece> whitePieces;
    protected ArrayList<Piece> capturedWhitePieces;
    protected ArrayList<Piece> blackPieces;
    protected ArrayList<Piece> capturedBlackPieces;
    protected HashMap<Position, Piece> occupiedPositions;

    public abstract void initBoard();

    public abstract GameEndType checkEndGame();
}
