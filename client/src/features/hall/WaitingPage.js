import React from 'react';
import {useSelector} from "react-redux";

const WaitingPage = () => {
    const roomID = useSelector(state => state.room.room_id);
    console.log()
    return (
        <>
            <div className={`spinner`}>
            </div>
            <strong className={`h3`}>{`Waiting for other player to join + \n`}</strong>
            <strong className={`h3`}>{roomID}</strong>
        </>
    );
};

export default WaitingPage;
