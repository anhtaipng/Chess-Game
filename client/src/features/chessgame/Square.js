import React, {useState} from 'react';
import BoardHelper from "../../slices/game/BoardHelper";
import styles from "./chess.module.css"
import {useSelector} from "react-redux";


const isEven= (num) => num%2 === 0;

const Square = (props) => {
    const [userChoosePiece, setUserChoosePiece] = useState(false);
    return (
        <div tabIndex={props.tabIndex} className={`${styles.square} ${props.isLightSquare? styles.lightSquare : styles.darkSquare}`} style={props.image ?{backgroundImage: `url(${props.image})`}:{}} onClick={() => props.onClick(props.posHashCode)} >
        </div>
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
