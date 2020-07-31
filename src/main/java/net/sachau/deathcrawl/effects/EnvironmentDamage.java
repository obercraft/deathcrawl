package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.GameEngine;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.dto.Player;

public class EnvironmentDamage extends CardEffect {

    public EnvironmentDamage() {

    }

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
        Player player = GameEngine.getInstance()
                .getPlayer();
        for (Card card : player.getParty().getCards()) {
            int hits = card.getHits() - Math.max(1, targetCard.getDamage());
            card.setHits(hits);
        }
    }

    @Override
    public void remove(Card card) {

    }

}
