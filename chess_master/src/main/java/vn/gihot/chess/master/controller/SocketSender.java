package vn.gihot.chess.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import vn.gihot.chess.master.model.socket.Message;
//public void sendMess(id room, id P1, id P2, message)}
// String hết, cái message là m tự format theo ý mình nhá, t truyền raw cái string đó luôn

@Controller
public class SocketSender {

    private SimpMessagingTemplate template;

    @Autowired
    public SocketSender(SimpMessagingTemplate template) {
        this.template = template;
    }


    //moi user se subcribe (lang nghe message tu server gui len) topic "/topic/only-user/{id_user}" voi id_user tuong ung
    //moi topic chi duoc subcribe boi 1 user tuong ung

    //ham xu ly send message len topic cua moi user
    public void sendOnlyUser(String id_user, String message){
        this.template.convertAndSend("/topic/only-user/" + id_user, message);
    }

    public void sendMess(String room, String player1, String player2, String mess){
        sendOnlyUser(player1,mess);
        sendOnlyUser(player2,mess);
    }

    //ham xu ly cac message nhan duoc tu user
    @MessageMapping("/user-all")
    public void receiveMessage(@Payload String message){
        System.out.println(message);
        sendOnlyUser("anhtai", message);
    }


}
