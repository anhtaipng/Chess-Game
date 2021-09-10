import {addChatMessage, addSpectator, updatePlayerInfo} from "../../slices/room/roomSlice";
import {store} from "../../app/store";

import SocketConfig from "../../SocketConfig";

export const MessageConstant = {
    MESSAGE_CODE: "Message",
    PLAYER_JOIN_CODE: "PlayerJoin",
    SPECTATOR_JOIN_CODE: "SpectatorJoin",
    MOVE_CODE: "Move"
}

const MessageRelayer = (() => {
    const dispatch = store.dispatch;
    const state = store.getState();
    console.log("Message Relayer Init:", state);
    // eslint-disable-next-line no-unused-vars
    const observers = [];
    const registerObserver = (o) => {
        observers.push(o);
    };
    const notifyObservers = (mess) => {
        for (let obs of observers) {
            obs.reactTo(mess);
        }
    }
    const update = (mess) => {
        // Currently log to check if work
        console.log("UI will be notified about the mess:", mess);
        console.log("OBSERVER LIST:", observers);
        notifyObservers(mess);
        const parts = mess.split(" ");
        console.log("Spitted String is:", parts, parts[1]);
        switch (parts[1]) {
            case MessageConstant.MESSAGE_CODE:
                dispatch(addChatMessage({user: parts[2], message: parts[3]}));
                break;
            case MessageConstant.PLAYER_JOIN_CODE:
                const state = store.getState();
                console.log("Message Relayer: State when player Joined:", state);
                if (state.room.player1) {
                    console.log("Update PLay 2 info");
                    dispatch(updatePlayerInfo({playerNum: 2, playerInfo: {playerID: parts[2]}, elo: parts[3],displayName:"black",playerRoyalty:"BLACK"}));
                }
                break;
            case MessageConstant.SPECTATOR_JOIN_CODE:
                dispatch(addSpectator({spectator: parts[2]}));
                break;
            /* falls through */
            case MessageConstant.MOVE_CODE:
                // dispatch(addMove());
            default:
                console.log("Redux don't care về message này:)");
        }
    }
    const send = (mess) => {
        // Do some thing to send the mess to the server:
        SocketConfig.sendMess(mess);
        console.log("Sending the mess to server:\n", mess);
    }
    const sendMove = (moveInfo) => {
        const state = store.getState();
        const mess = `${state.room.room_id} ${MessageConstant.MOVE_CODE} ${state.user.id_user} ${JSON.stringify(moveInfo)}`;
        send(mess);
    }
    // const sendJoinRoom =  (roomID,)
    return {sendMove, update, registerObserver,send};
})();
export default MessageRelayer;
// Every time receive message M from socket.
//      Import MessageRelayer
//      Call MessageRelayer.update(M)