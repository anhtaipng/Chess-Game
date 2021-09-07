import {createSlice} from "@reduxjs/toolkit";
import BoardHelper from "./BoardHelper";
import {Bishop, King, Knight, Pawn, PIECE_CONSTANT, Queen, Rook} from "../../features/chessgame/Piece";

export const GameConstant = {
    ROOM_CREATED: "CREATED",
    MODE_TIME_RABBIT: "RABBIT",
    MODE_TIME_BULLET: "BULLET",
    MODE_TIME_NORMAL: "NORMAL",
    MODE_GAME_CLASSIC: "CLASSIC",
    MODE_GAME_KOTH: "KOTH",
    MODE_GAME_THREE_CHECK: "THREE_CHECK",
}
/*
    (hashCodePos,piece)
    (hashCodePos,piece)
    (hashCodePos,piece)
    (hashCodePos,piece)
    (hashCodePos,piece)
 */
const initialState = {
    pieces: new Map(),
    captured_pieces: new Map(),
    currentTurn: 1,
    pieceChosen: undefined
}

export const gameSlice = createSlice({
    name: "game", initialState,
    reducers: {
        // Payload should be moveInfo returned by MoveCreator
        userMoved: (state,action) =>{
            const moveInfo = action.payload.move;
            const pos1 = BoardHelper.getPosObjectFromCharNum(moveInfo.piece_start_pos);
            const pos2 = BoardHelper.getPosObjectFromCharNum(moveInfo.piece_end_pos);
            if (BoardHelper.checkMoveValidity(pos1, pos2, state.pieces)) {
                const pos1HashCode = BoardHelper.getHashCodeFromPos(pos1);
                const pos2HashCode = BoardHelper.getHashCodeFromPos(pos2);
                const pieceMoved = state.pieces.get(pos1HashCode);
                state.pieces.delete(pos1HashCode);
                if (state.pieces.has(pos2HashCode)) {
                    state.pieces.delete(pos2HashCode);
                }
                state.pieces.set(pos1HashCode, pieceMoved);
            }
            else throw new Error("Invalid move");
        },
        choosePiece: (state,action) => {
            state.pieceChosen = action.payload.piecePos;
        },
        initClassicGame: (state,action)=>{
            // Pawn
            for (let spot = 21; spot < 28; spot++) {
                state.pieces.set(spot,new Pawn(PIECE_CONSTANT.WHITE_ROYALTY));
            }
            for (let spot = 71; spot < 78; spot++) {
                state.pieces.set(spot, new Pawn(PIECE_CONSTANT.BLACK_ROYALTY));
            }
            //Rook
            state.pieces.set(11, new Rook(PIECE_CONSTANT.WHITE_ROYALTY));
            state.pieces.set(81, new Rook(PIECE_CONSTANT.WHITE_ROYALTY));
            state.pieces.set(18, new Rook(PIECE_CONSTANT.BLACK_ROYALTY));
            state.pieces.set(88, new Rook(PIECE_CONSTANT.BLACK_ROYALTY));
            // Bishop
            state.pieces.set(31, new Bishop(PIECE_CONSTANT.WHITE_ROYALTY));
            state.pieces.set(61, new Bishop(PIECE_CONSTANT.WHITE_ROYALTY));
            state.pieces.set(38, new Bishop(PIECE_CONSTANT.BLACK_ROYALTY));
            state.pieces.set(68, new Bishop(PIECE_CONSTANT.BLACK_ROYALTY));
            // Knight
            state.pieces.set(21, new Knight(PIECE_CONSTANT.WHITE_ROYALTY));
            state.pieces.set(71, new Knight(PIECE_CONSTANT.WHITE_ROYALTY));
            state.pieces.set(28, new Knight(PIECE_CONSTANT.BLACK_ROYALTY));
            state.pieces.set(78, new Knight(PIECE_CONSTANT.BLACK_ROYALTY));
            // King
            state.pieces.set(51, new King(PIECE_CONSTANT.WHITE_ROYALTY));
            state.pieces.set(58, new King(PIECE_CONSTANT.WHITE_ROYALTY));
            // Queen
            state.pieces.set(41, new Queen(PIECE_CONSTANT.BLACK_ROYALTY));
            state.pieces.set(48, new Queen(PIECE_CONSTANT.BLACK_ROYALTY));
        },
        putPiece: (state,action)=>{

        }
    }
});
export default gameSlice.reducer;
export const {userMoved,choosePiece,initClassicGame,putPiece} = gameSlice.actions;