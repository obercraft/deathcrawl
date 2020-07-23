package net.sachau.deathcrawl.cards;

import net.sachau.deathcrawl.dto.Creature;
import net.sachau.deathcrawl.momentum.MomentumActions;
import org.apache.commons.lang3.StringUtils;

public abstract class CharacterCard extends Card {

    private String uniqueId;
    private Deck startingCards;
    private MomentumActions momentumActions = new MomentumActions();

    public CharacterCard(String name, int initialHits, int initialDamage, String uniqueId, Deck startingCards, MomentumActions momentumActions) {
        super(name, initialHits, initialDamage);;

        this.uniqueId = uniqueId;
        this.momentumActions = momentumActions;
        this.startingCards = startingCards;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Deck getStartingCards() {
        return startingCards;
    }

    public void setStartingCards(Deck startingCards) {
        this.startingCards = startingCards;
    }

    public MomentumActions getMomentumActions() {
        return momentumActions;
    }

    public void setMomentumActions(MomentumActions momentumActions) {
        this.momentumActions = momentumActions;
    }
}
