package net.sachau.deathcrawl.gui;

import javafx.scene.Parent;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.Deathcrawl;
import net.sachau.deathcrawl.cards.Card;

import java.util.HashMap;
import java.util.Map;

public class CardTile extends StackPane {

    private static final DataFormat cardFormat = new DataFormat("card");

    private static final double HEIGHT = 200;
    private static final double WIDTH = 150;
    private CardHolder cardHolder;
    private Card card;

    public static CardTile createCardTile(int index, Card card, CardHolder cardHolder) {
        return new CardTile(cardHolder, card, index * WIDTH, 0, WIDTH, HEIGHT);
    }

    private CardTile(CardHolder cardHolder, Card card, double x, double y, double width, double height) {
        setWidth(150);
        setHeight(200);
        setCardHolder(cardHolder);
        setCard(card);


        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(Color.ANTIQUEWHITE);
        rectangle.setStyle("-fx-fill: red; -fx-stroke: black; -fx-stroke-width: 5;");

        Text h = new Text(card.getName());
        h.setFill(Color.BLACK);
        this.getChildren()
                .addAll(rectangle, h);

        setOnMouseClicked(event -> {
            if (event.getButton()
                    .equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    System.out.println("play " + this.getCard());
                    //this.getCardHolder()
                    //        .remove(this.getCard().getPosition().getCardIndex(), this);
                    event.consume();
                } else if (event.getClickCount() == 1) {
                    System.out.println("select " + this.getCard());
                    event.consume();
                }
            }
        });

        setOnDragDetected(event -> {
            /* drag was detected, start a drag-and-drop gesture*/
            /* allow any transfer mode */
            System.out.println("drag");
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);

            /* Put a string on a dragboard */
            Map<DataFormat, Object> dbContent = new HashMap<>();
            dbContent.put(cardFormat, this.getCard());
            db.setContent(dbContent);
            event.consume();
        });

        setOnDragOver(event -> {
            if (event.getDragboard().hasContent(cardFormat)) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });

        setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();

            Card source = (Card) db.getContent(cardFormat);

            System.out.println(source + " -> " + this.getCard());
            // Deathcrawl.getInstance().getCardHolders().get(this.getCard().getPosition().getHolderIndex()).remove(this.getCard().getPosition().getCardIndex());
            event.consume();
        });

    }

    public CardHolder getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(CardHolder cardHolder) {
        this.cardHolder = cardHolder;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
