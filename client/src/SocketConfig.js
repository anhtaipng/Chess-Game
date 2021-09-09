import SockJS from "sockjs-client";
import Stomp from 'stompjs';
import {useSelector} from "react-redux";
import MessageRelayer from "./features/messenger/messageRelayer";
import MessageObserver from "./features/messenger/messageObserver";
import { store } from "./app/store";


const SocketConfig = (() =>{
    var connected = false;
    var client;
    const connectCreation = () =>{
        client = Stomp.over(new SockJS('http://localhost:9777/socket', null, null));
        client.connect({}, (props) => {
            const userDisplayName = store.getState().user.id_user;
            connected = true;
            console.log("USER ID FOUND:",userDisplayName);
            client.subscribe('/topic/only-user/' + userDisplayName,
            (msg) =>
            {//day chinh la cho ham update can goi 
                console.log(msg.body + '*******\n');
                MessageRelayer.update(msg.body);
            });
            console.log('Connected');
        });
        
    }
    // const observer =  new MessageObserver(); 
    // observer.registerMessAndCallBack("client_connect",connectCreation);
    // MessageRelayer.registerObserver(observer);
    
    
    const sendMess = (message) => {
        if (connected)
            client.send('/app/user-all',{}, message);
    }
  
    return {connectCreation,sendMess};
})();


export default SocketConfig;


