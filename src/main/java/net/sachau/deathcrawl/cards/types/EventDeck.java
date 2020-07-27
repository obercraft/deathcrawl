package net.sachau.deathcrawl.cards.types;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.keywords.Keyword;

public class EventDeck extends Card {

    private Deck deck = new Deck();

    public EventDeck() {
        super();
    }

    @Override
    public Deck getDeck() {
        return deck;
    }

    @Override
    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
