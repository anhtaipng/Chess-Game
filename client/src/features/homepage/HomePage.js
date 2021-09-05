import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';

import styles from './homepage.module.css';
import chess_landing_board from './chess_board_landing.png';
export function HomePage() {
    const dispatch = useDispatch();
    return (
        <div className={styles.landingContainer}>
            <div className={styles.chessHooker}>
                <p className={styles.slogan}>
                    Chess with your friend anywhere, anytime you want
                </p>
                <p className={styles.introductionText}>
                        “The beauty of chess is it can be whatever you want it to be.
                        It transcends language, age, race, religion, politics, gender and socioeconomic background. <br/>
                        Whatever your circumstances, anyone can enjoy a good fight to the death over the chess board.” <br/>
                     <br/>
                    <b>– Simon Williams -</b>
                </p>
                <div className={styles.diveInResources}>
                    <button className={styles.learnChessButton}> Learn Chess</button>
                    <button className={styles.watchChessButton}> Watch Chess</button>
                </div>
            </div>
            <div className={styles.gameReviewImage}>
                <img src={chess_landing_board} alt="Chess board image"/>
            </div>
        </div>
    );
}
