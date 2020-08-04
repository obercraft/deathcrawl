package net.sachau.deathcrawl.card.type;

import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.keyword.Keyword;
import net.sachau.deathcrawl.card.momentum.MomentumActions;

import java.util.ArrayList;
import java.util.List;

public class StartingCharacter extends Card {

    private List<Card> startingCards = new ArrayList<>();
    private MomentumActions momentumActions = new MomentumActions();

    public StartingCharacter() {
        super();
        addKeywords(Keyword.CREATURE);
    }

    public StartingCharacter(StartingCharacter startingCharacter) {
        super(startingCharacter);
        addKeywords(Keyword.CREATURE);
        if (startingCharacter.startingCards != null) {
            startingCards.addAll(startingCharacter.getStartingCards());
        }
    }

    public List<Card> getStartingCards() {
        return startingCards;
    }

    public void setStartingCards(List<Card> startingCards) {
        this.startingCards = startingCards;
    }

    public MomentumActions getMomentumActions() {
        return momentumActions;
    }

    public void setMomentumActions(MomentumActions momentumActions) {
        this.momentumActions = momentumActions;
    }
}
