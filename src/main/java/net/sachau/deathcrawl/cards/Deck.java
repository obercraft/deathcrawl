package net.sachau.deathcrawl.cards;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sachau.deathcrawl.keywords.Keyword;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {

    private ListProperty<Card> cards;
    private SimpleBooleanProperty visible = new SimpleBooleanProperty();

    public Deck() {
        super();
        ObservableList<Card> observableList = FXCollections.observableArrayList(new ArrayList<>());
        this.cards = new SimpleListProperty<>(observableList);
    }

    public static Deck builder() {
        return Deck.builder(false);
    }

    public static Deck builder(boolean visible) {
        Deck d = new Deck();
        d.setVisible(visible);
        return d;
    }

    public Deck add(Card card) {
        if (isVisible()) {
            card.setVisible(true);
        }
        card.setDeck(this);
        cards.add(card);
        return this;
    }

    public Deck add(Card ... cards) {
        for (Card c  : cards) {
            if (isVisible()) {
                c.setVisible(true);
            }
            c.setDeck(this);
            this.add(c);
        }
        return this;
    }

    public Deck add(Class<? extends  Card> ... clazzes) {
        for (Class<? extends  Card> clazz  : clazzes) {
            Card c = null;
            try {
                c = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (isVisible()) {
                c.setVisible(true);
            }
            c.setDeck(this);
            this.add(c);
        }
        return this;
    }


    public void shuffe() {
        if (cards.size() == 0) {
            return;
        }
        Collections.shuffle(cards);
    }

    public Card draw(Deck targetDeck) {
        return draw(0, targetDeck);
    }

    public void draw(Deck targetDeck, int amount) {
        if (amount <= 0) {
            amount = 0;
        }
        int limit = Math.min(this.size(), amount);
        for (int i = 0; i < limit; i++) {
            draw(0, targetDeck);
        }
    }

    public Card discard(Card card, Deck targetDeck) {
        Card foundCard = null;
        for (Card c : cards) {
            if (c.getId() == card.getId()) {
                foundCard = c;
                break;
            }
        }
        if (foundCard != null) {
            remove(foundCard);
            targetDeck.add(foundCard);
        }
        return card;
    }


    public Card drawRandom(Deck targetDeck) {
        int randomNum = ThreadLocalRandom.current()
                .nextInt(0, cards.size());
        return draw(randomNum, targetDeck);
    }

    private Card draw(int topIndex, Deck targetDeck) {
        Card drawedCard = cards.size() > topIndex ? cards.get(topIndex) : null;
        if (drawedCard != null) {
            drawedCard.triggerPhaseEffects(CardEffect.Phase.DRAW);

        }
        targetDeck.add(drawedCard);
        remove(drawedCard);
        return drawedCard;
    }

    private Card discard(int topIndex, Deck targetDeck) {
        Card discardCard = cards.size() > topIndex ? cards.get(topIndex) : null;
        if (discardCard != null) {
            discardCard.triggerPhaseEffects(CardEffect.Phase.DISCARD);

        }
        targetDeck.add(discardCard);
        remove(discardCard);
        return discardCard;
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


    public void moveAll(Deck drawPile) {
        for (Card card : getAll()) {
            card.setVisible(drawPile.isVisible());
            drawPile.add(card);
        }
        cards.removeAll(cards);
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


}
