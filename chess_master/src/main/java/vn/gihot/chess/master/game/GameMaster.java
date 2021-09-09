package vn.gihot.chess.master.game;

import org.springframework.stereotype.Component;
import vn.gihot.chess.master.model.game.piece.Type;
import vn.gihot.chess.master.model.move.MoveInfo;
import vn.gihot.chess.master.model.player.Player;
import vn.gihot.chess.master.model.room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Bookeep (Create - Terminate) the instances of the game
// Delegate move procession to the controller of the instances
// Implementing the game state saving mechanism
@Component
public class GameMaster {
    HashMap<String, Room> rooms;//luu room da duoc tao
    HashMap<String, List<String>> playerInRooms;//luu danh sach room va client dang o trong room
    HashMap<String,GameInstance> games;
    public GameMaster(){
        games = new HashMap<>();
        playerInRooms = new HashMap<>();
        rooms = new HashMap<>();
    }

    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    public HashMap<String, List<String>> getPlayerInRooms() {
        return playerInRooms;
    }

    public HashMap<String, GameInstance> getGames() {
        return games;
    }

    public void createGame(Room roomInfo, Player player1, Player player2){
        GameInstance game = new GameInstance(roomInfo, player1, player2);
        games.put(roomInfo.getId(), game);
    }
    public void processMove(String roomID, String playerId, MoveInfo moveInfo){
        try {
            games.get(roomID).processMove(playerId, moveInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createRoom(String id_room, String player_id, String game_mode, String time_mode){
        //create room
        Room room = new Room(id_room, game_mode, time_mode);
        rooms.put(id_room, room);
        List<String> listPlayer = new ArrayList<>();
        listPlayer.add(player_id);
        playerInRooms.put(id_room, listPlayer);
    }


    //join room, neu la nguoi thu 2 thi bat dau game luon
    public String joinRoom(String id_room, String player_id){
        playerInRooms.get(id_room).add(player_id);
        if (playerInRooms.get(id_room).size() == 2) {
            createGame(rooms.get(id_room), new Player(playerInRooms.get(id_room).get(0), "white", Type.WHITE),
                    new Player(playerInRooms.get(id_room).get(1), "black", Type.BLACK));
            //System.out.println(id_room + " PlayerJoin " + player_id + " 1200");
            return id_room + " PlayerJoin " + player_id + " 1200";
        }
        //System.out.println(id_room + " SpectatorJoin " + player_id);
        return id_room + " SpectatorJoin " + player_id;
    }



}
