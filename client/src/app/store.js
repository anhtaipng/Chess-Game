import { configureStore } from "@reduxjs/toolkit";
import counterReducer from "../features/counter/counterSlice";
import userReducer from "../slices/user/userSlice";
import roomReducer from "../slices/room/roomSlice";
import gameReducer from "../slices/game/gameSlice";
import { enableMapSet } from 'immer'

console.log(enableMapSet);
enableMapSet();
export const store = configureStore({
    reducer: {
        counter: counterReducer,
        user: userReducer,
        room: roomReducer,
        game: gameReducer,
    },
});