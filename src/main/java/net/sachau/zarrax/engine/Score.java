package net.sachau.zarrax.engine;

import net.sachau.zarrax.Configuration;

public class Score {
    
    public static int calculateScore(Player player) {
        int score = 0;
        VictoryCondition ratio = new VictoryCondition();
        ratio.setGold(Configuration.getInstance().getInt("zarrax.score.gold-ratio"));
        ratio.setExperience(Configuration.getInstance().getInt("zarrax.score.experience-ratio"));
        ratio.setExploration(Configuration.getInstance().getInt("zarrax.score.exploration-ratio"));
        ratio.setTreasure(Configuration.getInstance().getInt("zarrax.score.treasure-ratio"));

        VictoryCondition neededScore = player.getVictoryCondition();

        VictoryCondition actualScore = player.getActualScore();
        
        score += categoryScore(actualScore.getGold(), neededScore.getGold(), ratio.getGold());
        score += categoryScore(actualScore.getExperience(), neededScore.getExperience(), ratio.getExperience());
        score += categoryScore(actualScore.getTreasure(), neededScore.getTreasure(), ratio.getTreasure());
        score += categoryScore(actualScore.getExploration(), neededScore.getExploration(), ratio.getExploration());

        return score;

    }

    private static int categoryScore(int actualScore, int neededScore, int ratio) {

        return (actualScore - neededScore * ratio) * neededScore;
    }

}
