package net.sachau.deathcrawl.card.effect;

import net.sachau.deathcrawl.engine.GameEngine;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.engine.Player;

public class EnvironmentDamage extends CardEffect {

    public EnvironmentDamage() {

    }

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
        Player player = GameEngine.getInstance()
                .getPlayer();
        for (Card card : player.getParty()) {
            int hits = card.getHits() - Math.max(1, targetCard.getDamage());
            card.setHits(hits);
        }
    }

    @Override
    public void remove(Card card) {

    }

}
