package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.card.type.Goblin;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.type.Monster;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.card.keyword.Keyword;

public class GoblinLeader extends CardEffect {
    public GoblinLeader() {
        super();
    }

    @Override
    public void trigger(Card targetCard) {
        Logger.debug("trigger Goblin Leader");
        Player player = GameEngine.getInstance().getPlayer();
        if (player != null && player.getHazards() != null) {
            for (Card card : player.getHazards()) {
                if (card instanceof Goblin) {
                    int d = card.getDamage() + 1;
                    card.setDamage(d);
                    card.getEffects()
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
