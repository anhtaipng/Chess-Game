import React from 'react';
import styles from './gameroom.module.css'
import GameMetaBox from "./GameMetaBox";
import GameBoard from "./GameBoard";
import DataBoard from "./DataBoard";
import ChatBox from "./ChatBox";

const InGameRoom = () => {
    return (
        <div className={styles.inGameRoom}>
            <div>
                <GameMetaBox/>
                <ChatBox/>
            </div>
            <GameBoard/>
            <DataBoard/>
        </div>
    );
};

export default InGameRoom;
