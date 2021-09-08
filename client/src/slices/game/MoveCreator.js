// {
// “turn”: number (1 / 2/ 3,...)
// 	“player”: “white/black”
// “piece_moved”: “K/N/Q/P/R/B”
// “piece_start_pos”: “X1X2” (X1: A->B; X2: 1-8)
// “piece_end_pos”:”X1X2”
// “castling”: “true/false”
// “promoting”:”true/false”
// “promoted_class”:”K/N/Q/P/R/B” or “null” if not a promotion move
// 	“en_passant”:”true/false”
// }
import {PIECE_CONSTANT} from "../../features/chessgame/Piece";

export const creatMove = (turn, piece_moved, piece_start_pos, piece_end_pos) =>{
    const player = turn % 2 === 1 ? PIECE_CONSTANT.WHITE_ROYALTY : PIECE_CONSTANT.BLACK_ROYALTY;
    return {
        turn: turn,
        player,
        piece_moved,
        piece_start_pos, piece_end_pos,
        castling: false,
        promoting: false,
        promoted_class: "null",
        en_passant: false,
    }
}

export const creatCastlingMove = (turn, piece_start_pos, piece_end_pos) =>{
    const player = turn % 2 === 1 ? PIECE_CONSTANT.WHITE_ROYALTY : PIECE_CONSTANT.BLACK_ROYALTY;
    return {
        turn: turn,
        player,
        piece_moved: PIECE_CONSTANT.KING,
        piece_start_pos, piece_end_pos,
        castling: true,
        promoting: false,
        promoted_class: "null",
        en_passant: false,
    }
}
export const createPromotionMove = (turn, piece_start_pos, piece_end_pos) =>{
    const player = turn % 2 === 1 ? PIECE_CONSTANT.WHITE_ROYALTY : PIECE_CONSTANT.BLACK_ROYALTY;
    return {
        turn: turn,
        player,
        piece_moved: PIECE_CONSTANT.PAWN,
        piece_start_pos, piece_end_pos,
        castling: false,
        promoting: false,
        promoted_class: "null",
        en_passant: false,
    }
}
export const createEnpassantMove = (turn, piece_start_pos, piece_end_pos) =>{
    const player = turn % 2 === 1 ? PIECE_CONSTANT.WHITE_ROYALTY : PIECE_CONSTANT.BLACK_ROYALTY;
    return {
        turn: turn,
        player,
        piece_moved: PIECE_CONSTANT.PAWN,
        piece_start_pos, piece_end_pos,
        castling: false,
        promoting: false,
        promoted_class: "null",
        en_passant: true,
    }
}
