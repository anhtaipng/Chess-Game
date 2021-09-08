// Place Holder for an Room Item
import React from 'react';
import styles from 'hall.module.css'
// class Room{
//     String id;
//     GameMode  game_mode;
//     TimeMode time_mode;
// }
const Room = (props) => {
    return (
        <div className={styles.roomContainer}>
            <p>{props.roomId}</p>
            <p>{props.gameMode}</p>
            <p>{props.timeMode}</p>
        </div>
    );
};

export default Room;
