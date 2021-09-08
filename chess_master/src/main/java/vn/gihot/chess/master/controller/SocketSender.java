package vn.gihot.chess.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
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


    public void send(Message message) {
        this.template.convertAndSend("/topic/user", message);
    }

    public void sendMess (String room, String player1, String player2, String mess){

    }

//    @CrossOrigin
//    @RequestMapping(value = "/test", method = RequestMethod.POST)
//    public ResponseEntity<?> save(@RequestBody Message message) throws Exception {
//        this.template.convertAndSend("/topic/user", message);
//        return ResponseEntity.ok(message);
//    }

}
