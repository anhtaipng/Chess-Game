package vn.gihot.chess.master.model.game.board;

import vn.gihot.chess.master.model.exception.IllegalMoveException;
import vn.gihot.chess.master.model.exception.InvalidDestinationException;
import vn.gihot.chess.master.model.exception.UnSyncedMoveTurnException;
import vn.gihot.chess.master.model.game.piece.*;
import vn.gihot.chess.master.model.move.MoveInfo;
import vn.gihot.chess.master.utilclass.Pair;

import java.util.ArrayList;
import java.util.HashMap;

// This class is not complete
public class ClassicBoard extends Board {
    private final PieceFactory pieceFactory;
    private final ArrayList<King> kings = new ArrayList<>();
    private final ArrayList<Piece> whiteKingAttackers;
    private final ArrayList<Piece> blackKingAttackers;
    // This data structure is used for checking for 3 fold repetition
    private final HashMap<Integer, Integer> boardStateHashCodes;
    public ArrayList<Piece> getAttackersFromType(Type attackedSide) {
        return (attackedSide == Type.WHITE) ? whiteKingAttackers : blackKingAttackers;
    }
    public King getWhiteKing() {
        return kings.get(0);
    }

    public King getBlackKing() {
        return kings.get(1);
    }

    public Piece getKingFromType(Type type) {
        if (type == Type.WHITE) return kings.get(0);
        return kings.get(1);
    }

    public ClassicBoard() {
        pieceFactory = PieceFactory.getInstance();

        boardStateHashCodes = new HashMap<>();
        initBoard();

        whiteKingAttackers = new ArrayList<>();
        blackKingAttackers = new ArrayList<>();
    }

    @Override
    public void initBoard() {
        // Adding Pawns
        for (int x = 1; x <= 8; x++) {
            // Adding White Pawns
            Position whitePos = BoardHelper.getPosFromCoord(x, 2);
            addPiece(Piece.PAWN, Type.WHITE, whitePos);
            // Adding Black Pawns
            Position blackPos = BoardHelper.getPosFromCoord(x, 7);
            addPiece(Piece.PAWN, Type.BLACK, blackPos);
        }
        // Adding Rooks
        Position posR1 = BoardHelper.getPosFromCoord(1, 1);
        addPiece(Piece.ROOK, Type.WHITE, posR1);

        Position posR2 = BoardHelper.getPosFromCoord(8, 1);
        addPiece(Piece.ROOK, Type.WHITE, posR2);

        Position posR3 = BoardHelper.getPosFromCoord(1, 8);
        addPiece(Piece.ROOK, Type.BLACK, posR3);

        Position posR4 = BoardHelper.getPosFromCoord(8, 8);
        addPiece(Piece.ROOK, Type.BLACK, posR4);

        // Adding Bishop
        Position posB1 = BoardHelper.getPosFromCoord(3, 1);
        addPiece(Piece.BISHOP, Type.WHITE, posB1);

        Position posB2 = BoardHelper.getPosFromCoord(6, 1);
        addPiece(Piece.BISHOP, Type.WHITE, posB2);

        Position posB3 = BoardHelper.getPosFromCoord(3, 8);
        addPiece(Piece.BISHOP, Type.BLACK, posB3);

        Position posB4 = BoardHelper.getPosFromCoord(6, 8);
        addPiece(Piece.BISHOP, Type.BLACK, posB4);


        // Adding Knight
        Position posK1 = BoardHelper.getPosFromCoord(2, 1);
        addPiece(Piece.KNIGHT, Type.WHITE, posK1);

        Position posK2 = BoardHelper.getPosFromCoord(7, 1);
        addPiece(Piece.KNIGHT, Type.WHITE, posK2);

        Position posK3 = BoardHelper.getPosFromCoord(2, 8);
        addPiece(Piece.KNIGHT, Type.BLACK, posK3);

        Position posK4 = BoardHelper.getPosFromCoord(7, 8);
        addPiece(Piece.KNIGHT, Type.BLACK, posK4);


        // Adding King
        Position kingPos1 = BoardHelper.getPosFromCoord(5, 1);
        addPiece(Piece.KING, Type.WHITE, kingPos1);

        Position kingPos2 = BoardHelper.getPosFromCoord(5, 8);
        addPiece(Piece.KING, Type.BLACK, kingPos2);
        // Adding Queen
        Position queenPos1 = BoardHelper.getPosFromCoord(4, 1);
        addPiece(Piece.QUEEN, Type.WHITE, queenPos1);

        Position queenPos2 = BoardHelper.getPosFromCoord(4, 8);
        addPiece(Piece.QUEEN, Type.BLACK, queenPos2);

    }

    @Override
    public GameEndType checkEndGame() {
        // Check for stale mate
        Type possibleMatedSide = (getWhiteKing().isChecked()) ? Type.WHITE : Type.BLACK;
        if(getAttackersFromType(Type.WHITE) != null && getAttackersFromType(Type.BLACK) != null){
            if(isCheckMated()){
                return (possibleMatedSide == Type.WHITE) ? GameEndType.BLACK_CHECK_MATE_ENDED : GameEndType.WHITE_CHECK_MATE_ENDED;
            }
        }
        // Check for three_time repetition
        if(checkForThreeFoldRep()) return GameEndType.THREE_FOLD_REPETITION_ENDED;



        return GameEndType.NOT_END_YET;
    }

    @Override
    public void clearBoard() {
        whitePieces.clear();
        capturedWhitePieces.clear();
        blackPieces.clear();
        capturedBlackPieces.clear();
        occupiedPositions.clear();
        moveStack.clear();
    }

    // Check for draw by three-fold-repetition:
    public boolean checkForThreeFoldRep(){
        Integer currentState = this.hashCode();
        if (boardStateHashCodes.containsKey(currentState)) {
            int stateCount = boardStateHashCodes.get(currentState) +1;
            boardStateHashCodes.replace(currentState, stateCount);
            return stateCount == 3;
        }
        else {
            boardStateHashCodes.put(currentState,1);
        }
        return false;
    }
    // Check for Stalemate:
    // staleMatedSide: The Side who cannot make any legal move
    public boolean isStaleMate(Type staleMatedSide){
        return !thereAreLegalMoves(staleMatedSide) && getAttackersFromType(staleMatedSide).size() == 0;
    }
    public boolean thereAreLegalMoves(Type sideToCheck){
        HashMap<Piece, ArrayList<Position>> pinnedPieceAndItsPinRay = new HashMap<>();
        Type enemyRoyalty = BoardHelper.getEnemyRoyalty(sideToCheck);
        for (Piece possibleAttker : getAlivePieceListFromType(BoardHelper.getEnemyRoyalty(sideToCheck))) {
            if (possibleAttker instanceof Rook || possibleAttker instanceof Queen || possibleAttker instanceof Bishop) {
                ArrayList<Position> possibleAttackerRay = BoardHelper.getConnectedPositionBetweenPos(possibleAttker.getPosition(), getKingFromType(sideToCheck).getPosition());
                for (Piece possiblyPinnedPiece : getAlivePieceListFromType(sideToCheck)) {
                    if (possibleAttackerRay.contains(possiblyPinnedPiece.getPosition())) {
                        pinnedPieceAndItsPinRay.put(possiblyPinnedPiece,possibleAttackerRay);
                    }
                }
            }
        }
        if (getAttackersFromType(sideToCheck) == null) {
            for (Piece p : getAlivePieceListFromType(sideToCheck)) {
                if (pinnedPieceAndItsPinRay.containsKey(p)) {
                    for (Position pos : pinnedPieceAndItsPinRay.get(p)) {
                        if (p.canReachTo(pos)) {
                            return true;
                        }
                    }
                }
            }
        }
        // Not in double check
        else if(getAttackersFromType(sideToCheck).size() < 2){
            return checkIfStillCanBeDefended();
        }
        else return checkIfKingStillCanMove(sideToCheck);
        return false;
    }
    // This should be called only when one of the kings is checked
    public boolean isCheckMated() {
        Type possibleMatedSide = (getWhiteKing().isChecked()) ? Type.WHITE : Type.BLACK;
        Type checkingSide = (possibleMatedSide == Type.WHITE) ? Type.BLACK : Type.WHITE;
        King checkedKing = (King) getKingFromType(possibleMatedSide);
        // Check if king can't move anywhere else
        boolean stillCanMove = checkIfKingStillCanMove(possibleMatedSide);
        if(stillCanMove) return false;
        // Check if any allied piece can protect the king (By blocking the attack or killing the attacker):
        return !checkIfStillCanBeDefended();
    }

    public boolean checkIfKingStillCanMove(Type royalty) {
        King checkedKing = (King) getKingFromType(royalty);
        // Check if king can't move anywhere else
        boolean stillCanMove = false;
        for (Position p : checkedKing.getReachablePosition()) {
            boolean coveredPos = false;
            for (Piece piece : getAlivePieceListFromType(royalty)) {
                if (piece.canReachTo(p)) {
                    coveredPos = true;
                    break;
                }
            }
            if (!coveredPos) {
                stillCanMove = true;
                break;
            }
        }
        return stillCanMove;
    }

    public boolean checkIfStillCanBeDefended(){
        Type possibleMatedSide = (getWhiteKing().isChecked()) ? Type.WHITE : Type.BLACK;
        Type checkingSide = (possibleMatedSide == Type.WHITE) ? Type.BLACK : Type.WHITE;
        King checkedKing = (King) getKingFromType(possibleMatedSide);
        boolean canBeDefended = false;
        if(getAttackersFromType(possibleMatedSide) == null) canBeDefended = true;
        else{
            for (Piece attacker : getAttackersFromType(checkingSide)) {
                boolean canCounterAttack = true;
                for (Piece defender : getAlivePieceListFromType(possibleMatedSide)) {
                    // Check if can counter attack
                    Pair<Piece, Piece> piecesChanged = tryMove(defender, attacker.getPosition());
                    for (Piece p : getAlivePieceListFromType(checkingSide)) {
                        if (p.canReachTo(checkedKing.getPosition())) {
                            canCounterAttack = false;
                            break;
                        }
                    }
                    undoTryMove(piecesChanged);
                    // Check if can block the attack
                    if(attacker instanceof Rook || attacker instanceof Queen || attacker instanceof Bishop){
                        for (Position pos : BoardHelper.getConnectedPositionBetweenPos(checkedKing.getPosition(), attacker.getPosition())) {
                            if (defender.canReachTo(pos)) {
                                canBeDefended = true;
                                break;
                            }
                        }
                    }
                    if(canBeDefended) break;
                }
                canBeDefended = canBeDefended || canCounterAttack;
                if(!canBeDefended) break;
            }
        }
        return !canBeDefended;
    }

    /*
        Piece in First is Piece passed in move try.
        Piece in Second is Piece removed (if there is any). Otherwise null
        YOU HAVE BEEN WARNED:
           the tried move MUST BE undo before advancing the game any further.
           Failed to do so will lead to a fucked up game
     */
    public void undoTryMove(Pair<Piece, Piece> piecesChanged) {
        occupiedPositions.remove(piecesChanged.first.getPosition());
        if (piecesChanged.second != null) {
            occupiedPositions.put(piecesChanged.second.getPosition(), piecesChanged.second);
        }
    }
    public Pair<Piece,Piece> tryMove(Piece piece, Position endPos) {
        Piece pieceRemoved = null;
        if (occupiedPositions.containsKey(endPos)) {
            pieceRemoved = occupiedPositions.remove(endPos);
        }
        occupiedPositions.put(endPos, piece);
        return new Pair<Piece,Piece>(piece,pieceRemoved);
    }

    /* MoveInfo:
      “turn”: number (1 / 2/ 3,...)
     “player”: “white/black”
     “piece_moved”: “K/N/Q/P/R/B”
     “piece_start_pos”: “X1X2” (X1: A->B; X2: 1-8)
     “piece_end_pos”:”X1X2”
     “castling”: “true/false”
     “promoting”: “true/false”
     "en_passant": "true/false"
    */
    @Override
    public void processMove(MoveInfo move) {
        try {
            if(!moveStack.isEmpty()){
                int prevMoveTurn = moveStack.get(moveStack.size() - 1).getTurn();
                int thisMoveTurn = move.getTurn();
                if (prevMoveTurn != thisMoveTurn - 1) throw new UnSyncedMoveTurnException(prevMoveTurn + 1, thisMoveTurn);
            }
            Type royalty = move.getPlayerType();
            Type enemyRoyalty = move.getOpponentType();
            King moverKing = (King) getKingFromType(royalty);
            Position startPos = move.getStartPosition();
            Position endPos = move.getEndPosition();
            // Check if King is in check after move
            // (1) Try removing the moved piece from the pos:
            Piece pieceMoved = occupiedPositions.get(startPos);
            // Check move legality:
            if (!checkLegalMove(move)) {
                throw new IllegalMoveException("Cannot Suicide (King is check or unsaved from Check)");
            }
            // Check if it is a castle
            if (move.getCastling()) {
                Rook castlingRook = findCastlingRook(royalty, endPos);
                int rookEndY = endPos.getY();
                int rookEndX = (endPos.getX() == 3) ? 3 : 6;
                Position rookEndPos = BoardHelper.getPosFromCoord(rookEndX, rookEndY);
                pickThenPlacePiece(castlingRook, rookEndPos, move.getTurn());
                pickThenPlacePiece(pieceMoved, endPos, move.getTurn());
            }
            // Check if reachable from current Position
            else if (move.getPromoting()) {
                occupiedPositions.remove(startPos);
                addPiece(move.getPromotedClass(), royalty, endPos);
            } else if (move.getEnpassant()) {
                Pawn capturedPawn = (Pawn) occupiedPositions.get(BoardHelper.getPosFromCoord(endPos.getX(), startPos.getY()));
                removePiece(capturedPawn.getPosition());
                pickThenPlacePiece(pieceMoved, endPos, move.getTurn());
            } else {
                pickThenPlacePiece(pieceMoved, endPos, move.getTurn());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // If move is successful --> Notify the pieces so they can update their own metadata.
        moveStack.add(move);
        this.gameEndType = checkEndGame();
        notifyObservers();
    }

    public Rook findCastlingRook(Type royalty, Position kingEndPos) throws IllegalMoveException {
        Rook rookPiece = null;
        int x = kingEndPos.getX();
        int y = kingEndPos.getY();
        if (royalty == Type.WHITE) {
            // Castling queen side
            if (kingEndPos.getX() == 3) {
                rookPiece = (Rook) occupiedPositions.get(BoardHelper.getPosFromCoord(1, 1));
            } else if (kingEndPos.getX() == 7) {
                rookPiece = (Rook) occupiedPositions.get(BoardHelper.getPosFromCoord(8, 1));
            }
        } else if (royalty == Type.BLACK) {
            // Castling queen side
            if (kingEndPos.getX() == 3) {
                rookPiece = (Rook) occupiedPositions.get(BoardHelper.getPosFromCoord(1, 8));
            } else if (kingEndPos.getX() == 7) {
                rookPiece = (Rook) occupiedPositions.get(BoardHelper.getPosFromCoord(8, 8));
            }
        }
        if (rookPiece == null) throw new IllegalMoveException("Chosen Rook For Castle is invalid");
        if (rookPiece.isMoved()) throw new IllegalMoveException("Chosen Rook For Castle is Moved");
        return rookPiece;
    }

    @Override
    public boolean checkLegalMove(MoveInfo move) {
        Type royalty = move.getPlayerType();
        Type enemyRoyalty = move.getOpponentType();
        King moverKing = (King) getKingFromType(royalty);
        try {
            Position startPos = move.getStartPosition();
            Position endPos = move.getEndPosition();
            // Check if King is in check after move
            // (1) Try removing the moved piece from the pos:
            Piece pieceMoved = occupiedPositions.get(startPos);
            Piece pieceRemoved = null;
            if (!pieceMoved.canReachTo(endPos)) {
                throw new IllegalMoveException("Cannot reach the destination");
            }
            if (occupiedPositions.containsKey(endPos)) {
                pieceRemoved = occupiedPositions.remove(endPos);
                if (pieceRemoved.getType() == royalty)
                    throw new IllegalMoveException("Cannot capture your allies. You Betrayer");
            }
            occupiedPositions.put(endPos, pieceMoved);
            for (Piece attacker : getAlivePieceListFromType(enemyRoyalty)) {
                if (attacker.canReachTo(moverKing.getPosition())) {
                    throw new IllegalMoveException("Legal Move Should not Leave King Checked Afterwards " +
                            "\n your king position: " + moverKing.getPosition().toString(),attacker);
                }
            }
            // Check if our being checked king is saved
            if (moverKing.isChecked()) {
                return false;
            }
            // Undo the try (1)
            occupiedPositions.remove(endPos);
            occupiedPositions.put(startPos, pieceMoved);
            if (pieceRemoved != null) occupiedPositions.put(endPos, pieceRemoved);
            /*
            Castling is permissible provided all of the following conditions hold:[4]
                1. The castling must be kingside or queenside.[5]
                2. Neither the king nor the chosen rook has previously moved.
                3. There are no pieces between the king and the chosen rook.
                4. The king is not currently in check.
                5. The king does not pass through a square that is attacked by an enemy piece.
                6. The king does not end up in check. (True of any legal move.)
             */
            if (move.getCastling()) {
                // Cannot castle out of check
                if (moverKing.getPosition() != getKingFromType(royalty).getPosition()) {
                    throw new IllegalMoveException("Castleing piece must be initialized by King");
                }
                if (moverKing.isChecked()) throw new IllegalMoveException("Cannot Castle Out of check");
                if (moverKing.isMoved()) throw new IllegalMoveException("Moved King can't Castle ");
                // Find the rook and check for connection
                Rook rookPiece = findCastlingRook(royalty, endPos);
                // Check for Castling through check:
                int minRange = Math.min(moverKing.getPosition().getX(), rookPiece.getPosition().getX());
                int maxRange = Math.max(moverKing.getPosition().getX(), rookPiece.getPosition().getY());
                for (int kingX = minRange; kingX <= maxRange; kingX++) {
                    Position pos = BoardHelper.getPosFromCoord(kingX, moverKing.getPosition().getY());
                    for (Piece p : getAlivePieceListFromType(enemyRoyalty)) {
                        if (p.canReachTo(pos)) {
                            throw new IllegalMoveException("Cannot castle through check");
                        }
                    }
                }
            } else if (move.getEnpassant()) {
                // Check if the captured pawn can be en-passanted
                Pawn moverPawn = (Pawn) occupiedPositions.get(move.getStartPosition());
                Pawn capturedPawn = (Pawn) occupiedPositions.get(BoardHelper.getPosFromCoord(endPos.getX(), startPos.getY()));
                if (!moverPawn.canEnPassant()) throw new IllegalMoveException("Chosen Pawn cannot EnPassant");
                if (!capturedPawn.canBeEnpassanted())
                    throw new IllegalMoveException("Captured pawn can not be En-Passant");
            } else if (move.getPromoting()) {
                Pawn promotedPawn = (Pawn) pieceMoved;
                if ((Math.abs(endPos.getY() - promotedPawn.getStartPos().getY()) != 6))
                    throw new IllegalMoveException("Pawn doesn't satisfy promote condition");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    // Execute a move, Effectively advancing the game 1 step and Growing the moveStack
    public void executeMove(MoveInfo move) throws UnSyncedMoveTurnException {


    }

    // Undo the previously executed move, Effectively redo the game by 1 step
    @Override
    public void redoMove(MoveInfo move) {

    }

    /*
        This function place a piece to the destinated position

        Note: occupied destination still can be placed on (CHECK THIS)
              IF POS IS OCCUPIED. ---> Capture the piece as well
     */
    public void pickThenPlacePiece(Piece piece, Position endPos, int turn) {
        occupiedPositions.remove(piece.getPosition());
        if (occupiedPositions.containsKey(endPos)) {
            Piece removedPiece = occupiedPositions.get(endPos);
            if (removedPiece.getType() == Type.WHITE) {
                whitePieces.remove(removedPiece);
                capturedWhitePieces.add(removedPiece);
            } else {
                blackPieces.remove(removedPiece);
                capturedWhitePieces.add(removedPiece);
            }
            removedPiece.capturedOnMove = turn;
        }
        occupiedPositions.put(endPos, piece);
    }

    /*
        ADD_PIECE = CreatePiece (from factory) + adjust Bookkeeping Info + Place Piece
     */
    public void addPiece(String pieceClass, Type type, Position pos) {
        Piece piece = pieceFactory.createPiece(pieceClass, type, pos, this);
        this.occupiedPositions.put(pos, piece);
        this.registerObserver(piece);
        if (type == Type.WHITE) {
            whitePieces.add(piece);
        } else {
            blackPieces.add(piece);
        }
        if (pieceClass.equals(Piece.KING)) {
            kings.add((King) piece);
        }
    }

    /*
        This function remove a piece at position (delete its existence: White/black piece list + occupiedPosList)
        return: the piece removed
        NOTE: should throw errors if pos not exist
     */
    public Piece removePiece(Position pos) {
        if (!occupiedPositions.containsKey(pos)) return null;
        Piece pieceRemoved = occupiedPositions.remove(pos);
        if (pieceRemoved.getType() == Type.BLACK) {
            blackPieces.remove(pieceRemoved);
        } else whitePieces.remove(pieceRemoved);
        return pieceRemoved;
    }

    // pos: Used to determine which pawn to promote
    // pieceClass: The new kind of piece to promote to: constant declared in Piece Class
    public void promote(String pieceClass, Position pos, int turn) throws InvalidDestinationException {
        if (!occupiedPositions.containsKey(pos)) throw new InvalidDestinationException("Not exist pos");
        else if (occupiedPositions.get(pos) instanceof Pawn) throw new InvalidDestinationException("Not a Pawn");
        Type royalty = occupiedPositions.get(pos).getType();
        removePiece(pos);
        // Add new piece
        Piece piece = pieceFactory.createPiece(pieceClass, royalty, pos, this);
        if (royalty == Type.BLACK) blackPieces.add(piece);
        else whitePieces.add(piece);
        pickThenPlacePiece(piece, pos, turn);
    }
}
