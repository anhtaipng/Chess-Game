package com.example.ChessDatabaseService.Controller;

import com.example.ChessDatabaseService.Model.GameOfChess;
import com.example.ChessDatabaseService.Model.UserStatistic;
import com.example.ChessDatabaseService.Service.ChessDBService;
import com.example.ChessDatabaseService.Service.UserStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ChessDBController {
    @Autowired
    private ChessDBService chessDBService;

    @Autowired
    private UserStatisticService userStatisticService;

    @GetMapping(path = "history")
    public List<GameOfChess> getBoardGameByUserName(@RequestParam String username,@RequestParam int number){
        return chessDBService.getByUserName(username,number);
    }

    @GetMapping(path = "black-win-rate")
    public double getBlackWinRateSystem(){
        return chessDBService.getBlackWinRate();
    }

    @GetMapping(path="Statistic/User")
    public UserStatistic getStatisticByUserName(@RequestParam String username){
        return userStatisticService.getUserStatisticByUserName(username);
    }

    @PostMapping(path = "history/StoreGame")
    public void AddNewGame(@RequestBody GameOfChess gameOfChess){
        chessDBService.AddNewGame(gameOfChess);
        userStatisticService.InserOrUpdateUserStatic(gameOfChess);
    }


}
