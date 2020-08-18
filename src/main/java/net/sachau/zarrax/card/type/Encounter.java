package net.sachau.zarrax.card.type;

import net.sachau.zarrax.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Encounter extends Card {

    private String cardString;

    public Encounter() {
        super();
    }

    public Encounter(Encounter card) {
        super(card);
        this.cardString = card.getCardString();
    }

    public String getCardString() {
        return cardString;
    }

    public void setCardString(String cardString) {
        this.cardString = cardString;
    }
}
