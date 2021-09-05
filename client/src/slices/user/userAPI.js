// A mock function to mimic making an async request for data


import axios from "axios";

const serverRoot = "http://localhost:9306";
const baseUrl = `${serverRoot}/user/profile/`;
const logUrl = `${serverRoot}/authenticate`;
const regUrl = `${serverRoot}/register`;

export function fetchElo() {
    return fetch(baseUrl, {
        method: "GET",
        mode: "cors",
        headers: {
            'Content-Type': 'application/json'}
    });
}

export function fetchUser(userID) {
    return fetch(baseUrl+ userID, {
        method: "GET",
        mode: "cors",
    });
}

export function login(userLogginInfo){
    console.log(userLogginInfo);
    return axios({
        method: 'post',
        url: logUrl,
        data: userLogginInfo
    });
}
export function register(userRegInfo){
    console.log(userRegInfo);
    return axios({
        method: 'post',
        url: regUrl,
        data: userRegInfo
    });
}
