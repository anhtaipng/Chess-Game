import SockJS from "sockjs-client";
import Stomp from 'stompjs';
import MessageRelayer from "./features/messenger/messageRelayer";
import { store } from "./app/store";


class SocketConfig {
    constructor() {
        this.connected = false;
        this.client = Stomp.over(new SockJS('http://localhost:9777/socket'), null, null);
    }

    connectCreation(){
        this.client = Stomp.over(new SockJS('http://localhost:9777/socket', null, null));
        this.client.connect({}, (props) => {
            const userDisplayName = store.getState().user.id_user;
            this.connected = true;
            console.log("USER ID FOUND:",userDisplayName);
            this.client.subscribe('/topic/only-user/' + userDisplayName,
            (msg) =>
            {
                console.log(msg.body + '*******\n');
                MessageRelayer.update(msg.body);
            });
            console.log('Connected');
        });
        
    }
    // const observer =  new MessageObserver(); 
    // observer.registerMessAndCallBack("client_connect",connectCreation);
    // MessageRelayer.registerObserver(observer);
    getClient(){
        console.log("Getting the client:", this.client);
        return this.client;
    }
    sendMess(message){
        console.log("Thinh Socket send:", message);
        if (this.connected)
            this.client.send('/app/user-all',{}, message);
    }
}

const socket = new SocketConfig();
export default socket;



