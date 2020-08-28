package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.engine.Player;

public class EnvironmentDamage extends CardEffect {

    public EnvironmentDamage() {

    }

    @Override
    public void start(Card targetCard) {
        Player player = GameEngine.getInstance()
                .getPlayer();
        for (Card card : player.getParty()) {
            int hits = card.getHits() - Math.max(1, targetCard.getDamage());
            card.setHits(hits);
        }
    }

    @Override
    public void end(Card card) {

    }

}
