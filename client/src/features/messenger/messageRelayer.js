const messageRelayer = () =>{
    const update = (mess) =>{
        // Currently log to check if work
        console.log("UI will be notified about the mess:", mess);
    };
}
export default messageRelayer;
// Every time receive message M from socket.
//      Import messageRelayer
//      Call messageRelayer.update(M)