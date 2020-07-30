package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.types.Monster;
import net.sachau.deathcrawl.dto.Creature;
import net.sachau.deathcrawl.keywords.Keyword;

public class GoblinLeader extends CardEffect {
    public GoblinLeader() {
        super();
    }

    @Override
    public void trigger(Card sCard, Card targetCard) {
        Logger.debug("trigger Goblin Leader");
        if (targetCard != null && targetCard.getDeck() != null) {
            for (Card card : targetCard.getDeck()
                    .getCards()) {
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
