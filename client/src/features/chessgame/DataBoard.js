import React from 'react';
import {useSelector} from "react-redux";
import styles from "./databoard.module.css"
const DataBoard = () => {
    const user1Name = useSelector(state => state.room.player1.name);
    const user2Name = useSelector(state => state.room.player2.name);
    return (
        <div className={styles.dataBoardContainer}>
            <div className={styles.user1Nawe}></div>
            <div className={styles.moveBoard}>
                123123123123 <br/>
                123123123123 <br/>
            </div>
            <div className={styles.user2Nawe}></div>
        </div>
    );
};

export default DataBoard;
