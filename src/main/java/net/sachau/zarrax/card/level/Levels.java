package net.sachau.zarrax.card.level;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.engine.Player;

import java.util.LinkedList;
import java.util.List;

public class Levels {

    private List<Level> levelAdvancements;

    private static Levels levels;

    private Levels() {
        levelAdvancements = new LinkedList<>();

        levelAdvancements.add(new Level(0)); // Level 0
        levelAdvancements.add(new Level(0)); // Level 1 <-- characters start
        levelAdvancements.add(new Level(4, Level.Advancement.INCREASE_HITS));
        levelAdvancements.add(new Level(8, Level.Advancement.INCREASE_BUY_POINTS));
        levelAdvancements.add(new Level(12, Level.Advancement.INCREASE_HITS, Level.Advancement.INCREASE_BUY_POINTS));
        levelAdvancements.add(new Level(20, Level.Advancement.INCREASE_HITS));
        levelAdvancements.add(new Level(30, Level.Advancement.INCREASE_BUY_POINTS));
        levelAdvancements.add(new Level(40, Level.Advancement.INCREASE_BUY_POINTS, Level.Advancement.INCREASE_HITS));

    }

    public static Levels getInstance() {
        if (levels == null) {
            levels = new Levels();
        }
        return levels;

    }

    public boolean gainLevel(Player player) {
        boolean hasLevelGain = false;
        for (Card card : player.getParty()) {
            if (card instanceof Character) {
                Character character = (Character) card;
                Level nextAdvancement = getInstance().getLevelAdvancements()
                        .get(character.getLevel() + 1);
                if (player.getXp() >= nextAdvancement.getExperienceNeeded()) {
                    hasLevelGain = true;
                    character.setLevel(character.getLevel() + 1);
                    if (nextAdvancement.getAdvancements() != null) {
                        for (Level.Advancement advancement : nextAdvancement.getAdvancements()) {
                            switch (advancement) {
                                case INCREASE_HITS:
                                    int newHits = character.getMaxHits() + character.getHitsPerLevel();
                                    character.setHits(newHits);
                                    character.setMaxHits(newHits);
                                    break;
                                case INCREASE_BUY_POINTS:
                                    character.setAvailableBuyPoints(character.getAvailableBuyPoints() + 1);
                                    break;
                            }
                        }
                    }
                }
            }
        }
        return hasLevelGain;
    }

    public List<Level> getLevelAdvancements() {
        return levelAdvancements;
    }
}
