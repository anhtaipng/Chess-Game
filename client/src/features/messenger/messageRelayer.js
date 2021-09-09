import {addChatMessage, addSpectator, updatePlayerInfo} from "../../slices/room/roomSlice";
import {store} from "../../app/store";
export const MessageConstant = {
    MESSAGE_CODE: "Message",
    PLAYER_JOIN_CODE: "PlayerJoin",
    SPECTATOR_JOIN_CODE: "SpectatorJoin",
    MOVE_CODE: "SpectatorJoin"
}

const MessageRelayer = (() => {
    const dispatch = store.dispatch;
    const state = store.getState();
    console.log("Message Relayer Init:", state);
    // eslint-disable-next-line no-unused-vars
    const observers = [];
    const registerObserver = (o) =>{
        observers.push(o);
    };
    const notifyObservers = (mess)=>{
        for (let obs of observers) {
            obs.reactTo(mess);
        }
    }
    const update = (mess) => {
        // Currently log to check if work
        console.log("UI will be notified about the mess:", mess);
        notifyObservers(mess);
        const parts = mess.split();
        switch (parts[1]) {
            case MessageConstant.MESSAGE_CODE:
                dispatch(addChatMessage({user: parts[2], message: parts[3]}));
                break;
            case MessageConstant.PLAYER_JOIN_CODE:
                const state = store.getState();
                console.log("Message Relayer: State when player Joined:", state);
                if (state.player1) {
                    dispatch(updatePlayerInfo({playerNum: 2, playerInfo: {id_user:parts[2]},elo:parts[3]}));
                }
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
    const sendMove = (moveInfo) => {
        const mess = `${state.room.room_id} ${MessageConstant.MOVE_CODE} ${state.user.id} ${moveInfo}`;
        send(mess);
    }
    // const sendJoinRoom =  (roomID,)
    return {sendMove, update};
})();
export default MessageRelayer;
// Every time receive message M from socket.
//      Import MessageRelayer
//      Call MessageRelayer.update(M)