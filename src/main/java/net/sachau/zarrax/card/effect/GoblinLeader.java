package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.card.type.Goblin;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.type.Monster;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.v2.GEngine;
import net.sachau.zarrax.v2.GState;

public class GoblinLeader extends CardEffect {
    public GoblinLeader() {
        super();
    }

    @Override
    public void start(Card targetCard) {
        Logger.debug("trigger Goblin Leader");
        GState state = GEngine.getBean(GState.class);
        Player player = state.getPlayer();
        if (player != null && player.getHazards() != null) {
            for (Card card : player.getHazards()) {
                if (card instanceof Goblin) {
                    int d = card.getDamage() + 1;
                    Logger.debug(this + " increases damage of " + card + " to " + d);
                    card.setDamage(d);
                    card.getEffects()
                            .add(this);
                }
            }

        }

    }

    @Override
    public void end(Card card) {
        if (card != null && card instanceof Monster) {
            card.setDamage(card.getDamage() - 1);
        }
    }
}
