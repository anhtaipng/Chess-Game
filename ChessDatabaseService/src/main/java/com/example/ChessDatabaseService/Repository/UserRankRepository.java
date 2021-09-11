package com.example.ChessDatabaseService.Repository;

import com.example.ChessDatabaseService.Model.UserRank;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRankRepository extends MongoRepository<UserRank,String> {
}
