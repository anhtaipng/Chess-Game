package vn.gihot.chess.master.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gihot.chess.master.config.JwtTokenUlti;
import vn.gihot.chess.master.game.GameMaster;
import vn.gihot.chess.master.model.game.piece.Type;
import vn.gihot.chess.master.model.player.Player;
import vn.gihot.chess.master.model.room.Room;
import vn.gihot.chess.master.model.socket.Message;
import vn.gihot.chess.master.request.CreateRoomRequest;
import vn.gihot.chess.master.request.JoinRoomRequest;

import java.util.ArrayList;
import java.util.List;
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


    //tao room
    @CrossOrigin
    @RequestMapping(value = "/games", method = RequestMethod.POST)
    public ResponseEntity<?> newRoom(@RequestHeader("authorization") String token, @RequestBody CreateRoomRequest createRoomRequest) throws Exception {
        if (jwtTokenUlti.validateJwttoken(token)) {
            String id_room = UUID.randomUUID().toString();
            gameMaster.createRoom(id_room,createRoomRequest.getPlayer1(), createRoomRequest.getGame_mode(), createRoomRequest.getTime_mode());
            Gson gson = new Gson();
            String player_string = gson.toJson(new Player(gameMaster.getPlayerInRooms().get(id_room).get(0), "white", Type.WHITE),Player.class);
            String id_room_string = "\",\"id_room\":" + "\"" + id_room + "\"" +"}";
            String respone_string = player_string.substring(0,player_string.length() -2) + id_room_string;
            System.out.println("GameMasterReturn From Room creat request:" + respone_string);
            return ResponseEntity.ok(gson.fromJson(respone_string,Object.class));
        }
        return ResponseEntity.status(401).body("Authorization failed!");
    }


    //can xem lai B join room
    //JOIN ROOM
    @CrossOrigin
    @RequestMapping(value = "/games/join", method = RequestMethod.POST)
    public ResponseEntity<?> joinRooom(@RequestHeader("authorization") String token, @RequestBody JoinRoomRequest joinRoomRequest) throws Exception {
        if (jwtTokenUlti.validateJwttoken(token)) {
            System.out.println("join rooom" + joinRoomRequest.getPlayer());
            socketSender.sendMess(joinRoomRequest.getRoom_id(), " ", " ", gameMaster.joinRoom(joinRoomRequest.getRoom_id(), joinRoomRequest.getPlayer()));

            return ResponseEntity.ok(gameMaster.getGames().get(joinRoomRequest.getRoom_id()).getModel());
        }
        return ResponseEntity.status(401).body("Authorization failed!");
    }

    //xem danh sach room hien co
    @CrossOrigin
    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public ResponseEntity<?> listRoom() throws Exception {
        List<Room> listRooms;
        listRooms = new ArrayList<>();
        gameMaster.getRooms().forEach((key, value) -> {listRooms.add(value);});
        return ResponseEntity.ok(listRooms);
    }

}
