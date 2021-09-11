package com.example.ChessDatabaseService.Service;

import com.example.ChessDatabaseService.Model.GameOfChess;
import com.example.ChessDatabaseService.Repository.GameOfChessRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ChessDBService {
//    @Autowired
//    private GameOfChessRepositoryImpl repository;
//    @Autowired
//    private MongoTemplate mongoTemplate;

    private GameOfChessRepositoryImpl repository;

    private MongoTemplate mongoTemplate;

    @Autowired
    public ChessDBService(GameOfChessRepositoryImpl repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<GameOfChess> getByUserName(String UserName,int number){
        Query query = new Query();
        query.limit(number);
        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("blackplayerName").is(UserName),Criteria.where("whitePlayerName").is(UserName));
        query.addCriteria(criteria);
        List<GameOfChess> gameOfChessList = mongoTemplate.find(query,GameOfChess.class);
        Collections.reverse(gameOfChessList);
        return gameOfChessList;
    }

    public double getBlackWinRate(){
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("result").is("Black"));
        Long numberOfBlackWin = mongoTemplate.count(query1,GameOfChess.class);

        Long numberOfGame = mongoTemplate.count(new Query(),GameOfChess.class);
//        Long numberOfGame = mongoTemplate.estimatedCount(GameOfChess.class);

        double rate = numberOfBlackWin.doubleValue()/numberOfGame.doubleValue();
        return rate;
    }

    public double getWhiteWinRate(){
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("result").is("White"));
        Long numberOfBlackWin = mongoTemplate.count(query1,GameOfChess.class);


        Long numberOfGame = mongoTemplate.count(new Query(),GameOfChess.class);

        double rate = numberOfBlackWin.doubleValue()/numberOfGame.doubleValue();
        return rate;
    }

    public void AddNewGame(GameOfChess gameOfChess) {
        repository.insert(gameOfChess);
    }
}
