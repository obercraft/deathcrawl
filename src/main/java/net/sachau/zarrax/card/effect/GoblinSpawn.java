package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.type.Goblin;
import net.sachau.zarrax.card.type.Monster;
import net.sachau.zarrax.engine.ApplicationContext;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.util.DiceUtils;

import java.util.List;

public class GoblinSpawn extends CardEffect {
    public GoblinSpawn() {
        super();
    }

    @Override
    public void start(Card targetCard) {
        Logger.debug("trigger Goblin Spawn");
        Player player = GameEngine.getInstance().getPlayer();
        if (player != null && player.getHazards() != null) {

            List<Card> goblins = ApplicationContext.getCatalog().get(Goblin.class);
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
