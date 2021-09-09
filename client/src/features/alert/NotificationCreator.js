const NotificationCreator = () => {
    const createNotification = (title,message,type,insert,container) =>{
        return {
            title,
            message,
            type,
            insert,
            container,
            animationIn: ["animate__animated animate__fadeIn"], // `animate.css v4` classes
            animationOut: ["animate__animated animate__fadeOut"], // `animate.css v4` classes
            dismiss: {
                duration: 2000
            },
            slidingExit: {
                duration: 800,
                timingFunction: 'ease-out',
                delay: 0
            }
        }
    }
    return {createNotification};
}


export default NotificationCreator;