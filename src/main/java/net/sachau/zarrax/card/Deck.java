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

public class Deck  {

    private List<Card> drawPile;
    private List<Card> discardPile;

    public Deck() {
        super();
        this.drawPile = new ArrayList<>();
        this.discardPile = new ArrayList<>();
    }

    public void discard(Card card) {
        this.discardPile.add(card);
        return;
    }

    public void shuffle() {
        for (Card card : this.drawPile) {
            card.setVisible(false);
        }
        Collections.shuffle(this.drawPile);
    }

    public Card draw() {
        if (this.drawPile.size() > 0) {
            return this.drawPile.remove(0);
        } else if (this.discardPile.size() == 0){
            return null;
        } else {
            this.drawPile.addAll(discardPile);
            this.discardPile.clear();
            shuffle();
            return this.draw();
        }

    }

    public List<Card> getDiscardPile() {
        return this.discardPile;
    }

    public void addToDrawPile(Card card) {
        this.drawPile.add(card);
    }


    public int getSize() {
        return drawPile.size() + discardPile.size();
    }

    public List<Card> getDrawPile() {
        return drawPile;
    }
}
