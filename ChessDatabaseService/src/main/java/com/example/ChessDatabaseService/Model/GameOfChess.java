package com.example.ChessDatabaseService.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document
public class GameOfChess {
    @Id
    private String _id;
    private String blackplayerName;
    private String whitePlayerName;
    private String result;
    private String move;
    private LocalDateTime time;

    public GameOfChess(String blackplayerName, String whitePlayerName, String result, String move, LocalDateTime time) {
        this.blackplayerName = blackplayerName;
        this.whitePlayerName = whitePlayerName;
        this.result = result;
        this.move = move;
        this.time = time;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBlackplayerName() {
        return blackplayerName;
    }

    public void setBlackplayerName(String blackplayerName) {
        this.blackplayerName = blackplayerName;
    }

    public String getWhitePlayerName() {
        return whitePlayerName;
    }

    public void setWhitePlayerName(String whitePlayerName) {
        this.whitePlayerName = whitePlayerName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "GameOfChess{" +
                "_id='" + _id + '\'' +
                ", blackplayerName='" + blackplayerName + '\'' +
                ", whitePlayerName='" + whitePlayerName + '\'' +
                ", result='" + result + '\'' +
                ", move='" + move + '\'' +
                ", time=" + time +
                '}';
    }
}
