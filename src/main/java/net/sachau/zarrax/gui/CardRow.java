package net.sachau.zarrax.gui;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
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
import net.sachau.zarrax.gui.card.CardTileCache;

public class CardRow extends ScrollPane {

    private HBox container = new HBox();
    private SimpleListProperty<Card> cards;

    public CardRow(SimpleListProperty<Card> cards, int length, String cssClass, , EventHandler<? super MouseEvent> mouseClicked) {
        super();

        this.cards = cards;

        container.setMinHeight(CardTile.HEIGHT);
        setMinHeight((cssClass.contains("small") ? CardTile.HEIGHT_SMALL : CardTile.HEIGHT) +20);

        setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setMinWidth(CardTile.WIDTH * length);
        container.setAlignment(Pos.TOP_LEFT);
        setContent(container);

        for (Card card : cards) {
            try {
                container.getChildren()
                        .add(new CardInfoView(card, "card", null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public ObservableList<Card> getCards() {
        return cards.get();
    }

    public SimpleListProperty<Card> cardsProperty() {
        return cards;
    }
}
