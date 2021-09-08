import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {create_room} from "./roomAPI";

export const RoomConstant = {
    ROOM_PENDING: "PENDING",
    ROOM_CREATED: "CREATED",
    MODE_TIME_RABBIT: "RABBIT",
    MODE_TIME_BULLET: "BULLET",
    MODE_TIME_NORMAL: "NORMAL",
    MODE_GAME_CLASSIC: "CLASSIC",
    MODE_GAME_KOTH: "KOTH",
    MODE_GAME_THREE_CHECK: "THREE_CHECK",
}

export const createRoom = createAsyncThunk(
    "room/createRoom",
    async(roomCreateInfo,{rejectedWithValue}) =>{
        try{
            const response = await create_room(roomCreateInfo);
            return response.data;
        }
        catch (e){
            return rejectedWithValue({error: e.message});
        }
    }
)
export const joinRoom = createAsyncThunk(
    "room/joinRoom",
    async(roomJoinInfo,{rejectedWithValue}) =>{
        try{
            const response = await create_room(roomJoinInfo);
            return response.data;
        }
        catch (e){
            return rejectedWithValue({error: e.message});
        }
    }
)

const initialState = {
    room_id: undefined,
    room_status: undefined,
    // Player 1 should be the guy who created the room:
    player1: undefined,
    // Player 2 is the second guy that joined the room
    player2: undefined,
    // Spectator list: Should be a list containing the names of the spectators.
    spectators: [],
    // Modes: Variant and Time control
    variant: undefined,
    time_mode: undefined,
    // Time Clock
    time_player_1: undefined,
    time_player_2: undefined,
    chat_messages: [],
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
            state.spectators.push(action.payload.spectator);
        },
        addChatMessage:(state,action)=>{
            state.chat_messages.push(action.payload.message);
        }
    },
    extraReducers:(builder) =>{
        builder
            .addCase(createRoom.pending, (state, action) =>{
                state.room_status = RoomConstant.ROOM_PENDING;
            })
            .addCase(createRoom.fulfilled, (state, action) =>{
                state.room_id = action.payload.room_id;
                state.variant = action.payload.game_mode;
                state.time_mode = action.payload.time_mode;
                state.room_status = RoomConstant.ROOM_CREATED;
            })
            .addCase(joinRoom.pending, (state, action) =>{
                state.room_status = RoomConstant.ROOM_PENDING;
            })
            .addCase(joinRoom.fulfilled, (state, action) =>{
                state.room_id = action.payload.room_id;
                state.variant = action.payload.game_mode;
                state.time_mode = action.payload.time_mode;
                state.room_status = RoomConstant.ROOM_CREATED;
            })
    }
});

export default roomSlice.reducer;
export const {updatePlayerInfo,decreaseClock,increaseClock,setMode,addSpectator,addChatMessage} = roomSlice.actions;