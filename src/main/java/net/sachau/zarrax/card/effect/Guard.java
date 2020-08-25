package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.card.type.Goblin;
import net.sachau.zarrax.card.type.Monster;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.Player;

import java.util.List;

public class Guard extends CardEffect {
    public Guard() {
        super();
    }

    @Override
    public void trigger(Card targetCard) {
        Logger.debug(targetCard + "triggers Guard");
        Player player = GameEngine.getInstance()
                .getPlayer();
        List<Card> cards = targetCard.getOwner() instanceof Player ? player.getParty() : player.getHazards();

        for (Card card : cards) {
            if (card.getId() != targetCard.getId()) {
                card.getEffects()
                        .add(new KeywordEffect(Keyword.GUARDED, targetCard, this));
            }
        }

    }

    @Override
    public void remove(Card targetCard) {
        Player player = GameEngine.getInstance()
                .getPlayer();
        List<Card> cards = targetCard.getOwner() instanceof Player ? player.getParty() : player.getHazards();
        for (Card card : cards) {
            if (card.getId() != targetCard.getId()) {
             card.removeEffectWithSourceId(this.getId());
            }
        }

    }

}
