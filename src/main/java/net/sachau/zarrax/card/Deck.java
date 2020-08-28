package net.sachau.zarrax.card;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.engine.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Deck extends LinkedList<Card> {

    private List<Card> discards;

    public Deck() {
        super();
        discards = new LinkedList<>();
    }

    public void discard(Card card) {
        discards.add(card);
    }

    public void shuffle() {
        for (Card card : this) {
            card.setVisible(false);
        }
        Collections.shuffle(this);
    }

    public Card draw() {
        if (this.size() > 0) {
            return this.remove();
        } else if (this.discards.size() == 0){
            return null;
        } else {
            this.addAll(discards);
            this.discards.clear();
            shuffle();
            return this.draw();
        }

    }

    public List<Card> getDiscards() {
        return discards;
    }
}
