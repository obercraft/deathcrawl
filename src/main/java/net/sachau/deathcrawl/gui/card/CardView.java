package net.sachau.deathcrawl.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.GameEvent;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.CardCache;
import net.sachau.deathcrawl.cards.CharacterCard;
import net.sachau.deathcrawl.keywords.Keyword;

import java.util.Observer;

public abstract class CardView extends StackPane implements Observer {

  public static final double HEIGHT = 88*3;
  public static final double WIDTH = 63*3;
  private final Card card;

  public CardView(Card card) {
    super();
    GameEvent.events().addObserver(this);
    this.getStyleClass().add("card");
    this.getStyleClass().add("card-background");
    this.card = card;

    CardCover cardCover = new CardCover();

    VBox textband = new VBox();
    textband.getStyleClass().add("card-nameband");
    Text name = new Text(card.getName());
    name.getStyleClass().add("card-nameband-text");
    Text keywords = new Text(card.getCardKeyWords());
    keywords.getStyleClass().add("card-nameband-text");
    textband.getChildren().addAll(name, keywords);

    ConditionBox conditionBox = new ConditionBox(card);

    this.getChildren()
      .addAll(textband, conditionBox);

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
          if (card instanceof CharacterCard) {
            GameEvent.events().send(GameEvent.Type.CHARACTERDEATH);
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

