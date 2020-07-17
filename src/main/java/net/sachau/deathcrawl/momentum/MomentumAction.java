package net.sachau.deathcrawl.momentum;

import net.sachau.deathcrawl.cards.Card;

public class MomentumAction {

    private Card card;
    private int cost;

    public MomentumAction(Card card, int cost) {
        this.card = card;
        this.cost = cost;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
