package vn.gihot.chess.master.request;

public class JoinRoomRequest {
    String player;
    String id_room;

    public JoinRoomRequest() {
    }

    public JoinRoomRequest(String player, String id_room) {
        this.player = player;
        this.id_room = id_room;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getId_room() {
        return id_room;
    }

    public void setId_room(String id_room) {
        this.id_room = id_room;
    }
}
