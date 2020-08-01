package net.sachau.deathcrawl.gui;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.catalog.Catalog;
import net.sachau.deathcrawl.gui.card.CardInfoView;
import net.sachau.deathcrawl.gui.card.CardTile;
import net.sachau.deathcrawl.gui.card.CardView;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class CardInfoWindow extends Stage {

    private Map<Long, CardInfoView> cache = new HashMap<>();

    private SimpleLongProperty cardId = new SimpleLongProperty(-1);

    private HBox secondaryLayout;
    public CardInfoWindow() {
        super();
        secondaryLayout = new HBox();
        Scene secondScene = new Scene(secondaryLayout, 1000, 800);
        secondScene.getStylesheets().add(this.getClass().getResource("/application.css").toExternalForm());
        this.setScene(secondScene);


        cardId.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue == null) {
                    return;
                }
                Long id = newValue.longValue();
                if (id > 0) {
                    CardInfoView cardInfoView = cache.get(id);
                    if (cardInfoView == null) {
                        Card card = Catalog.getById(id);
                        cardInfoView = new CardInfoView(card, "");
                        cache.put(id, cardInfoView);
                    }

                    if (secondaryLayout.getChildren().size()> 0) {
                        secondaryLayout.getChildren().remove(0, secondaryLayout.getChildren().size());
                    }
                    secondaryLayout.getChildren().add(cardInfoView);
                }
            }
        });
    }

    public long getCardId() {
        return cardId.get();
    }

    public SimpleLongProperty cardIdProperty() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId.set(cardId);
    }
}
