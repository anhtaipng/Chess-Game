// A mock function to mimic making an async request for data


const serverRoot = "http://localhost:9306";
const baseUrl = `${serverRoot}/user/profile/`;
const logUrl = `${serverRoot}/authenticate`;
const regUrl = `${serverRoot}/register/`;

export function fetchElo() {
    return fetch(baseUrl, {
        method: "GET",
        mode: "cors",
    });
}

export function fetchUser(userID) {
    return fetch(baseUrl+ userID, {
        method: "GET",
        mode: "cors",
    });
}

export function login(userLogginInfo){
    console.log(logUrl);
    return fetch(logUrl,{
        method:"POST",
        mode: "cors",
        body: JSON.stringify(userLogginInfo)
    })
}
export function register(userRegInfo){
    return fetch(regUrl,{
        method:"POST",
        mode: "cors",
        body: JSON.stringify(userRegInfo)
    })
}
