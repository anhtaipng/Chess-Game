import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {create_room, join_room} from "./roomAPI";
import {userToken} from "../user/userSlice";

export const RoomConstant = {
    ROOM_PENDING: "PENDING",
    ROOM_CREATED: "CREATED",
    ROOM_FAILED: "FAILED",
    ROOM_PLAYING: "PLAYING",
    MODE_TIME_RABBIT: "RABBIT",
    MODE_TIME_BULLET: "BULLET",
    MODE_TIME_NORMAL: "NORMAL",
    MODE_GAME_CLASSIC: "CLASSIC",
    MODE_GAME_KOTH: "KOTH",
    MODE_GAME_THREE_CHECK: "THREE_CHECK",
}

export const createRoom = createAsyncThunk(
    "room/createRoom",
    async(roomCreateInfo,{rejectWithValue}) =>{
        try{
            const response = await create_room(roomCreateInfo);
            return response.data;
        }
        catch (e){
            if (!e.response) {
                throw e;
            }

            return rejectWithValue(e.response.data);
        }
    }
)
export const joinRoom = createAsyncThunk(
    "room/joinRoom",
    async(roomJoinInfo,{rejectWithValue}) =>{
        try{
            const response = await join_room(roomJoinInfo);
            return response.data;
        }
        catch (e){
            if (!e.response) {
                throw e;
            }

            return rejectWithValue(e.response.data);        }
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
    move_message:[],
    room_error_mess:undefined
}



export const roomSlice = createSlice({
    name: "room", initialState,
    reducers: {
        updatePlayerInfo: (state, action) => {
            if (action.payload.playerNum === 1) {
                state.player1 = action.payload.playerInfo;
            } else if (action.payload.playerNum === 2) {
                state.player2 = action.payload.playerInfo;
            }
            state.room_status = RoomConstant.ROOM_PLAYING;
        },
        decreaseClock: (state, action) => {
            if (action.payload.playerNum === 1) {
                state.time_player_1 -= action.payload.amount;
            } else if (action.payload.playerNum === 2) {
                state.time_player_2 -= action.payload.amount;
            }
        },
        increaseClock: (state, action) => {
            if (action.payload.playerNum === 1) {
                state.time_player_1 += action.payload.amount;
            } else if (action.payload.playerNum === 2) {
                state.time_player_2 += action.payload.amount;
            }
        },
        setMode: (state, action) => {
            state.variant = action.payload.variant;
            state.time_mode = action.payload.time_mode;
        },
        addSpectator: (state, action) => {
            state.spectators.push(action.payload.spectator);
        },
        addChatMessage: (state, action) => {
            state.chat_messages.push(action.payload);
        },
        addMoveMessage: (state,action) =>{
            state.move_message.push(action.payload);
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(createRoom.pending, (state, action) => {
                console.log("Creat room with token", userToken);
                state.room_status = RoomConstant.ROOM_PENDING;
            })
            .addCase(createRoom.fulfilled, (state, action) => {
                console.log("Room Info returned by Server:", action.payload);
                state.room_id = action.payload.id_room;
                state.variant = action.payload.game_mode;
                state.time_mode = action.payload.time_mode;
                state.room_status = RoomConstant.ROOM_CREATED;
                state.player1 = action.payload;
                console.log("Room Created with ID:", action.payload.room_id);
            })
            .addCase(createRoom.rejected, (state, action) => {
                state.room_status = RoomConstant.ROOM_FAILED;
                state.room_error_message = action.payload;
                state.player1 = action.payload;
            })
            .addCase(joinRoom.pending, (state, action) => {
                state.room_status = RoomConstant.ROOM_PENDING;
            })
            .addCase(joinRoom.fulfilled, (state, action) => {
                state.room_id = action.payload.room.id;
                state.variant = action.payload.room.gameMode;
                state.time_mode = action.payload.timeMode;
                state.player1 = action.payload.player1;
                state.player2 = action.payload.player2;
                state.room_status = RoomConstant.ROOM_PLAYING;
            })
            .addCase(joinRoom.rejected, (state, action) => {
                state.room_status = RoomConstant.ROOM_FAILED;
                state.room_error_messsage = action.payload;
            })
    }
});

export default roomSlice.reducer;
export const {updatePlayerInfo,increaseClock,setMode,addSpectator,addChatMessage} = roomSlice.actions;
