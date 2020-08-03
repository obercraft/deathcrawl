package net.sachau.deathcrawl.cards.types;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.keywords.Keyword;

import java.util.ArrayList;
import java.util.List;

public class Encounter extends Card {

    private List<Card> cardList = new ArrayList<>();

    public Encounter() {
        super();
    }

    public Encounter(Encounter card) {
        super(card);
        this.cardList = new ArrayList<>(card.getCardList());
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }
}
