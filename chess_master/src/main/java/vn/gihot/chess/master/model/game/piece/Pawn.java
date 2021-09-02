package vn.gihot.chess.master.model.game.piece;

import vn.gihot.chess.master.model.exception.InvalidPositionFormatException;
import vn.gihot.chess.master.model.game.board.Board;
import vn.gihot.chess.master.model.game.board.BoardHelper;
import vn.gihot.chess.master.model.game.board.Position;
import vn.gihot.chess.master.model.game.pattern.ChessObserver;
import vn.gihot.chess.master.model.move.MoveInfo;

//
public class Pawn extends Piece {
    private boolean canEnPassant;
    private boolean canBeEnpassanted;
    private int enPassanableTurn;

    public Position getStartPos() {
        return startPos;
    }

    private Position startPos;

    public Pawn(Position position, Type type, Board board) {
        super(position, type, board);
        canEnPassant = false;
        canBeEnpassanted = false;
        enPassanableTurn = -1;
        startPos = position;
    }

    @Override
    public String getSymbol() {
        return "P";
    }

    // Implement possible moves for normal cases as well as for en-passant
    @Override
    public void updateReachableDestinationsFrom(Position pos) {

    }

    @Override
    public boolean canReachTo(Position pos) {
        if (!board.coordinateOccupied(pos.getX(), pos.getY())) {
            if (position == startPos) {
                return pos.getY() - position.getY() <= 2;
            } else {
                if (getType() == Type.WHITE) {
                    return pos.getY() == position.getY() + 1;
                } else {
                    return pos.getY() == position.getY() - 1;
                }
            }
        }
        // Check for capture
        else {
            if (getType() == Type.WHITE) {
                // Normal capure
                if (Math.abs(position.getX() - pos.getX()) == 1 && (pos.getY() - position.getY() == 1))
                    return true;
                // Enpassant Capture
                if (canEnPassant) {
                    Piece p = board.getPieceAtPos(pos);
                    if (!(p instanceof Pawn)) {
                        return false;
                    }
                    Pawn pawn = (Pawn) p;
                    if (!pawn.canBeEnpassanted) {
                        return false;
                    } else {
                        return getEnpassantPos(pawn) == pos;
                    }
                }
            } else {
                // Normal capure
                if (Math.abs(position.getX() - pos.getX()) == 1 && (pos.getY() - position.getY() == -1))
                    return true;
                // Enpassant Capture
                if (canEnPassant) {
                    Piece p = board.getPieceAtPos(pos);
                    if (!(p instanceof Pawn)) {
                        return false;
                    }
                    Pawn pawn = (Pawn) p;
                    if (!pawn.canBeEnpassanted) {
                        return false;
                    } else {
                        return getEnpassantPos(pawn) == pos;
                    }
                }
            }
        }
        return false;
    }

    // Return null if can be en-passed with this pawn
    private Position getEnpassantPos(Pawn enemyPawn) {
        // Checking for enpassant condiion
        if (!enemyPawn.canBeEnpassanted) {
            return null;
        } else if (getType() == enemyPawn.getType()) {
            return null;
        } else {
            if (getType() == Type.WHITE) {
                return BoardHelper.getPosFromCoord(enemyPawn.getPosition().getX(), position.getY() + 1);
            }
            if (getType() == Type.BLACK) {
                return BoardHelper.getPosFromCoord(enemyPawn.getPosition().getX(), position.getY() - 1);
            }
        }
        return null;
    }

    public boolean canBeEnpassanted() {
        return canBeEnpassanted;
    }

    public void setCanBeEnpassanted(boolean canBeEnpassanted) {
        this.canBeEnpassanted = canBeEnpassanted;
    }

    public boolean canEnPassant() {
        return canEnPassant;
    }

    public void setCanEnPassant(boolean canEnPassant) {
        this.canEnPassant = canEnPassant;
    }

    // Pawn can only be EP on first move
    //
    @Override
    public void update(Board board, MoveInfo move, boolean isRedoMove) {
        if (isRedoMove) {
            if (move.getTurn() == enPassanableTurn) {
                enPassanableTurn = move.getTurn();
                canBeEnpassanted = true;
            }
        } else {
            try {
                if (move.getStartPosition() == position) {
                    int endY = move.getEndPosition().getY();
                    if (Math.abs(move.getEndPosition().getY() - startPos.getY()) == 2) {
                        canBeEnpassanted = true;
                        enPassanableTurn = move.getTurn();
                    } else {
                        canEnPassant =  (Math.abs(endY - startPos.getY()) == 3);
                    }
                } else {
                    canBeEnpassanted = false;
                }
            } catch (InvalidPositionFormatException e) {
                e.printStackTrace();
            }

        }
        if(!isRedoMove){
            try{
                if(move.getStartPosition() == this.position){
                    this.position = move.getEndPosition();
                }
            } catch (InvalidPositionFormatException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "Pawn{" +
                "canEnPassant=" + canEnPassant +
                ", canBeEnpassanted=" + canBeEnpassanted +
                ", enPassanableTurn=" + enPassanableTurn +
                ", startPos=" + startPos +
                '}';
    }
}
