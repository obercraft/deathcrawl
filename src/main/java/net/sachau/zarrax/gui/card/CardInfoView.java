package net.sachau.zarrax.gui.card;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import net.sachau.zarrax.card.Card;

import java.util.Observable;

public class CardInfoView extends CardView {

    private Card card;

    public CardInfoView(Card card, String cssClass, EventHandler<? super MouseEvent> mouseClicked) {
        super(card, cssClass);
        this.card = card;
        if (mouseClicked !=null) {
            this.setOnMouseClicked(mouseClicked);
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public Card getCard() {
        return card;
    }
}
