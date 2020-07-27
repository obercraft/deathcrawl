package net.sachau.deathcrawl.cards.types;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.dto.Creature;
import net.sachau.deathcrawl.keywords.Keyword;
import net.sachau.deathcrawl.momentum.MomentumActions;
import org.apache.commons.lang3.StringUtils;

public class Character extends Card {

    private Deck startingCards;
    private MomentumActions momentumActions = new MomentumActions();

    public Character() {
        super();
        addKeywords(Keyword.CREATURE);
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
