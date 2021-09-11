package com.example.MatchMakingService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class MatchMakingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchMakingServiceApplication.class, args);
	}
//	@Bean
//	CommandLineRunner Runner(MatchMakingManagement matchMakingManagement){
//		return args -> {
//			matchMakingManagement.MatchingPlayer(new Player("Thanh",1000,"classic"));
//			matchMakingManagement.MatchingPlayer(new Player("Thinh",900,"kingofthehill"));
//			matchMakingManagement.MatchingPlayer(new Player("Tai",1200,"classic"));
//			matchMakingManagement.MatchingPlayer(new Player("Huy",900,"classic"));
//			matchMakingManagement.MatchingPlayer(new Player("Duc",1201,"classic"));
//			matchMakingManagement.MatchingPlayer(new Player("Anh",1000,"kingofthehill"));
//		};
//	}
}
