import React, {useState} from 'react';
import styles from './chatbox.module.css';
import {useSelector} from "react-redux";
import {Button,Tag} from "react-bulma-components";
import MessageRelayer from "../messenger/messageRelayer";

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
    const [mess, setMess] = useState("");

    return (
        <div className={`${styles.chatBoxContainer} box`}>
            <Tag backgroundColor={`dark`} color={`primary`}>Chat Box</Tag>
            {
                messages.map(item => <CustomMessageItem title={item.user} body={item.message}/>)
            }
            <div className={styles.messageTypeBox}>
                <input className={`input is-info`}  type="text" placeholder="Info input"
                       onChange={(e)=>setMess(e.target.value)} value={mess}
                       onKeyPress={event => {
                           if (event.key === 'Enter') {
                               MessageRelayer.sendMessageToServer(mess); setMess("");
                           }
                       }}/>
                <Button
                    color="success"
                    renderAs="span"
                    onClick={() => {MessageRelayer.sendMessageToServer(mess); setMess("")}}
                >
                    Send
                </Button>
            </div>
        </div>
    );
};

export default ChatBox;
