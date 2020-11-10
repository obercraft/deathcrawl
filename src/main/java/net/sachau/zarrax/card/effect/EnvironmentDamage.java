package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.v2.GEngine;
import net.sachau.zarrax.v2.GState;

public class EnvironmentDamage extends CardEffect {

    public EnvironmentDamage() {

    }

    @Override
    public void start(Card targetCard) {
        GState state = GEngine.getBean(GState.class);
        Player player = state.getPlayer();
        for (Card card : player.getParty()) {
            int hits = card.getHits() - Math.max(1, targetCard.getDamage());
            card.setHits(hits);
        }
    }

    @Override
    public void end(Card card) {

    }

}
