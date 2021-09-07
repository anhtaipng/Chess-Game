import React, {useState} from 'react';
import BoardHelper from "../../slices/game/BoardHelper";
import styles from "./chess.module.css"
import {useSelector} from "react-redux";


const isEven= (num) => num%2 === 0;

const Square = (props) => {
    const thisSpotHashCode = props.posHashCode;
    const pieces =  useSelector(state => state.game.pieces);
    const piece = pieces.get(thisSpotHashCode);
    const [userChoosePiece, setUserChoosePiece] = useState(false);
    const {x, y} = BoardHelper.getPosFromHashCode(props.posHashCode);
    const isLightSquare = (isEven(y) && !isEven(x)) || (!isEven(y) && isEven(x));

    return (
        <button className={`${styles.square} ${isLightSquare? styles.lightSquare : styles.darkSquare}`} style={piece.image ?{backgroundImage: `url(${piece.image})`}:{}} >
        </button>
    );
};

export default Square;


// const getPieceCssClass = (pieceType)=>{
//     switch (pieceType){
//         case PIECE_CONSTANT.PAWN:
//             return styles.pawn;
//         case PIECE_CONSTANT.KING:
//             return styles.king;
//         case PIECE_CONSTANT.ROOK:
//             return styles.rook;
//         case PIECE_CONSTANT.QUEEN:
//             return styles.queen;
//         case PIECE_CONSTANT.KNIGHT:
//             return styles.knight;
//         default:
//             return "";
//     }
// }
