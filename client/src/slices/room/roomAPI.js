import axios from "axios";
import {useSelector} from "react-redux";
import {userToken} from "../user/userSlice";
const serverRoot = "http://localhost:9306";
const roomUrl = `${serverRoot}/games`;
/*
    Body: {
    “player1”: string
    “game_mode”: string // {CLASSIC / KOTH /….}
    “time_mode": string // {BULLET / RAPID / NORMAL}
    }
 */
export function create_room(roomCreationInfo){
    console.log('Creating Room: ',roomCreationInfo);
    return axios({
        method: 'post',
        url: roomUrl,
        data: roomCreationInfo,
        headers: {
            'authorization': userToken,
            'Accept' : 'application/json',
            'Content-Type': 'application/json'
        }
    });
}
/*
    POST  $HOME/games/
    Body: {
        “room_id”: string
       “player”: string
    }
 */
export function join_room(roomJoinInfo){
    console.log('Joining Room',roomJoinInfo);
    return axios({
        method: 'post',
        url: roomUrl,
        data: roomJoinInfo,
        headers: {
            'authorization': userToken,
            'Accept' : 'application/json',
            'Content-Type': 'application/json'
        }
    });
}
export function get_room_list(){
    console.log('Getting Room List');
    return axios({
        method: 'get',
        url: roomUrl,
        headers: {
            'authorization': userToken,
            'Accept' : 'application/json',
            'Content-Type': 'application/json'
        }
    });
}