package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.types.Monster;
import net.sachau.deathcrawl.keywords.Keyword;

public class GoblinLeader extends CardEffect {
    public GoblinLeader() {
        super();
    }

    @Override
    public void trigger(Card sCard, Card targetCard) {
        Logger.debug("trigger Goblin Leader");
        if (targetCard != null && targetCard.getDeck() != null) {
            for (Card card : targetCard.getDeck().getCards()) {
                    if (card instanceof Monster && card.getKeywords().contains(Keyword.GOBLIN)) {
                        int d = card.getDamage() + 1;
                        card.setDamage(d);
                        card.getConditions().add(new GoblinLeader());
                    }
            }

        }

    }

    @Override
    public void remove(Card targetCard) {
        if (targetCard == null) {
            return;
        }
        for (Card card : targetCard.getDeck().getCards()) {
            CardEffect removeEffect = null;
            for (CardEffect ce : card.getConditions()) {
                if (ce instanceof GoblinLeader) {
                    removeEffect = ce;
                }
            }
            if (removeEffect != null) {
                card.setDamage(card.getDamage() - 1);
                card.getConditions()
                        .remove(removeEffect);
            }
        }
    }
}
