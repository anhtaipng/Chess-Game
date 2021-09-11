package com.example.ChessDatabaseService.Config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig
{

    @Value("${mongodb.url}")
    private String connectionURL;

    @Value("${mongodb.name}")
    private String dbName;

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(connectionURL + "/" + dbName);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), dbName);
    }
}
