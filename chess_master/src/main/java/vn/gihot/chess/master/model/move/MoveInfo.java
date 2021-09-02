package vn.gihot.chess.master.model.move;

import vn.gihot.chess.master.model.exception.InvalidPositionFormatException;
import vn.gihot.chess.master.model.game.board.BoardHelper;
import vn.gihot.chess.master.model.game.board.Position;
import vn.gihot.chess.master.model.game.piece.Type;

/* Encapsulate move for a chess game:
    “turn”: number (1 / 2/ 3,...)
	“player”: “white/black”
	“piece_moved”: “K/N/Q/P/R/B”
	“piece_start_pos”: “X1X2” (X1: A->B; X2: 1-8)
	“piece_end_pos”:”X1X2”
	“castling”: “true/false”
	“promoting”:”true/false”
	“promoted_class”:”K/N/Q/P/R/B” or “null” if not a promotion move
	“en_passant”:”true/false”

   NOTE: camelCase methods will return the information converted to some other type
         snake_case methods will return the information as string (The same as it is stored);
         the actual move info stored is: 1.White player 2.Black Player 3.White Player 4.Player
         (For ease of redo and checking en-passant conditoin)
*/

public class MoveInfo {
    public static String WHITE_PLAYER ="white";
    public static String BLACK_PLAYER ="black";
    private final String turn;
    private final String player;
    private final String piece_moved;
    private final String piece_start_pos;
    private final String piece_end_pos;
    private final String castling;
    private final String promoting;
    private final String promotedClass;
    private final String en_passant;

    public MoveInfo(String turn, String player, String piece_moved, String piece_start_pos, String piece_end_pos, String castling, String promoting, String promotedClass, String en_passant) {
        this.turn = turn;
        this.player = player;
        this.piece_moved = piece_moved;
        this.piece_start_pos = piece_start_pos;
        this.piece_end_pos = piece_end_pos;
        this.castling = castling;
        this.promoting = promoting;
        this.promotedClass = promotedClass;
        this.en_passant = en_passant;
    }

    public String get_turn() {
        return turn;
    }

    public int getTurn() {
        return Integer.parseInt(turn);
    }

    public String getPlayer() {
        return player;
    }

    public Type getPlayerType() {
        return (player.equals("white")) ? Type.WHITE : Type.BLACK;
    }

    public Type getOpponentType() {
        return (player.equals("white")) ? Type.BLACK : Type.WHITE;
    }

    public String get_piece_moved() {
        return piece_moved;
    }

    public String get_piece_start_pos() {
        return piece_start_pos;
    }

    public Position getStartPosition() throws InvalidPositionFormatException {
        return BoardHelper.getPositionFromCharNum(piece_start_pos);
    }

    public String get_piece_end_pos() {
        return piece_end_pos;
    }

    public Position getEndPosition() throws InvalidPositionFormatException {
        return BoardHelper.getPositionFromCharNum(piece_end_pos);
    }

    public String get_castling() {
        return castling;
    }

    public boolean getCastling() {
        return Boolean.parseBoolean(castling);
    }

    public String get_promoting() {
        return promoting;
    }

    public boolean getPromoting() {
        return Boolean.parseBoolean(promoting);
    }

    public String get_en_passant() {
        return en_passant;
    }

    public boolean getEnpassant() {
        return Boolean.parseBoolean(en_passant);
    }

    public String getPromotedClass() {
        return promotedClass;
    }
}
