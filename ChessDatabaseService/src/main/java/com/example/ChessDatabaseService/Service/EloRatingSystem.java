package com.example.ChessDatabaseService.Service;

import com.example.ChessDatabaseService.Model.UserRank;
import com.example.ChessDatabaseService.Repository.UserRankRepository;
import com.example.ChessDatabaseService.Repository.UserStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class EloRatingSystem {

    private UserRank player1;
    private UserRank player2;


    private UserRankRepository repository;
    private MongoTemplate mongoTemplate;

    @Autowired
    public EloRatingSystem(UserRankRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }


    public void CalculateRating(String player1_name,
                                String player2_name,
                                double result) {
        player1 = getByUserName(player1_name);
        player2 = getByUserName(player2_name);

        if(player1 == null){
            player1 = new UserRank(player1_name,0);
        }
        if(player2 == null){
            player2 = new UserRank(player2_name,0);
        }

        int k = 20;
        if(player1.getElo()>=2400){
            k = 10;
        }



        double ratio_P1 = (double)(player2.getElo()-player1.getElo())/400;
        double ratio_P2 = (double)(player1.getElo()-player2.getElo())/400;

        double expected_core_P1 = 1/(1+Math.pow(10,ratio_P1));
        double expected_core_P2 = 1/(1+Math.pow(10,ratio_P2));

        long new_elo1 = player1.getElo() + Math.round(k*(result-expected_core_P1));
        long new_elo2 = player2.getElo() + Math.round(k*((1-result)-expected_core_P2));

        if(new_elo1<0){
            new_elo1 = 0;
        }

        if(new_elo2<0){
            new_elo2 = 0;
        }
        player1.setElo((int)new_elo1);
        player2.setElo((int)new_elo2);

        UpdateEloUser(player1);
        UpdateEloUser(player2);
    }

    public UserRank getByUserName(String player_name){
        UserRank player = mongoTemplate.findById(player_name,UserRank.class);
        return player;
    }

    public void UpdateEloUser(UserRank userRank){

       repository.save(userRank);
    }

    public UserRank getPlayer1() {
        return player1;
    }

    public void setPlayer1(UserRank player1) {
        this.player1 = player1;
    }

    public UserRank getPlayer2() {
        return player2;
    }

    public void setPlayer2(UserRank player2) {
        this.player2 = player2;
    }

}
