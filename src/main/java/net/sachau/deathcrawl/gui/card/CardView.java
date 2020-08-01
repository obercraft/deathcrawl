package net.sachau.deathcrawl.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.StackPane;
import net.sachau.deathcrawl.events.GameEvent;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

import java.util.Observer;

public abstract class CardView extends StackPane implements Observer {

    // see also top of application.css
    public static final double HEIGHT = 88 * 3;
    public static final double HEIGHT_SMALL = 88 * 3 - 100;

    public static final double WIDTH = 63 * 3;
    private Card card;

    public CardView(Card card, String cssClass) {
        super();
        this.card = card;

        GameEvent.getInstance()
                .addObserver(this);
        this.getStyleClass()
                .add(cssClass);
        this.getStyleClass()
                .add("card-background");


        CardDesignPane cardDesignPane = new CardDesignPane(card, cssClass);


        this.getChildren()
                .addAll(cardDesignPane, new CenterConditionBox(card, cssClass));

        if (card.getKeywords()
                .contains(Keyword.CREATURE)) {
            CornerValueBox leftBox = new CornerValueBox(card.damageProperty(), null, "bottom-left", cssClass);
            CornerValueBox rightBox = new CornerValueBox(card.hitsProperty(), card.maxHitsProperty(), "bottom-right", cssClass);
            this.getChildren()
                    .addAll(leftBox, rightBox);
        }

    }

}

