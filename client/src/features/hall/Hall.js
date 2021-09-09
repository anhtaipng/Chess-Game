// This component is supposed to show all the game room
import React, {useEffect, useState} from 'react';
import {get_room_list} from "../../slices/room/roomAPI";
import styles from './hall.module.css'
import Room from "./Room";
import {useDispatch, useSelector} from "react-redux";
import {createRoom, joinRoom, RoomConstant} from "../../slices/room/roomSlice";
import {GameConstant} from "../../slices/game/gameSlice";
import WaitingPage from "./WaitingPage";
import GameMetaBox from "../chessgame/GameMetaBox";
import InGameRoom from "../chessgame/InGameRoom";
import DismissableAlert from "../alert/DismissableAlert";

const Hall = () => {
    const [roomList, setRoomList] = useState([]);

    useEffect(() => {
        getRoomList();
    }, [])
    const player = useSelector(state => state.user.id_user);
    const roomStatus = useSelector(state => state.room.room_status);
    const player2 = useSelector(state => state.room.player2);
    const errorMessage = useSelector(state => state.room.room_error_message);
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
    const tryJoinRoom = async (roomID) => {
        const joinRoomInfo = {
            room_id: roomID,
            player: player
        }
        console.log("Trying to join a Room with Info:", joinRoomInfo);
        try {
            const res = await dispatch(joinRoom(joinRoomInfo));
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
        <>
            {
                (() => {
                    if (roomStatus === RoomConstant.ROOM_PENDING) {
                        return <WaitingPage/>;
                    } else if (roomStatus === RoomConstant.ROOM_CREATED && !player2) {
                        return <div>
                            <WaitingPage/>
                            <GameMetaBox isWaiting={true}/>
                        </div>
                    } else if(roomStatus === RoomConstant.ROOM_PLAYING) {
                        return <InGameRoom/>
                    } else if (roomStatus === RoomConstant.ROOM_FAILED) {
                        return <DismissableAlert heading={`Room failed. Something is wrong.`} message={errorMessage ? errorMessage : `UNKNOWN ERRRO`} type={`danger`}/>
                    } else {
                        return <div>
                            <button onClick={() => createNewClassicRoom()}>
                                Create a New room
                            </button>
                            <div className={styles.roomListContainer}>
                                {
                                    roomList.map((ele, index) => {
                                        console.log("Room Found:", ele);
                                        return <Room tabIndex={index} key={ele.id.toString()} roomId={ele.id}
                                                     gameMode={ele.gameMode} timeMode={ele.timeMode}
                                                     onClick={() => tryJoinRoom(ele.id)
                                                     }/>
                                    })
                                }
                            </div>
                        </div>;
                    }
                })()
            }

        </>
    )
};
export default Hall;


// (roomStatus === RoomConstant.ROOM_PENDING || (roomStatus === RoomConstant.ROOM_PENDING && !player2) ) ? <WaintingPage/> :
//     <div>
//         <button onClick={() => createNewClassicRoom()}>
//             Create a New room
//         </button>
//         <div className={styles.roomListContainer}>
//             {
//                 roomList.map((ele) => {
//                     return <Room key={ele.id.toString()} roomId={ele.roomId}
//                                  gameMode={ele.game_mode} timeMode={ele.time_mode}
//                                  onClick={() => tryJoinRoom(ele.id)
//                                  }/>
//                 })
//             }
//         </div>
//     </div>


// return (
//     <>
//         {
//             () => {

//             }
//         }
//     </>
// )