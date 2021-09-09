import React from 'react';
import {toast, ToastContainer} from "react-toastify";

const handler = () => {
    toast.warn('🦄 Wow so easy!', {
        position: toast.POSITION.TOP_CENTER,
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
    });
}
const TestComponent = () => {
    return (
        <div>
            <button onClick={handler}> Display Notification </button>

        </div>
    );
};

export default TestComponent;
