package net.sachau.deathcrawl.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.GameState;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.commands.CommandParser;
import net.sachau.deathcrawl.dto.Creature;
import net.sachau.deathcrawl.dto.Player;

import java.util.HashMap;
import java.util.Map;

public class CardTile extends StackPane {

    public static final DataFormat cardFormat = new DataFormat("card");
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
                System.out.println("changed" + newValue);
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
        Text name = new Text(card.getName());
        name.setFill(Color.BLACK);

        HitsBox hits = new HitsBox(card);

        this.getChildren()
                .addAll(rectangle, name, hits);

        if (!card.isVisible()) {
            this.getChildren().add(cardCover);
        }

        setOnMouseClicked(event -> {
        });

        setOnDragDetected(event -> {
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
            event.consume();
        });

        setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();

            CardTile sourceTile = CardTileCache.getTile((Long) db.getContent(cardFormat));
            Card sourceCard = sourceTile.getCard();

            CommandParser.executeCommand(sourceCard, getCard());

            Creature owner = sourceCard.getOwner();
            if (owner != null && owner instanceof Player) {
                Player player = (Player) owner;
                sourceCard.getDeck()
                        .discard(player.getDiscard());
            }

            event.consume();
        });

    }

    public Card getCard() {
        return card;
    }

}
