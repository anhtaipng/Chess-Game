package vn.gihot.chess.master.game.core;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import vn.gihot.chess.master.controller.SocketSender;
import vn.gihot.chess.master.model.game.board.GameEndType;
import vn.gihot.chess.master.model.move.MoveInfo;

// Implementing the logic to displayed the game info and the move result to the client
// Update the move result to firebase
// Udpate the meta data about the game to the client
public class ChessGameView {
    public static String GAME_ERROR = "ERROR the server detected abnormal behaviors. The game is terminated";
    private final String player1;
    private final String player2;
    private final String room;
    // Use this class for Serialization + Deserialization
    private final Gson gson;
//    @Autowired
//    SocketSender socketSender;
    private final SocketSender socketSender;
    public ChessGameView(String room,String player1,String player2){
        this.player1 = player1;
        this.player2 = player2;
        this.room = room;
        gson = new Gson();
        this.socketSender = ApplicationContextUtils.getApplicationContext().getBean(SocketSender.class);
    }

    // Send a normal mess
    public void sendTo2Players(String mess) {
        socketSender.sendMess(room, player1, player2, mess);
    }
    // Send Move
    public void sendMove(String playerID,MoveInfo move){
        if (socketSender == null) {
            System.out.println("SOCKET SENDER IS NULL @@");
        }
        String moveToSend = String.format("%s Move %s %s", this.room, playerID, gson.toJson(move));
        System.out.println("Server Send Move Message:" + moveToSend);
        sendTo2Players(moveToSend);
    }
    // Send Noti
    public void notifyGameEnd(GameEndType gameEndType){
        sendTo2Players("GAME_ENDED " + gameEndType);
    }

    public void signalErrorGame(){
        sendTo2Players(GAME_ERROR);
    }
}
