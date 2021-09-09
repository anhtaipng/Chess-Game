import React, {useReducer, useState} from 'react';
import styles from './chess.module.css';
import Square from "./Square";
import BoardHelper from "../../slices/game/BoardHelper";
import {Bishop, King, Knight, Pawn, PIECE_CONSTANT, Queen, Rook} from "./Piece";
import {GameConstant} from "../../slices/game/gameSlice";
import MessageRelayer from "../messenger/messageRelayer";
import MoveCreator from "../../slices/game/MoveCreator";
import MessageObserver from "../messenger/messageObserver";
import {useSelector} from "react-redux";
import {toast} from "react-toastify";

const isEven = (num) => num % 2 === 0;
const range = (start, stop, step) => Array.from({length: (stop - start) / step + 1}, (_, i) => start + (i * step));

const GAME_ACTION = {
    USER_MOVED: "USER_MOVED",
    INIT_CLASSIC_GAME: "INIT_CLASSIC_GAME",
    CHOOSE_PIECE: "CHOOSE_PIECE",
    MOVE_VALIDATED: "CHOOSE_DEST",
}
const TURN_CONSTANTT = {
    ODD_TURN: "ODD",
    EVEN_TURN:"EVEN",
    SPECTATE_ONLY: "SPEC"
}
const initialState = {
    pieces: new Map(),
    captured_pieces: [],
    currentTurn: 1,
    pieceSrcHashCode: undefined,
    pieceDestinationHashCode: undefined
}
const init = (gameMode) => {
    switch (gameMode) {
        case GameConstant.MODE_GAME_CLASSIC:
            console.log("INITING CLASSIC GAME");
            const pieces_copy = new Map();
            const pawn_arr1 = range(21, 28, 1);
            const pawn_arr2 = range(71, 78, 1);
            for (let ele of pawn_arr1) {
                pieces_copy.set(ele, new Pawn(PIECE_CONSTANT.WHITE_ROYALTY));
            }
            for (let ele of pawn_arr2) {
                pieces_copy.set(ele, new Pawn(PIECE_CONSTANT.BLACK_ROYALTY));
            }
            //Rook
            pieces_copy.set(11, new Rook(PIECE_CONSTANT.WHITE_ROYALTY));
            pieces_copy.set(18, new Rook(PIECE_CONSTANT.WHITE_ROYALTY));
            pieces_copy.set(81, new Rook(PIECE_CONSTANT.BLACK_ROYALTY));
            pieces_copy.set(88, new Rook(PIECE_CONSTANT.BLACK_ROYALTY));
            // Bishop
            pieces_copy.set(13, new Bishop(PIECE_CONSTANT.WHITE_ROYALTY));
            pieces_copy.set(16, new Bishop(PIECE_CONSTANT.WHITE_ROYALTY));
            pieces_copy.set(83, new Bishop(PIECE_CONSTANT.BLACK_ROYALTY));
            pieces_copy.set(86, new Bishop(PIECE_CONSTANT.BLACK_ROYALTY));
            // Knight
            pieces_copy.set(12, new Knight(PIECE_CONSTANT.WHITE_ROYALTY));
            pieces_copy.set(17, new Knight(PIECE_CONSTANT.WHITE_ROYALTY));
            pieces_copy.set(82, new Knight(PIECE_CONSTANT.BLACK_ROYALTY));
            pieces_copy.set(87, new Knight(PIECE_CONSTANT.BLACK_ROYALTY));
            // King
            pieces_copy.set(15, new King(PIECE_CONSTANT.WHITE_ROYALTY));
            pieces_copy.set(85, new King(PIECE_CONSTANT.BLACK_ROYALTY));
            // Queen
            pieces_copy.set(14, new Queen(PIECE_CONSTANT.WHITE_ROYALTY));
            pieces_copy.set(84, new Queen(PIECE_CONSTANT.BLACK_ROYALTY));
            return {
                pieces: pieces_copy,
                captured_pieces: new Map(),
                currentTurn: 1,
                pieceChosen: undefined
            };
        default:
            return initialState;
    }
}

const reducer = (state, action) => {
    switch (action.type) {
        case GAME_ACTION.INIT_CLASSIC_GAME: {
            return init(GameConstant.MODE_GAME_CLASSIC);
        }
        case GAME_ACTION.CHOOSE_PIECE: {
            // state.pieceChosen = action.payload.piecePos;
            let src_piece = action.payload.srcHashCode;
            return {...state, pieceSrcHashCode: src_piece};
        }
        case GAME_ACTION.MOVE_VALIDATED:
            let piece_dest = action.payload.destHashCode;
            let piece_src = action.payload.srcHashCode;
            let pieces_fallen = [...state.captured_pieces];
            const pieces_copy = new Map(state.pieces);
            // Try execute the move;
            const srcHashCode = piece_src;
            const pieceMoved = pieces_copy.get(srcHashCode);
            pieces_copy.delete(srcHashCode);
            if (pieces_copy.has(piece_dest)) {
                const piece_dead = pieces_copy.get(piece_dest);
                pieces_fallen.push(piece_dead);
                pieces_copy.delete(piece_dest);
            }
            pieces_copy.set(piece_dest, pieceMoved);
            const newTurn = state.currentTurn + 1;
            // MessageRelayer.send()
            return {
                ...state,
                pieces: pieces_copy,
                pieceSrcHashCode: undefined,
                currentTurn: newTurn,
                captured_pieces: pieces_fallen
            };
        default:
            throw new Error();
    }
}
const GameBoard = () => {
    const hashCodeArr = range(11, 88, 1).filter(x => x % 10 >= 1 && x % 10 <= 8);
    // const dispatchToStore = useDispatch();
    // const sampleMove = creatMove(1,"P","A2","A4");
    // console.log("Sample move created:",sampleMove,JSON.stringify(sampleMove));
    const [state, dispatch] = useReducer(reducer, initialState, undefined);
    const thisUser = useSelector(state.user.id);
    const thisPlayer1 = useSelector(state.room.player1);
    const thisPlayer2 = useSelector(state.room.player2);
    const myValidTurn = (() => {
        if (thisUser === thisPlayer1.id) {
            return TURN_CONSTANTT.ODD_TURN;
        }
        else if (thisUser === thisPlayer2.id) {
            return TURN_CONSTANTT.EVEN_TURN;
        }
        else return TURN_CONSTANTT.SPECTATE_ONLY;
    })();
    const initClassicGame = () => {
        dispatch({type: GAME_ACTION.INIT_CLASSIC_GAME});
        console.log(state);
    }
    // Message related computations
    const messageObserver = new MessageObserver();
    const socketMoveMessageHandler = (moveInfo) => {
        console.log("Board Received move:", moveInfo);
        const endPosHashCode = BoardHelper.getHashCodeFromCharNum(moveInfo.piece_end_pos);
        const startPosHashCode = BoardHelper.getHashCodeFromCharNum(moveInfo.piece_start_pos);
        dispatch({type: GAME_ACTION.MOVE_VALIDATED,payload:{
                srcHashCode: startPosHashCode,
                destHashCode: endPosHashCode
            }})
    }

    messageObserver.registerMoveCallBack(socketMoveMessageHandler);
    // Piece Choosing and execute move Logic
    const checkValidTurn = () =>{
        if(myValidTurn === TURN_CONSTANTT.ODD_TURN && state.currentTurn % 2 === 1){
            return true;
        }
        else if(myValidTurn === TURN_CONSTANTT.ODD_TURN && state.currentTurn % 2 === 0){
            return true;
        }
        else{
            return false;
        }
    }
    const handleSquareClick = (posHashCode) => {
        if(checkValidTurn()) {
            if (!state.pieceSrcHashCode) {
                dispatch({
                    type: GAME_ACTION.CHOOSE_PIECE, payload: {
                        srcHashCode: posHashCode
                    }
                })
                console.log("Piece chosen:" + posHashCode);
            } else {
                // Send socket move to server
                const pieceMoved = state.pieces.get(state.srcHashCode);
                const moveInfo = MoveCreator.creatMove(state.currentTurn, pieceMoved.type, state.srcHashCode, posHashCode);
                console.log("Validating pieceMoved:", moveInfo);
                MessageRelayer.sendMove(moveInfo);
                console.log("Trying to move To" + posHashCode)
            }
        }
        else{
            toast.error('🦄 Wow so easy!', {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
            });
        }
    }
    return (
        <div className={styles.container}>
            <button onClick={initClassicGame}> New Game</button>
            <div className={styles.boardContainer}>
                <div className={styles.boardGrid}>
                    {
                        hashCodeArr.map((hashCode) => {
                                const {x, y} = BoardHelper.getPosFromHashCode(hashCode);
                                const isLightSquare = (isEven(y) && !isEven(x)) || (!isEven(y) && isEven(x));
                                const image = state.pieces.has(hashCode) ? state.pieces.get(hashCode).image : undefined;
                                console.log("The board is reloaded");
                                return <Square tabIndex={hashCode.toString()} key={hashCode.toString()}
                                               posHashCode={hashCode}
                                               dispatch={dispatch} isLightSquare={isLightSquare} image={image}
                                               onClick={(hashCode) => handleSquareClick(hashCode)}
                                />
                            }
                        )
                    }
                </div>
            </div>
        </div>
    );
};

export default GameBoard;
