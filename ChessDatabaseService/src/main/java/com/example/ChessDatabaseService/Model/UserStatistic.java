package com.example.ChessDatabaseService.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserStatistic {
    @Id
    String username;
    long total;
    int win;
    int lose;
    int blackwin;
    int blacklose;
    double ratewin;

    public UserStatistic(String username, long total, int win, int lose, int blackwin, int blacklose) {
        this.username = username;
        this.total = total;
        this.win = win;
        this.lose = lose;
        this.blackwin = blackwin;
        this.blacklose = blacklose;
        this.ratewin = (double)win/(double)total;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getBlackwin() {
        return blackwin;
    }

    public void setBlackwin(int blackwin) {
        this.blackwin = blackwin;
    }

    public int getBlacklose() {
        return blacklose;
    }

    public void setBlacklose(int blacklose) {
        this.blacklose = blacklose;
    }

    public double getRatewin() {
        return ratewin;
    }

    public void setRatewin() {
        this.ratewin = (double)win/(double)total;
    }

    @Override
    public String toString() {
        return "UserStatistic{" +
                "username='" + username + '\'' +
                ", total=" + total +
                ", win=" + win +
                ", lose=" + lose +
                ", blackwin=" + blackwin +
                ", blacklose=" + blacklose +
                ", ratewin=" + ratewin +
                '}';
    }
}
