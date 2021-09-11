package com.example.ChessDatabaseService.Service;

import com.example.ChessDatabaseService.Model.UserCoin;
import com.example.ChessDatabaseService.Repository.UserCoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class UserCoinService {
    @Autowired
    UserCoinRepository userCoinRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public void updateCoinByUsername(String username,long CoinBet, boolean result){
        UserCoin userCoin= mongoTemplate.findById(username,UserCoin.class);
        if(result == true)
        {
            userCoin.setCoin(userCoin.getCoin()+CoinBet);
        }
        else{
            userCoin.setCoin(userCoin.getCoin()-CoinBet);
        }
        userCoinRepository.save(userCoin);
    }

    public void insertNewUser(String username){
        userCoinRepository.insert(new UserCoin(username));
    }

    public long getCoinByUsername(String username){
        UserCoin userCoin= mongoTemplate.findById(username,UserCoin.class);
        return userCoin.getCoin();
    }
}
