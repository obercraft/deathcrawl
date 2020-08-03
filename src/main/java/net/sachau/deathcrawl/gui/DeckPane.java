package net.sachau.deathcrawl.gui;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.gui.card.CardTile;
import net.sachau.deathcrawl.gui.card.CardTileCache;

import java.util.List;

public class DeckPane extends ScrollPane {

    private HBox container = new HBox();
    private SimpleListProperty<Card> cards;

    public DeckPane(SimpleListProperty<Card> cards, int length, String cssClass) {
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
                        .add(CardTileCache.getTile(card, cssClass));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.cards.addListener(new ListChangeListener<Card>() {
                    @Override
                    public void onChanged(Change<? extends Card> change) {
                        while (change.next()) {
                            int added = change.getAddedSize();
                            if (added > 0) {
                                for (Card c : change.getAddedSubList()) {
                                    container.getChildren()
                                            .add(CardTileCache.getTile(c, cssClass));
                                }
                            }

                            int removed = change.getRemovedSize();
                            if (removed > 0) {
                                for (Card c : change.getRemoved()) {
                                    container.getChildren()
                                            .remove(CardTileCache.getTile(c, cssClass));

                                }
                            }
                        }
                    }
                });

    }

}
