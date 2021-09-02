package vn.gihot.chess.master.model.room;

import vn.gihot.chess.master.model.player.Player;

public class Room {
    private final String id;
    private final String gameMode;
    private final String timeMode;

    public String getId() {
        return id;
    }


    public String getGameMode() {
        return gameMode;
    }

    public String getTimeMode() {
        return timeMode;
    }



    public Room(String id, String gameMode, String timeMode) {
        this.id = id;
        this.gameMode = gameMode;
        this.timeMode = timeMode;
    }
}
