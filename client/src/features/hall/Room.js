// Place Holder for an Room Item
import React from 'react';
import styles from './hall.module.css'
// class Room{
//     String id;
//     GameMode  game_mode;
//     TimeMode time_mode;
// }
const Room = ({roomId,gameMode,timeMode}) => {
    return (
        <div className={styles.roomContainer}>
            <p>{roomId}</p>
            <p>{gameMode}</p>
            <p>{timeMode}</p>
        </div>
    );
};

export default Room;
