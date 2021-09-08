import React from 'react';
import styles from './gameroom.module.css'
import GameMetaBox from "./GameMetaBox";
import GameBoard from "./GameBoard";
import DataBoard from "./DataBoard";

const InGameRoom = () => {
    return (
        <div className={styles.inGameRoom}>
            <GameMetaBox/>
            <GameBoard/>
            <DataBoard/>
        </div>
    );
};

export default InGameRoom;
