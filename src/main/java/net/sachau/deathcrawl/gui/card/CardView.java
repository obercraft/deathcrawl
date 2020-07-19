package net.sachau.deathcrawl.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

public class CardView extends StackPane {

  public static final double HEIGHT = 352; // 88*4
  public static final double WIDTH = 252; // 63*4
  private final Card card;

  public CardView(Card card) {
    super();
    this.getStyleClass().add("card");
    this.card = card;

    CardCover cardCover = new CardCover();


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
          card.getDeck().remove(card);
        }
      }
    });



    Text name = new Text(card.getName() + "@" + card.getId());
    name.setFill(Color.BLACK);


    ConditionBox conditionBox = new ConditionBox(card);

    this.getChildren()
      .addAll(name, conditionBox);

    if (card.getKeywords().contains(Keyword.CREATURE)) {
      HitsBox hitbox = new HitsBox(card);
      this.getChildren().add(hitbox);
    }

    if (card.getMaxDamage() > 0) {
      DamageBox damageBox = new DamageBox(card);
      this.getChildren().add(damageBox);
    }

    if (!card.isVisible()) {
      this.getChildren().add(cardCover);
    }

  }

  public Card getCard() {
    return card;
  }



}

