package net.sachau.deathcrawl.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.CardCache;
import net.sachau.deathcrawl.commands.CommandParser;
import net.sachau.deathcrawl.dto.Creature;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.keywords.Keyword;

import java.util.HashMap;
import java.util.Map;

public class CardTile extends StackPane {

    public static final DataFormat cardFormat = new DataFormat("card");
    public static final DataFormat momentumFormat = new DataFormat("momentum");
    public static final double HEIGHT = 200;
    public static final double WIDTH = 150;
    private Card card;

    public CardTile(Card card) {
        super();
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
        setWidth(WIDTH);
        setHeight(HEIGHT);


        card.hitsProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (card.getHits() <= 0) {
                    card.getDeck().remove(card);
                }
            }
        });


        Rectangle rectangle = new Rectangle(WIDTH, HEIGHT);
        rectangle.setFill(Color.ANTIQUEWHITE);
        Text name = new Text(card.getName() + "@" + card.getId());
        name.setFill(Color.BLACK);



        ConditionBox conditionBox = new ConditionBox(card);

        this.getChildren()
                .addAll(rectangle, name, conditionBox);

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

        setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    if (card.getKeywords()
                            .contains(Keyword.ACTION)) {
                        executeCommand(card);
                    }
                }
            }
            event.consume();
        });

        setOnDragDetected(event -> {

            if (!card.isPlayable()) {
                return;
            }
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);

            Map<DataFormat, Object> map = new HashMap<>();
            map.put(cardFormat, card.getId());
            db.setContent(map);
            event.consume();
        });

        setOnDragOver(event -> {
            if (event.getDragboard()
                    .hasContent(cardFormat)) {
                event.acceptTransferModes(TransferMode.ANY);
            }

            if (event.getDragboard()
                    .hasContent(momentumFormat)) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });

        setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();

            if (db.getContent(cardFormat) != null) {

                CardTile sourceTile = CardTileCache.getTile((Long) db.getContent(cardFormat));
                Card sourceCard = sourceTile.getCard();

                executeCommand(sourceCard);
            } else if (db.getContent(momentumFormat) != null) {
                Card sourceCard = CardCache.get((Long) db.getContent(momentumFormat));
                executeCommand(sourceCard);

            }
//
//            boolean commandSuccessful = CommandParser.executeCommand(sourceCard, getCard());
//            if (commandSuccessful) {
//                Creature owner = sourceCard.getOwner();
//                if (owner != null && owner instanceof Player) {
//                    Player player = (Player) owner;
//                    sourceCard.getDeck()
//                            .discard(sourceCard, player.getDiscard());
//                }
//            }

            event.consume();
        });

    }

    public Card getCard() {
        return card;
    }

    private void executeCommand(Card sourceCard) {
        boolean commandSuccessful = CommandParser.executeCommand(sourceCard, getCard());
        if (commandSuccessful) {
            Creature owner = sourceCard.getOwner();
            if (owner != null && owner instanceof Player) {
                Player player = (Player) owner;
                if (sourceCard.getDeck() != null) {
                    sourceCard.getDeck()
                            .discard(sourceCard, player.getDiscard());
                }
            }
        }
    }


}

