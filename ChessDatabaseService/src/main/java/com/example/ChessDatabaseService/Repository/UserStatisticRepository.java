package com.example.ChessDatabaseService.Repository;

import com.example.ChessDatabaseService.Model.UserStatistic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserStatisticRepository extends MongoRepository<UserStatistic,String> {
}
