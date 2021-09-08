import React from 'react';
import {useSelector} from "react-redux";
import styles from './chess.module.css';
import classicChessIcon from './chess_image/icon/classic.png'
import {RoomConstant} from "../../slices/room/roomSlice";

const getVariantIconFromString= (variant) =>{
    switch (variant){
        case RoomConstant.MODE_GAME_CLASSIC:
            return classicChessIcon;
        default:
            return classicChessIcon;
    }
}
const GameMetaBox = () => {
    const variant = useSelector(state => state.room.variant);
    const user1 = useSelector(state => state.room.player1);
    const user2 = useSelector(state => state.room.player2);
    const variantImage = getVariantIconFromString(variant);
    return (
        <div>
            <div className={styles.metaBoxContainer}>
                <div className={styles.roomMetaContainer}>
                    <img width="80px" height="80px" src={variantImage} alt={`vairnaasd`}/>
                    <p>
                        Game is playing
                    </p>
                </div>
                <div className={styles.userMetaContainer}>
                    <div className={styles.user1Meta}>
                        <div>black</div>
                        <div className={styles.userName}>{user1.username}</div>
                        <div className ={styles.userElo}>{user1.elo}</div>
                        <div className ={styles.userTitle}>{user1.title}</div>
                    </div>
                    <div className={styles.user2Meta}>
                        <div>black</div>
                        <div className={styles.userName}>{user2.username}</div>
                        <div className ={styles.userElo}>{user2.elo}</div>
                        <div className ={styles.userTitle}>{user2.title}</div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default GameMetaBox;
