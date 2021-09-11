package com.example.MatchMakingService;

public class Player {
    String username;
    int elo;
    String mode;

    public Player(String username, int elo, String mode) {
        this.username = username;
        this.elo = elo;
        this.mode = mode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", elo=" + elo +
                ", mode='" + mode + '\'' +
                '}';
    }
}
