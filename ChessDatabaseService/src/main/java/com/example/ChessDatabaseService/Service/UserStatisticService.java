package com.example.ChessDatabaseService.Service;

import com.example.ChessDatabaseService.Model.GameOfChess;
import com.example.ChessDatabaseService.Model.UserStatistic;
import com.example.ChessDatabaseService.Repository.UserStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserStatisticService
{

    private UserStatisticRepository repository;


    private MongoTemplate mongoTemplate;


    private EloRatingSystem eloRatingSystem;

    @Autowired
    public UserStatisticService(UserStatisticRepository repository, MongoTemplate mongoTemplate, EloRatingSystem eloRatingSystem) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
        this.eloRatingSystem = eloRatingSystem;
    }

    public UserStatistic getUserStatisticByUserName(String username){
			UserStatistic userStatistic = mongoTemplate.findById(username,UserStatistic.class);
			System.out.print(userStatistic);
			return userStatistic;
    }


    public void InserOrUpdateUserStatic(GameOfChess gameOfChess){
        UserStatistic userStatistic1 = mongoTemplate.findById(gameOfChess.getBlackplayerName(),UserStatistic.class);
        UserStatistic userStatistic2 = mongoTemplate.findById(gameOfChess.getWhitePlayerName(),UserStatistic.class);

        double result = 0.5;
        if(gameOfChess.getResult().toLowerCase().equals("black")){
            result = 1;
        }
        else if (gameOfChess.getResult().toLowerCase().equals("white")){
            result = 0;
        }


        if(gameOfChess.getResult().toLowerCase().equals("black")){
            if(userStatistic1 == null){
                repository.insert(new UserStatistic(
                        gameOfChess.getBlackplayerName(),
                        1, 1, 0, 1, 0
                ));
            }
            else{
                userStatistic1.setTotal(userStatistic1.getTotal()+1);
                userStatistic1.setWin(userStatistic1.getWin()+1);
                userStatistic1.setBlackwin(userStatistic1.getBlackwin()+1);
                userStatistic1.setRatewin();
                mongoTemplate.save(userStatistic1);
            }
            if(userStatistic2 == null){
                repository.insert(new UserStatistic(
                        gameOfChess.getWhitePlayerName(),
                        1, 0, 1, 0, 0
                ));
            }
            else{
                userStatistic2.setTotal(userStatistic2.getTotal()+1);
                userStatistic2.setLose(userStatistic2.getLose()+1);
                userStatistic2.setRatewin();
                mongoTemplate.save(userStatistic2);
            }

        }
        else if (gameOfChess.getResult().toLowerCase().equals("white")){
            if(userStatistic1 == null){
                repository.insert(new UserStatistic(
                        gameOfChess.getBlackplayerName(),
                        1, 0, 1, 0, 1
                ));
            }
            else{
                userStatistic1.setTotal(userStatistic1.getTotal()+1);
                userStatistic1.setLose(userStatistic1.getLose()+1);
                userStatistic1.setBlacklose(userStatistic1.getBlacklose()+1);
                userStatistic1.setRatewin();
                mongoTemplate.save(userStatistic1);
            }
            if(userStatistic2 == null){
                repository.insert(new UserStatistic(
                        gameOfChess.getWhitePlayerName(),
                        1, 1, 0, 0, 0
                ));
            }
            else{
                userStatistic2.setTotal(userStatistic2.getTotal()+1);
                userStatistic2.setWin(userStatistic2.getWin()+1);
                userStatistic2.setRatewin();
                mongoTemplate.save(userStatistic2);
            }
        }
        else{
            if(userStatistic1 == null){
                repository.insert(new UserStatistic(
                        gameOfChess.getBlackplayerName(),
                        1, 0, 0, 0, 0
                ));
            }
            else{
                userStatistic1.setTotal(userStatistic1.getTotal()+1);
                mongoTemplate.save(userStatistic1);
            }
            if(userStatistic2 == null){
                repository.insert(new UserStatistic(
                        gameOfChess.getWhitePlayerName(),
                        1, 0, 0, 0, 0
                ));
            }
            else{
                userStatistic2.setTotal(userStatistic2.getTotal()+1);
                mongoTemplate.save(userStatistic2);
            }
        }


        eloRatingSystem.CalculateRating(gameOfChess.getBlackplayerName(),
                gameOfChess.getWhitePlayerName(),
                result);

    }



}
