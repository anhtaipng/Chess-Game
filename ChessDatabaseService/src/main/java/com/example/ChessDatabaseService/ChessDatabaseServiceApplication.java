package com.example.ChessDatabaseService;

import com.example.ChessDatabaseService.Model.UserStatistic;
import com.example.ChessDatabaseService.Repository.GameOfChessRepositoryImpl;
import com.example.ChessDatabaseService.Repository.UserStatisticRepository;
import com.example.ChessDatabaseService.Service.EloRatingSystem;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class ChessDatabaseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChessDatabaseServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner Runner(GameOfChessRepositoryImpl gameRepository,
							 UserStatisticRepository repository,
							 MongoTemplate mongoTemplate
							 ){
		return args -> {

//			gameRepository.insert(new GameOfChess("Thanh","Tai","Black","abc",LocalDateTime.now()));
//			Query query = new Query();
//			query.addCriteria(Criteria.where("result").is("Black"));
//			Long numberOfBlackWin = mongoTemplate.count(query,GameOfChess.class);
//			Long numberOfGame = mongoTemplate.estimatedCount(GameOfChess.class);
//			double rate = numberOfBlackWin.doubleValue()/numberOfGame.doubleValue();
//			System.out.println(rate);

//			userStatisticRepository.save(new UserStatistic("Thanh",500,300,200,200,100));
//			Query query = new Query();
//			query.addCriteria(Criteria.where("username").is("Thanh"));
//			UserStatistic userStatistic = mongoTemplate.findById("Thanh",UserStatistic.class);
//			userStatistic.setTotal(300);
//			userStatistic.setBlacklose(100);
//			mongoTemplate.save(userStatistic);
//			System.out.println(userStatistic.toString());
//			String username = "Thanh";
//			UserStatistic userStatistic1 = mongoTemplate.findById(username,UserStatistic.class);
//			if(userStatistic1 == null){
//				repository.insert(new UserStatistic(
//						username,
//						1, 1, 0, 1, 0
//				));
//			}
//			else{
//				userStatistic1.setTotal(userStatistic1.getTotal()+1);
//				userStatistic1.setWin(userStatistic1.getWin()+1);
//				userStatistic1.setBlackwin(userStatistic1.getBlackwin()+1);
//				mongoTemplate.save(userStatistic1);
//			}

		};
	}
}
