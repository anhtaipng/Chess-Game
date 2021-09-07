import {createSlice} from "@reduxjs/toolkit";

export const RoomConstant = {
    ROOM_CREATED: "CREATED",
    MODE_TIME_RABBIT: "RABBIT",
    MODE_TIME_BULLET: "BULLET",
    MODE_TIME_NORMAL: "NORMAL",
    MODE_GAME_CLASSIC: "CLASSIC",
    MODE_GAME_KOTH: "KOTH",
    MODE_GAME_THREE_CHECK: "THREE_CHECK",
}

const initialState = {
    // Player 1 should be the guy who created the room:
    player1: undefined,
    // Player 2 is the second guy that joined the room
    player2: undefined,
    // Spectator list: Should be a list containing the names of the spectators.
    spectators: [],
    // Modes: Variant and Time control
    vairant: undefined,
    time_mode: undefined,
    // Time Clock
    time_player_1: undefined,
    time_player_2: undefined,
    chat_messages: []
}

export const roomSlice = createSlice({
    name: "room", initialState,
    reducers: {
        updatePlayerInfo: (state,action) =>{
            if(action.payload.playerNum === 1){
                state.player1 = action.payload.playerInfo;
            }
            else if(action.payload.playerNum === 2){
                state.player2 = action.payload.playerInfo;
            }
        },
        decreaseClock:(state,action)=>{
            if(action.payload.playerNum === 1){
                state.time_player_1 -= action.payload.amount;
            }
            else if(action.payload.playerNum === 2){
                state.time_player_2 -= action.payload.amount;
            }
        },
        increaseClock:(state,action)=>{
            if(action.payload.playerNum === 1){
                state.time_player_1 += action.payload.amount;
            }
            else if(action.payload.playerNum === 2){
                state.time_player_2 += action.payload.amount;
            }
        },
        setMode:(state,action)=>{
            state.variant = action.payload.variant;
            state.time_mode = action.payload.time_mode;
        },
        addSpectator:(state,action)=>{
            state.spectators.push(action.payload.spectators);
        },
        addChatMessage:(state,action)=>{
            state.chat_messages.push(action.payload.message);
        }
    }
});

export default roomSlice.reducer;
export const {updatePlayerInfo,decreaseClock,increaseClock,setMode,addSpectator} = roomSlice.actions;