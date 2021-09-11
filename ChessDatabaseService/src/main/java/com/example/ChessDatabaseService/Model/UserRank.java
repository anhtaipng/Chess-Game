package com.example.ChessDatabaseService.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserRank {
    @Id
    String userName;
    int elo;

    public UserRank(String userName, int elo) {
        this.userName = userName;
        this.elo = elo;
    }

    @Override
    public String toString() {
        return "UserRank{" +
                "UserName='" + userName + '\'' +
                ", elo=" + elo +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }
}
