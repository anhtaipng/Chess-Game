package com.example.MatchMakingService;


import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class RankingQueue {
    private Queue<Player> classic;
    private Queue<Player> kingOfTheHill;
    private Queue<Player> threeTimeCheck;

    public RankingQueue() {
        classic = new LinkedList<Player>();
        kingOfTheHill = new LinkedList<Player>();
        threeTimeCheck = new LinkedList<Player>();
    }

    public Queue<Player> getQueueByMode(String Mode){
        if(Mode.toLowerCase().equals("classic")){
            return classic;
        }
        else if(Mode.toLowerCase().equals("kingofthehill")){
            return kingOfTheHill;
        }
        else{
            return  threeTimeCheck;
        }

    }
}
