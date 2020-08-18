package net.sachau.zarrax.card.type;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.keyword.Keyword;

import java.util.ArrayList;
import java.util.List;

public class Character extends Card {

    private List<Card> startingCards = new ArrayList<>();
    private SimpleListProperty<Card> levelCards;
    private SimpleIntegerProperty selectedLevelCard = new SimpleIntegerProperty();

    private SimpleIntegerProperty level = new SimpleIntegerProperty(1);
    private SimpleIntegerProperty availableBuyPoints = new SimpleIntegerProperty(0);
    private int hitsPerLevel;

    public Character() {
        super();
        addKeywords(Keyword.CREATURE);
        levelCards = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    }

    public Character(Character character) {
        super(character);
        addKeywords(Keyword.CREATURE);
        if (character.startingCards != null) {
            startingCards.addAll(character.getStartingCards());
        }
        levelCards = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
        for (Card c : character.getLevelCards()) {
            levelCards.add(c);
        }
    }

    public List<Card> getStartingCards() {
        return startingCards;
    }

    public void setStartingCards(List<Card> startingCards) {
        this.startingCards = startingCards;
    }

    public ObservableList<Card> getLevelCards() {
        return levelCards.get();
    }

    public SimpleListProperty<Card> levelCardsProperty() {
        return levelCards;
    }

    public void setLevelCards(ObservableList<Card> levelCards) {
        this.levelCards.set(levelCards);
    }

    public void addLevelCard(Card card) {
        card.addKeywords(Keyword.PERMANENT);
        levelCardsProperty().add(card);
    }

    public int getSelectedLevelCard() {
        return selectedLevelCard.get();
    }

    public SimpleIntegerProperty selectedLevelCardProperty() {
        return selectedLevelCard;
    }

    public void setSelectedLevelCard(int selectedLevelCard) {
        this.selectedLevelCard.set(selectedLevelCard);
    }

    public Card getSelectedCard() {
        return getLevelCards().get(getSelectedLevelCard());
    }

    public int getLevel() {
        return level.get();
    }

    public SimpleIntegerProperty levelProperty() {
        return level;
    }

    public void setLevel(int level) {
        this.level.set(level);
    }

    public int getAvailableBuyPoints() {
        return availableBuyPoints.get();
    }

    public SimpleIntegerProperty availableBuyPointsProperty() {
        return availableBuyPoints;
    }

    public void setAvailableBuyPoints(int availableBuyPoints) {
        this.availableBuyPoints.set(availableBuyPoints);
    }

    public int getHitsPerLevel() {
        return hitsPerLevel;
    }

    public void setHitsPerLevel(int hitsPerLevel) {
        this.hitsPerLevel = hitsPerLevel;
    }
}
