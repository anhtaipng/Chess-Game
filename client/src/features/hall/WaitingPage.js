import React from 'react';

const WaitingPage = () => {
    return (
        <>
            <div className={`spinner`}>
            </div>
            <strong className={`h3`}>Waiting for other player to join</strong>
        </>
    );
};

export default WaitingPage;
