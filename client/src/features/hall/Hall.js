// This component is supposed to show all the game room
import React, {useEffect, useState} from 'react';
import { get_room_list} from "../../slices/room/roomAPI";
import styles from './hall.module.css'
import Room from "./Room";
import {useDispatch, useSelector} from "react-redux";
import {createRoom, joinRoom, RoomConstant} from "../../slices/room/roomSlice";
import {GameConstant} from "../../slices/game/gameSlice";
import WaintingPage from "./WaitingPage";

// class Room{
//     String id;
//     GameMode  game_mode;
//     TimeMode time_mode;
// }

const Hall = () => {
    const [roomList, setRoomList] = useState([]);

    useEffect(() => {
        getRoomList();
    }, [])
    const player = useSelector(state => state.user.id);
    const roomStatus = useSelector(state => state.room.room_status);
    const dispatch = useDispatch();

    const getRoomList = async () => {
        try {
            const response = await get_room_list();
            console.log("Room list fetched", response.data);
            const roomList = response.data;
            setRoomList(roomList);
        } catch (e) {
            return console.log("Get Room list error", e);
        }
    }
    const tryJoinRoom = (roomID) => {
        const joinRoomInfo = {
            room_id: roomID,
            player: player
        }
        console.log("Trying to join a Room with Info:", joinRoomInfo);
        try {
            const res = dispatch(joinRoom(joinRoomInfo));
            // should now dispath the room returned to store:

            console.log("join event:", res);
        } catch (e) {
            console.log("Join room Error:", e);
        }
    }
    const createNewClassicRoom = () => {
        /*
            Body: {
            “player1”: string
            “game_mode”: string // {CLASSIC / KOTH /….}
            “time_mode": string // {BULLET / RAPID / NORMAL}
            }
        */
        const roomCreateInfo = {
            player1: player,
            game_mode: GameConstant.MODE_GAME_CLASSIC,
            time_mode: GameConstant.MODE_TIME_NORMAL
        }
        dispatch(createRoom(roomCreateInfo));
    }
    return (
        (roomStatus === RoomConstant.ROOM_PENDING) ? <WaintingPage/> :
            <div>
                <button onClick={() => createNewClassicRoom()}>
                    Create a New room
                </button>
                <div className={styles.roomListContainer}>
                    {
                        roomList.map((ele) => {
                            return <Room key={ele.id.toString()} roomId={ele.roomId}
                                         gameMode={ele.game_mode} timeMode={ele.time_mode}
                                         onClick={() => tryJoinRoom(ele.id)
                                         }/>
                        })
                    }
                </div>
            </div>
    )
};

export default Hall;
