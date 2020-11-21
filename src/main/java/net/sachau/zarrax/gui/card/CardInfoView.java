package net.sachau.zarrax.gui.card;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import net.sachau.zarrax.card.Card;

import java.util.Observable;

public class CardInfoView extends CardView {


    public CardInfoView(Card card, String cssClass, EventHandler<? super MouseEvent> mouseClicked) {
        super(card, cssClass);

        if (mouseClicked !=null) {
            this.setOnMouseClicked(mouseClicked);
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
