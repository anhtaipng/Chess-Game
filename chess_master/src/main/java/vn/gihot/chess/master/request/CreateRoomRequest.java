package vn.gihot.chess.master.request;

public class CreateRoomRequest {
    String player1;
    String game_mode;
    String time_mode;
    public CreateRoomRequest() {}

    public CreateRoomRequest(String player1, String game_mode, String time_mode) {
        this.player1 = player1;
        this.game_mode = game_mode;
        this.time_mode = time_mode;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getGame_mode() {
        return game_mode;
    }

    public void setGame_mode(String game_mode) {
        this.game_mode = game_mode;
    }

    public String getTime_mode() {
        return time_mode;
    }

    public void setTime_mode(String time_mode) {
        this.time_mode = time_mode;
    }
}
