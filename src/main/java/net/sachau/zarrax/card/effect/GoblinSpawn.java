package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.type.Goblin;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.util.DiceUtils;
import net.sachau.zarrax.v2.GEngine;
import net.sachau.zarrax.v2.GState;

import java.util.List;

public class GoblinSpawn extends CardEffect {
    public GoblinSpawn() {
        super();
    }

    @Override
    public void start(Card targetCard) {
        Logger.debug("trigger Goblin Spawn");
        GState state = GEngine.getBean(GState.class);
        Catalog catalog = GEngine.getBean(Catalog.class);
        Player player = state.getPlayer();
        if (player != null && player.getHazards() != null) {

            List<Card> goblins = catalog.get(Goblin.class);
            Card card = DiceUtils.getRandomCard(goblins);
            if (card != null) {
                card.setOwner(null);
                card.setVisible(true);
                player.getSpawnCards().add(card);
            }
        }
    }

    @Override
    public void end(Card card) {
    }
}
