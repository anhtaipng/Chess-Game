import SockJS from "sockjs-client";
import Stomp from 'stompjs';
import {useSelector} from "react-redux";
import MessageRelayer from "./features/messenger/messageRelayer";

var connected = false;
const client = Stomp.over(new SockJS('http://localhost:9777/socket', null, null));



client.connect({}, (props) => {
    const userDisplayName = useSelector((state) => state.user.id_user)
    connected = true;
    client.subscribe('/topic/only-user/' + userDisplayName,
    (msg) =>
    {//day chinh la cho ham update can goi 
        console.log(msg.body + '*******\n');
        MessageRelayer.update(msg.body);
    });
    console.log('Connected');
});

const sendMess = (message) => {
    if (connected)
        client.send('/app/user-all',{}, message);
}

export default sendMess;


