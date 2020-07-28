package net.sachau.deathcrawl.cards.types;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.keywords.Keyword;
import net.sachau.deathcrawl.momentum.MomentumActions;

public class StartingCharacter extends Card {

    private Deck startingCards = new Deck();
    private MomentumActions momentumActions = new MomentumActions();

    public StartingCharacter() {
        super();
        addKeywords(Keyword.CREATURE);
    }

    public StartingCharacter(StartingCharacter startingCharacter) {
        super(startingCharacter);
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
