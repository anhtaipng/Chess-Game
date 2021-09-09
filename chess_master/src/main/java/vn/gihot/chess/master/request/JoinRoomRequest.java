package vn.gihot.chess.master.request;

public class JoinRoomRequest {
    String room_id;
    String player;


    public JoinRoomRequest() {
    }

    public JoinRoomRequest(String room_id, String player) {
        this.room_id = room_id;
        this.player = player;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
