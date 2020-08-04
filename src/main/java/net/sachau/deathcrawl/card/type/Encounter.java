package net.sachau.deathcrawl.card.type;

import net.sachau.deathcrawl.card.Card;

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
