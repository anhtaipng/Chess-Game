package vn.gihot.chess.master.model.player;

import vn.gihot.chess.master.model.game.piece.Type;

public class Player {
    private final String playerID;
    private final String displayName;
    private final Type playerRoyalty;


    public String getPlayerID() {
        return playerID;
    }


    public String getDisplayName() {
        return displayName;
    }


    public Type getPlayerRoyalty() {
        return playerRoyalty;
    }

    public Player(String playerID, String displayName, Type playerRoyalty) {
        this.playerID = playerID;
        this.displayName = displayName;
        this.playerRoyalty = playerRoyalty;
    }
}
