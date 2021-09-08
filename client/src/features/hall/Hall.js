// This component is supposed to show all the game room
import React from 'react';
import axios from "axios";
import {create_room, get_room_list} from "../../slices/room/roomAPI";
import styles from './hall.module.css'
import Room from "./Room";

// class Room{
//     String id;
//     GameMode  game_mode;
//     TimeMode time_mode;
// }

const Hall = async () => {
    const getRoomList = async ()=>{
        try{
            const response = await get_room_list();
            return response.data;
        }
        catch (e){
            return console.log("Get Room list error",e);
        }
    }
    const roomList = await getRoomList();
    const joinRoom = () =>{

    }
    return (
        <div className={styles.roomListContainer}>
            {
                roomList.map((ele)=>{
                    return <Room key={ele.id.toString()} roomId={ele.roomId} gameMode={ele.game_mode} timeMode={ele.time_mode} />
                })
            }
        </div>
    );
};

export default Hall;
