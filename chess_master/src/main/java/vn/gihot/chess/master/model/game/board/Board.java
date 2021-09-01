package vn.gihot.chess.master.model.game.board;

import vn.gihot.chess.master.model.game.pattern.ChessObserver;
import vn.gihot.chess.master.model.game.pattern.ChessSubject;
import vn.gihot.chess.master.model.game.piece.King;
import vn.gihot.chess.master.model.game.piece.Piece;
import vn.gihot.chess.master.model.game.piece.Type;
import vn.gihot.chess.master.model.move.MoveInfo;
import vn.gihot.chess.master.utilclass.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Board implements ChessSubject {
    // Board index will be 1-8 for box the x axis and the y axis:
    /*
         y^
       ...|
        3 |
        2 |
        1 |
            -------------> x
            1  2  3 ....
     */
    protected ArrayList<Piece> whitePieces = new ArrayList<>();
    protected ArrayList<Piece> capturedWhitePieces = new ArrayList<>();
    protected ArrayList<Piece> blackPieces = new ArrayList<>();
    protected ArrayList<Piece> capturedBlackPieces = new ArrayList<>();
    protected HashMap<Position, Piece> occupiedPositions = new HashMap<>();
    protected ArrayList<ChessObserver> observers = new ArrayList<>();
    protected ArrayList<MoveInfo> moveStack = new ArrayList<>();

    public ArrayList<Piece> getAlivePieceListFromType(Type type){
        if(type == Type.WHITE) return whitePieces;
        return blackPieces;
    }
    // Return the piece at pos if found. Null otherwisw
    public Piece getPieceAtPos(Position pos){
        return occupiedPositions.get(pos);
    }
    @Override
    public void registerObserver(ChessObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(ChessObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (ChessObserver obs : observers) {
            obs.update(this,moveStack.get(moveStack.size() -1 ),false);
        }
    }
    public HashMap<Position, Piece> getOccupiedPositions() {
        return occupiedPositions;
    }

    public boolean coordinateOccupied(int x, int y) {
        return occupiedPositions.containsKey(BoardHelper.getPosFromCoord(x,y));
    }
    // The following function determine if the two position is connected
    // by some set of rules (With collision / Blockage counted)

    /*  Return true if the pos is on the NorthWest-SouthEast diagonal
        x
          B
            x
     */
    public boolean connectedByFirstDiagonal(Position pos1, Position pos2) {
        int x1 = pos1.getX();
        int y1 = pos1.getY();
        int x2 = pos2.getX();
        int y2 = pos2.getY();
        int diagonalSum = x1 + y1;
        if (x1 + y1 != x2 + y2) return false;
        if (x1 < x2) {
            for (int i = x1; i < x2; i++) {
                int y_diff = diagonalSum - i;
                if (coordinateOccupied(i, y_diff)) {
                    return false;
                }
            }
        } else {
            for (int i = x2 + 1; i < x1; i++) {
                int y_diff = diagonalSum - i;
                if (coordinateOccupied(i, y_diff)) {
                    return false;
                }
            }
        }
        return true;
    }

    /*  Return true if the pos is on the SouthWest-NorthEast diagonal
           x
         B
       x
    */
    public boolean connectedBySecondDiagonal(Position pos1, Position pos2) {
        int x1 = pos1.getX();
        int y1 = pos1.getY();
        int x2 = pos2.getX();
        int y2 = pos2.getY();
        if (Math.abs(x1 - y1) != Math.abs(x2 - y2)) return false;
        // Second Diagonal Invariance: |x1 - y1| = const
        int diagonalConst = Math.abs(x1 - y1);
        boolean negativeDig = x2 - y2 < 0;
        if (x2 < x1) {
            for (int i = x2 + 1; i < x1; i++) {
                int y_diff = negativeDig ? i + diagonalConst : i - diagonalConst;
                if (coordinateOccupied(i, y_diff)) {
                    return false;
                }
            }
        } else {
            for (int i = x1 + 1; i < x2; i++) {
                int y_diff = negativeDig ? i + diagonalConst : i - diagonalConst;
                if (coordinateOccupied(i, y_diff)) {
                    return false;
                }
            }
        }
        return true;
    }
    public abstract void processMove(MoveInfo move);
    public abstract boolean checkLegalMove(MoveInfo move);
//    public abstract void executeMove(MoveInfo move);
    public abstract void redoMove(MoveInfo move);
    /*  Return true if the pos is on the SouthWest-NorthEast diagonal
        p1 x x x p2
    */
    public boolean connectedByHorizontalLine(Position pos1, Position pos2) {
        int x1 = pos1.getX();
        int y1 = pos1.getY();
        int x2 = pos2.getX();
        int y2 = pos2.getY();
        if (y1 != y2) return false;
        if (x1 < x2) {
            for (int i = x1 + 1; i < x2; i++) {
                if (coordinateOccupied(i, y1)) return false;
            }
        } else {
            for (int i = x2 + 1; i < x1; i++) {
                if (coordinateOccupied(i, y1)) return false;
            }
        }
        return true;
    }

    /*  Return true if the pos is on the SouthWest-NorthEast diagonal
         x
         p1
         x
         x
         p2
         x
    */
    public boolean connectedByVerticalLine(Position pos1, Position pos2) {
        int x1 = pos1.getX();
        int y1 = pos1.getY();
        int x2 = pos2.getX();
        int y2 = pos2.getY();
        if (x1 != x2) return false;
        if (y1 < y2) {
            for (int i = y1 + 1; i < y2; i++) {
                if (coordinateOccupied(x1, i)) return false;
            }
        } else {
            for (int i = y2 + 1; i < y1; i++) {
                if (coordinateOccupied(x1, i)) return false;
            }
        }
        return true;
    }

    /*  Return true if the pos is on the SouthWest-NorthEast diagonal
        p2 x x x p2 x
        x  x p1 x x x
        p2 x x x p2 x
        x  p2 x x x x
        x  x  x x x x
    */
    public boolean connectedByOneKnightMove(Position pos1, Position pos2) {
        int x1 = pos1.getX();
        int y1 = pos1.getY();
        int x2 = pos2.getX();
        int y2 = pos2.getY();
        for (Pair<Integer, Integer> ele : BoardHelper.knightMoveDiffArray) {
            if ((x1 + ele.first == x2) && (y1 + ele.second == y2)) return true;
        }
        return false;
    }

    public boolean twoCoordinateConnected(Position pos1, Position pos2) {
        return false;
    }

    public abstract void initBoard();

    public abstract GameEndType checkEndGame();
    //  Piece will be printed: P+ (White Pawn), P-(BlackPawn).
    public void printBoard(boolean showAxis) {
        for (int i = 8; i >= 1; i--) {
            if(showAxis) System.out.print(i + "|");
            for (int j = 8; j >= 1; j--) {
                Position pos = BoardHelper.getPosFromCoord(j,i);
                if (occupiedPositions.containsKey(pos)) {
                    Piece piece = occupiedPositions.get(pos);
                    String playerTypeIndicator = (piece.getType()== Type.WHITE)? "+" : "-";
                    System.out.print(" " + piece.getSymbol().concat(playerTypeIndicator) + " ");
                } else
                    System.out.print(" XX ");
            }
            System.out.print("\n");
        }
        if(showAxis){
            System.out.print("   ");
            for (int i = 0; i < 8; i++) {
                System.out.print(Character.toChars(97 + i));
                System.out.print("   ");
            }
        }
        System.out.print("\n");
    }
}