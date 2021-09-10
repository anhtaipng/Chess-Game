import {toast} from "react-toastify";

const NotificationCreator = (() => {
    const toastSuccessful = (message) =>{
        toast.success(`${message}`, {
            position: "top-right",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
        });
    }
    const toastWarning = (message) =>{
        toast.warn(`🦄 ${message}!`, {
            position: "top-right",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
        });
    }
    const toastError = (message) =>{
        toast.error(`😡 ${message}!`, {
            position: "top-right",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
        });
    }
    const toastMoveErrorWarning = (message)=>{
        toast.warn(`💩 ${message}!`, {
            position: "top-right",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
        });
    }
    return {toastWarning, toastSuccessful,toastError,toastMoveErrorWarning};
})();


export default NotificationCreator;