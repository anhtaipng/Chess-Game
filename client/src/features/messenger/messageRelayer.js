const messageRelayer = () =>{
    const update = (mess) =>{
        // Currently log to check if work
        console.log("UI will be notified about the mess:", mess);
    }
    const send = (mess)=>{
        // Do some thing to send the mess to the server:
        console.log("Sending the mess to server:\n",mess)
    }
}
export default messageRelayer;
// Every time receive message M from socket.
//      Import messageRelayer
//      Call messageRelayer.update(M)