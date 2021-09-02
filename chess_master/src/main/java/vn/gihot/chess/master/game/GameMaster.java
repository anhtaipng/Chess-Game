package vn.gihot.chess.master.game;

import vn.gihot.chess.master.model.move.MoveInfo;
import vn.gihot.chess.master.model.player.Player;
import vn.gihot.chess.master.model.room.Room;

import java.util.ArrayList;
import java.util.HashMap;

// Bookeep (Create - Terminate) the instances of the game
// Delegate move procession to the controller of the instances
// Implementing the game state saving mechanism
public class GameMaster {
    HashMap<String,GameInstance> games;
    public GameMaster(){
        games = new HashMap<>();
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
}
