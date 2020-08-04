package net.sachau.deathcrawl.card.effect;

import net.sachau.deathcrawl.engine.GameEngine;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.type.Monster;
import net.sachau.deathcrawl.engine.Player;
import net.sachau.deathcrawl.card.keyword.Keyword;

public class GoblinLeader extends CardEffect {
    public GoblinLeader() {
        super();
    }

    @Override
    public void trigger(Card sCard, Card targetCard) {
        Logger.debug("trigger Goblin Leader");
        Player player = GameEngine.getInstance().getPlayer();
        if (player != null && player.getHazards() != null) {
            for (Card card : player.getHazards()) {
                if (card instanceof Monster && card.getKeywords()
                        .contains(Keyword.GOBLIN)) {
                    int d = card.getDamage() + 1;
                    card.setDamage(d);
                    card.getConditions()
                            .add(this);
                }
            }

        }

    }

    @Override
    public void remove(Card card) {
        if (card != null && card instanceof Monster) {
            card.setDamage(card.getDamage() - 1);
        }
    }
}
