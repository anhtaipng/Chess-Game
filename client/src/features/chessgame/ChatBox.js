import React from 'react';
import styles from './chatbox.module.css';
import {useSelector} from "react-redux";
import {Message} from "react-bulma-components";

const CustomMessageItem = (props) =>{
    return (
        <div className={styles.messageItem}>
            <span className="tag is-primary">{props.title}</span>
            <span className="tag is-info is-light">{props.body}</span>
        </div>
    );
}

const ChatBox = () => {
    const messages = useSelector(state => state.room.chat_messages);
    console.log("Chat box now has messagees:", messages);
    return (
        <div className={styles.chatBoxContainer}>
            {
                messages.map(item => <CustomMessageItem title={item.user} body={item.message}/>)
            }
        </div>
    );
};

export default ChatBox;
