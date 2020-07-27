package net.sachau.deathcrawl.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.StackPane;
import net.sachau.deathcrawl.Event;
import net.sachau.deathcrawl.Game;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.types.Character;
import net.sachau.deathcrawl.keywords.Keyword;

import java.util.Observer;

public abstract class CardView extends StackPane implements Observer {

  public static final double HEIGHT = 88*3;
  public static final double WIDTH = 63*3;
  private final Card card;

  public CardView(Card card) {
    super();
    this.card = card;

    Game.events().addObserver(this);
    this.getStyleClass().add("card");
    this.getStyleClass().add("card-background");


    CardCover cardCover = new CardCover();


    CardDesignPane cardDesignPane = new CardDesignPane(card);


    this.getChildren()
      .addAll(cardDesignPane, new CenterConditionBox(card));

    if (card.getKeywords().contains(Keyword.CREATURE)) {
      CornerValueBox cornerValueBox = new CornerValueBox(card.hitsProperty(), card.maxHitsProperty(), "bottom-right");
      this.getChildren().add(cornerValueBox);
    }

    if (card.getMaxDamage() > 0) {
      CornerValueBox cornerValueBox = new CornerValueBox(card.damageProperty(), card.maxDamageProperty(), "bottom-left");
      this.getChildren().add(cornerValueBox);
    }

    if (!card.isVisible()) {
      this.getChildren().add(cardCover);
    }



    card.visibleProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (Boolean.TRUE.equals(newValue)) {
          getChildren().remove(cardCover);
        } else {
          getChildren().add(cardCover);
        }
      }
    });

    card.hitsProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if (card.getHits() <= 0) {
          if (card instanceof Character) {
            Game.events().send(Event.CHARACTERDEATH);
          } else {
            card.getDeck()
                    .remove(card);
          }

        }
      }
    });


  }

  public Card getCard() {
    return card;
  }



}

