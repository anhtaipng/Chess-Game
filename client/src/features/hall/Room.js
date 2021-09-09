// Place Holder for an Room Item
import React from 'react';
import styles from './hall.module.css'
// class Room{
//     String id;
//     GameMode  game_mode;
//     TimeMode time_mode;
// }
const Room = ({roomId,gameMode,timeMode,onClick}) => {
    return (
        <button tabIndex={roomId} className={styles.roomContainer} onClick={()=>onClick()}>
            <p>{roomId}</p>
            <p>{gameMode}</p>
            <p>{timeMode}</p>
        </button>
    );
};

export default Room;
