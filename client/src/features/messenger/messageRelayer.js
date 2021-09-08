import {useDispatch} from "react-redux";
import {addChatMessage, addSpectator, updatePlayerInfo} from "../../slices/room/roomSlice";

const MessageConstant = {
    MESSAGE_CODE: "Message",
    PLAYER_JOIN_CODE: "PlayerJoin",
    SPECTATOR_JOIN_CODE: "SpectatorJoin",
    MOVE_CODE: "SpectatorJoin"
}

const MessageRelayer = () => {
    const dispatch = useDispatch();
    // eslint-disable-next-line no-unused-vars
    const update = (mess) => {
        // Currently log to check if work
        console.log("UI will be notified about the mess:", mess);
        const parts = mess.split();
        switch (parts[1]) {
            case MessageConstant.MESSAGE_CODE:
                dispatch(addChatMessage({user: parts[2], message: parts[3]}));
                break;
            case MessageConstant.PLAYER_JOIN_CODE:
                dispatch(updatePlayerInfo({playerNum: 2, playerInfo: {}}));
                break;
            case MessageConstant.SPECTATOR_JOIN_CODE:
                dispatch(addSpectator({spectator: parts[2]}));
                break;
            /* falls through */
            default:
                console.log("Server gửi tin xàm lol :)");
        }
    }
    const send = (mess) => {
        // Do some thing to send the mess to the server:
        console.log("Sending the mess to server:\n", mess)
    }
    const sendMove = (roomID, playerID, moveInfo) => {
        const mess = `${roomID} ${MessageConstant.MOVE_CODE} ${playerID} ${moveInfo}`;
    }
    // const sendJoinRoom =  (roomID,)
}
export default MessageRelayer;
// Every time receive message M from socket.
//      Import MessageRelayer
//      Call MessageRelayer.update(M)