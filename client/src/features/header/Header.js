import React from 'react';
import styles from './header.module.css';
import logo from '../../logo.png';
import search_icon from '../../images/search_icon.png';
import clash_icon from '../../images/swords.png';
import noti_icon from '../../images/bell.png';
import {useDispatch, useSelector} from "react-redux";
import {UserConstant, userLoggingIn, userRegistering} from "../../slices/user/userSlice";

const Header = props => {
    const iconWidth = "30px";
    const iconHeight = "30px";
    const userLoginStatus = useSelector((state) => state.user.login_status)
    const userDisplayName = useSelector((state) => state.user.displayName)
    const dispatch = useDispatch();
    return (
        <div className={`${styles.containter} ${userLoginStatus === UserConstant.LOGGING_IN ? styles.blurred :''}`}>
            <div className={styles.logoContainer}>
                <img className={styles.logo} src={logo} alt="Normal Logo"/>
                <strong className={styles.logoText}>Chess Master Matters</strong>
            </div>
            <div className={styles.leftToolBar}>
                <button className={styles.button}>
                    Play
                </button>
                <button className={styles.button}>
                    Learn
                </button>
                <button className={styles.button}>
                    Puzzle
                </button>
                <button className={styles.button}>
                    Tool
                </button>
            </div>
            <div className={styles.rightToolBar}>
                <input type="image" className={styles.imageButton} id="search-button" src={search_icon}
                       width={iconWidth} height={iconHeight} alt="Submit Form"/>
                <input type="image" className={styles.imageButton} id="quick-match-button" src={clash_icon}
                       width={iconWidth} height={iconHeight} alt="Submit Form"/>
                <input type="image" className={styles.imageButton} id="notification" src={noti_icon} width={iconWidth}
                       height={iconHeight} alt="Submit Form"/>
                {userLoginStatus !== UserConstant.LOGGED_IN ? (
                    <div>
                        <button className={styles.button} id={styles.loginButton} onClick={() => dispatch(userLoggingIn())}>Login</button>
                        <button className={styles.button} id={styles.registerButton} onClick={() => dispatch(userRegistering())}>Register</button>
                    </div>) : (
                    <span className={styles.userName}>{userDisplayName}</span>)}
            </div>
        </div>
    );
};

export default Header;
