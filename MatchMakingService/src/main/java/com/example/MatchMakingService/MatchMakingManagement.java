package com.example.MatchMakingService;

import org.springframework.stereotype.Service;

import java.util.Queue;

@Service
public class MatchMakingManagement {
    private RankingQueue novices;
    private RankingQueue classD;
    private RankingQueue classC;
    private RankingQueue classB;
    private RankingQueue classA;
    private RankingQueue CM;
    private RankingQueue FM;
    private RankingQueue IM;
    private RankingQueue GM;
    private RankingQueue WC;

    public MatchMakingManagement() {
        novices = new RankingQueue();
        classD = new RankingQueue();
        classC = new RankingQueue();
        classB = new RankingQueue();
        classA = new RankingQueue();
        CM = new RankingQueue();
        FM = new RankingQueue();
        IM = new RankingQueue();
        GM = new RankingQueue();
        WC = new RankingQueue();
    }

    public void MatchingPlayer(Player player){
        Queue<Player> queue = getSuitableQueue(player);
        if(!queue.isEmpty()){
            Player suitablePlayer = queue.remove();
            sendResultMatchingToRoomService(player,suitablePlayer );
        }
        else {
            queue.add(player);
        }

    }

    private void sendResultMatchingToRoomService(Player player, Player suitablePlayer) {
        System.out.println(player.toString()+"/"+suitablePlayer.toString());
    }


    private Queue<Player> getSuitableQueue(Player player) {
        Queue<Player> queue;
        if (player.elo < 1200){
            queue = novices.getQueueByMode(player.mode);
        }
        else if (player.elo < 1400){
            queue = classD.getQueueByMode(player.mode);
        }
        else if (player.elo < 1600){
            queue = classC.getQueueByMode(player.mode);
        }
        else if(player.elo < 1800){
            queue = classB.getQueueByMode(player.mode);
        }
        else if(player.elo < 2000){
            queue = classA.getQueueByMode(player.mode);
        }
        else if(player.elo < 2300){
            queue = CM.getQueueByMode(player.mode);
        }
        else if(player.elo < 2400){
            queue = FM.getQueueByMode(player.mode);
        }
        else if(player.elo < 2500){
            queue = IM.getQueueByMode(player.mode);
        }
        else if(player.elo <2700){
            queue = GM.getQueueByMode(player.mode);
        }
        else {
            queue = WC.getQueueByMode(player.mode);
        }
        return queue;
    }

}
