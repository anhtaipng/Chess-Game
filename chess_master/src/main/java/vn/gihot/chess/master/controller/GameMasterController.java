package vn.gihot.chess.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gihot.chess.master.config.JwtTokenUlti;
import vn.gihot.chess.master.game.GameMaster;
import vn.gihot.chess.master.model.socket.Message;
import vn.gihot.chess.master.request.CreateRoomRequest;
import vn.gihot.chess.master.request.JoinRoomRequest;

import java.util.UUID;

// This class should receive and delegate the request from the client
@RestController
public class GameMasterController {
    @Autowired
    SocketSender socketSender;

    @Autowired
    JwtTokenUlti jwtTokenUlti;

    @Autowired
    GameMaster gameMaster;

    @CrossOrigin
    @RequestMapping(value = "/test-socket", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody Message message) throws Exception {
        socketSender.send(message);
        return ResponseEntity.ok(message);
    }


    //tao room
    @CrossOrigin
    @RequestMapping(value = "/games", method = RequestMethod.POST)
    public ResponseEntity<?> newRoom(@RequestHeader("authorization") String token, @RequestBody CreateRoomRequest createRoomRequest) throws Exception {
        if (jwtTokenUlti.validateJwttoken(token)) {
            String id_room = UUID.randomUUID().toString();
            gameMaster.createRoom(id_room,createRoomRequest.getPlayer1(), createRoomRequest.getGame_mode(), createRoomRequest.getTime_mode());
            return ResponseEntity.ok(id_room);
        }
        return ResponseEntity.status(401).body("Authorization failed!");
    }

    //JOIN ROOM
    @CrossOrigin
    @RequestMapping(value = "/games/join", method = RequestMethod.POST)
    public ResponseEntity<?> newRoom(@RequestHeader("authorization") String token, @RequestBody JoinRoomRequest joinRoomRequest) throws Exception {
        if (jwtTokenUlti.validateJwttoken(token)) {
            gameMaster.joinRoom(joinRoomRequest.getId_room(), joinRoomRequest.getPlayer());
            //gui cai gi cho th A cap nhat ne
            //socketSender.send();
            return ResponseEntity.ok(gameMaster.getGames().get(joinRoomRequest.getId_room()).getModel());
        }
        return ResponseEntity.status(401).body("Authorization failed!");
    }

    //xem danh sach room hien co
    @CrossOrigin
    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public ResponseEntity<?> listRoom() throws Exception {
        return ResponseEntity.ok(gameMaster.getPlayerInRooms());
    }

}
