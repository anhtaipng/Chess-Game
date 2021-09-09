import {MessageConstant} from "./messageRelayer";

export default class MessageObserver{
    constructor() {
        this.messageCallBackMap = new Map()
    }

    registerMessAndCallBack(mess, callback) {
        this.messageCallBackMap.set(mess, callback);
    }
    registerMoveCallBack(callback){
        this.moveCallBack = callback;
    }
    reactTo(mess){
        console.log("Observer receiving Mess:",mess);
        if (this.messageCallBackMap.has(mess)) {
            this.messageCallBackMap.get(mess)();
        }
        else{
            const parts = mess.split();
            if (parts[1] === MessageConstant.MOVE_CODE) {
                console.log("Observer is executing move callback");
                this.moveCallBack(JSON.parse(parts[3]));
            }
        }
    }
}