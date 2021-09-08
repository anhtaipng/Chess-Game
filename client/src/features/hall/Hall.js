// This component is supposed to show all the game room
import React, {useEffect, useState} from 'react';
import axios from "axios";
import {create_room, get_room_list, join_room} from "../../slices/room/roomAPI";
import styles from './hall.module.css'
import Room from "./Room";
import {useDispatch, useSelector} from "react-redux";
import {joinRoom} from "../../slices/room/roomSlice";

// class Room{
//     String id;
//     GameMode  game_mode;
//     TimeMode time_mode;
// }

const Hall = () => {
    const [roomList, setRoomList] = useState([]);

    useEffect(()=>{
        getRoomList();
    },[])
    const player = useSelector(state => state.user.id);
    const dispatch = useDispatch();

    const getRoomList = async ()=>{
        try{
            const response = await get_room_list();
            console.log("Room list fetched", response.data);
            const roomList = response.data;
            setRoomList(roomList);
        }
        catch (e){
            return console.log("Get Room list error",e);
        }
    }
    const tryJoinRoom = (roomID) => {
        const joinRoomInfo = {
            room_id: roomID,
            player: player
        }
        console.log("Trying to join a Room with Info:",joinRoomInfo);
        try{
            const res =  dispatch(joinRoom(joinRoomInfo));
            // should now dispath the room returned to store:

            console.log("join event:", res);
        }
        catch (e) {
            console.log("Join room Error:", e);
        }
    }
    return (
        <div className={styles.roomListContainer}>
            {
                roomList.map((ele)=>{
                    return <Room key={ele.id.toString()} roomId={ele.roomId}
                                 gameMode={ele.game_mode} timeMode={ele.time_mode}
                    onClick={() => tryJoinRoom(ele.id)
                    }/>
                })
            }
        </div>
    );
};

export default Hall;
