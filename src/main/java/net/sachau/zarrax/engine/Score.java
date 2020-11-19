package net.sachau.zarrax.engine;

import net.sachau.zarrax.Configuration;
import net.sachau.zarrax.gui.screen.Autowired;

@GameData
public class Score {
    
    private final Configuration configuration;

    @Autowired
    public Score(Configuration configuration) {
        this.configuration = configuration;
    }

    public int calculateScore(Player player) {
        int score = 0;
        VictoryCondition ratio = new VictoryCondition();
        ratio.setGold(configuration.getInt("zarrax.score.gold-ratio"));
        ratio.setExperience(configuration.getInt("zarrax.score.experience-ratio"));
        ratio.setExploration(configuration.getInt("zarrax.score.exploration-ratio"));
        ratio.setTreasure(configuration.getInt("zarrax.score.treasure-ratio"));

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
