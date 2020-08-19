package net.sachau.zarrax.card;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sachau.zarrax.card.keyword.Keyword;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {

    private ListProperty<Card> cards;
    private List<Card> discards;
    private SimpleBooleanProperty visible = new SimpleBooleanProperty();



    public Deck() {
        super();
        ObservableList<Card> observableList = FXCollections.observableArrayList(new ArrayList<>());
        this.cards = new SimpleListProperty<>(observableList);
        this.discards = new ArrayList<>();
    }

    public Deck add(Card card) {
        if (isVisible()) {
            card.setVisible(true);
        }
        cards.add(card);
        return this;
    }

//    public Deck add(Card ... cards) {
//        for (Card c  : cards) {
//            if (isVisible()) {
//                c.setVisible(true);
//            }
//            c.setDeck(this);
//            this.add(c);
//        }
//        return this;
//    }
//
//    public Deck add(Class<? extends  Card> ... clazzes) {
//        for (Class<? extends  Card> clazz  : clazzes) {
//            Card c = null;
//            try {
//                c = clazz.newInstance();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            if (isVisible()) {
//                c.setVisible(true);
//            }
//            c.setDeck(this);
//            this.add(c);
//        }
//        return this;
//    }
//

    public void shuffe() {
        if (cards.size() == 0) {
            return;
        }
        Collections.shuffle(cards);
    }


    public void draw(List<Card> targetHand, int amount) {
        if (amount <= 0) {
            amount = 0;
        }
        int limit = Math.min(this.size(), amount);
        for (int i = 0; i < limit; i++) {
            draw(0, targetHand);
        }

        if (amount - limit > 0) {
            List<Card> removedCards = new ArrayList<>();
            for (Card discard : discards) {
                cards.add(discard);
                removedCards.add(discard);
            }
            discards.removeAll(removedCards);
            shuffe();
            draw(Math.min(size() -1, amount - limit), targetHand);
        }

    }

    public Card addToDiscard(Card card) {
        this.discards.add(card);
        return card;
    }


    public Card drawRandom(List<Card> targetDeck) {
        int randomNum = ThreadLocalRandom.current()
                .nextInt(0, cards.size());
        return draw(randomNum, targetDeck);
    }

    private Card draw(int topIndex, List<Card> targetDeck) {
        Card drawedCard = cards.size() > topIndex ? cards.get(topIndex) : null;
        targetDeck.add(drawedCard);
        remove(drawedCard);
        return drawedCard;
    }

    public void remove(Card discardCard) {
        cards.remove(discardCard);
    }


    public int size() {
        return cards.size();
    }

    public List<Card> getAll() {
        return cards;

    }

    public void clean() {
        cards.remove(0, cards.size());

    }

    @Override
    public String toString() {
        return "Deck [cards=" + cards + "]";
    }

    public ObservableList<Card> getCards() {
        return cards.get();
    }

    public ListProperty<Card> cardsProperty() {
        return cards;
    }

    public void setCards(ObservableList<Card> cards) {
        this.cards.set(cards);
    }

    public boolean isVisible() {
        return visible.get();
    }

    public SimpleBooleanProperty visibleProperty() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible.set(visible);
    }

    public void destroy(Card card) {
        cards.remove(card);
    }

    public Set<Keyword> getKeywords() {
        Set<Keyword> keywords = new HashSet<>();
        for (Card c : cards) {
            keywords.addAll(c.getKeywords());
        }
        return keywords;
    }

    public Card getRandomCard() {

        if (cards.size() == 0) {
            return null;
        } else {
            int randomNum = ThreadLocalRandom.current()
                    .nextInt(0, cards.size());
            return cards.get(randomNum);
        }

    }

    public List<Card> getDiscards() {
        return discards;
    }
}
