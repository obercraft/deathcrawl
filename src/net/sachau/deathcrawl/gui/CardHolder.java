package net.sachau.deathcrawl.gui;

import javafx.geometry.*;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import net.sachau.deathcrawl.GameState;
import net.sachau.deathcrawl.GuiState;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;

public class CardHolder extends ScrollPane {

    HBox container = new HBox();
    Deck deck;

    public CardHolder(int length) {
    	super();

        setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setVbarPolicy(ScrollBarPolicy.NEVER);
        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setHeight(CardTile.HEIGHT +20);
        setMaxHeight(CardTile.HEIGHT +20);
        setMaxWidth(CardTile.WIDTH * length);
        setFitToHeight(true);
        setFitToWidth(true);
        container.setAlignment(Pos.TOP_LEFT);
        setContent(container);

    }

    public void add(Card card) {
        CardTile cardTile = GuiState.getInstance()
                .getCardTile(card);
        container.getChildren().add(cardTile);
        setContent(container);
    }

    public void remove(Card source) {
        CardTile targetCardTile = null;
        for (Node node : container.getChildren()) {
            if (node instanceof CardTile) {
                CardTile ct = (CardTile) node;
                if (ct.getCard().getId() == source.getId()) {
                    targetCardTile = ct;
                }
            }
            if (targetCardTile != null) {
                container.getChildren().remove(targetCardTile);
            }
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
        if (this.deck != null && deck.size() > 0) {

            for(Card card : deck.getAll()) {
                container.getChildren()
                        .add(GuiState.getInstance().getCardTile(card));
            }
        }

    }
}
