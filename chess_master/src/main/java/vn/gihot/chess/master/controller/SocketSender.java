package vn.gihot.chess.master.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import vn.gihot.chess.master.game.GameMaster;
import vn.gihot.chess.master.model.exception.InvalidUserException;
import vn.gihot.chess.master.model.move.MoveInfo;
//public void sendMess(id room, id P1, id P2, message)}
// String hết, cái message là m tự format theo ý mình nhá, t truyền raw cái string đó luôn

@Controller
public class SocketSender {

    private SimpMessagingTemplate template;

    @Autowired
    GameMaster gameMaster;

    @Autowired
    public SocketSender(SimpMessagingTemplate template) {
        this.template = template;
    }


    //moi user se subcribe (lang nghe message tu server gui len) topic "/topic/only-user/{id_user}" voi id_user tuong ung
    //moi topic chi duoc subcribe boi 1 user tuong ung

    //ham xu ly send message len topic cua moi user
    public void sendOnlyUser(String id_user, String message){
        System.out.println(id_user + "***" + message);
        this.template.convertAndSend("/topic/only-user/" + id_user, message);
    }

    public void sendMess(String room_id, String player1, String player2, String mess){
        for(String item: gameMaster.getPlayerInRooms().get(room_id))
            sendOnlyUser(item, mess);
    }

    //ham xu ly cac message nhan duoc tu user
    @MessageMapping("/user-all")
    public void receiveMessage(@Payload String message) throws InvalidUserException {
        String[] splits = message.split(" ");
        for (String item : splits)
            System.out.println(item);
        String room_id = splits[0];
        if (splits[1].equals("Move")){//xu ly move
//            sendMess(room_id, "", "", message);

            Gson gson = new Gson();
            MoveInfo moveInfo = gson.fromJson(splits[3], MoveInfo.class);
            System.out.println(moveInfo);

            gameMaster.processMove(room_id, splits[2], moveInfo);
        }
        if (splits[1].equals("Message")){//xu ly message
            sendMess(room_id, "", "", message);
        }
        //sendOnlyUser("anhtai", message);
    }


}
