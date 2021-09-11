package com.example.ChessDatabaseService.Repository;

import com.example.ChessDatabaseService.Model.GameOfChess;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface GameOfChessRepositoryImpl extends MongoRepository<GameOfChess,String> {

}
