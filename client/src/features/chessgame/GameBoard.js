import React from 'react';
import styles from './chess.module.css'
import Square from "./Square";
import {useDispatch} from "react-redux";
import {initClassicGame} from "../../slices/game/gameSlice";

const GameBoard = () => {
    const range = (start, stop, step) => Array.from({ length: (stop - start) / step + 1}, (_, i) => start + (i * step));
    const hashCodeArr = range(11,88,1).filter(x => x%10>=1 && x%10 <=8);
    const dispatch = useDispatch();
    return (
        <div>
            <button onClick={dispatch(initClassicGame())}> New Game</button>
            <div className={styles.boardContainer}>
                <div className={styles.boardGrid}>
                    {
                        hashCodeArr.map((hashCode)=>
                            <Square key={hashCode.toString()} posHashCode={hashCode}/>
                        )
                    }
                </div>
            </div>

        </div>
    );
};

export default GameBoard;
