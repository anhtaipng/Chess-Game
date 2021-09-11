package com.example.MatchMakingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchMakingController {
    MatchMakingManagement matchMakingManagement;

    @Autowired
    public MatchMakingController(MatchMakingManagement matchMakingManagement) {
        this.matchMakingManagement = matchMakingManagement;
    }

    @PostMapping(path = "match")
    public void processFindGame(@RequestBody Player player){
        matchMakingManagement.MatchingPlayer(player);
    }
}
