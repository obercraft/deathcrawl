package net.sachau.zarrax.gui;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.gui.card.CardInfoView;
import net.sachau.zarrax.gui.card.CardTile;

public class CardRow extends ScrollPane {

    private HBox container = new HBox();
    private SimpleListProperty<Card> sourceCards;
    private SimpleListProperty<Card> targetCards;

    public CardRow(SimpleListProperty<Card> sourceCards, SimpleListProperty<Card> targetCards, int length, String cssClass) {
        super();

        this.sourceCards = sourceCards;

        container.setMinHeight(CardTile.HEIGHT);
        setMinHeight((cssClass.contains("small") ? CardTile.HEIGHT_SMALL : CardTile.HEIGHT) +20);

        setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setMinWidth(CardTile.WIDTH * length);
        container.setAlignment(Pos.TOP_LEFT);
        setContent(container);

        for (Card card : sourceCards) {
            try {
                CardInfoView sourceView = new CardInfoView(card, "card", null);

                sourceView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (sourceCards.contains(card)) {
                            targetCards.add(card);
                            sourceCards.remove(card);
                        } else {

                        }
                    }
                });

                container.getChildren()
                        .add(sourceView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public ObservableList<Card> getSourceCards() {
        return sourceCards.get();
    }

    public SimpleListProperty<Card> sourceCardsProperty() {
        return sourceCards;
    }
}
