package com.example.ChessDatabaseService.Repository;

import com.example.ChessDatabaseService.Model.UserCoin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserCoinRepository extends MongoRepository<UserCoin,String> {
}
