package net.sachau.deathcrawl.gui;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.GameState;
import net.sachau.deathcrawl.GuiState;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.gui.images.TileSet;

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
        setWidth(WIDTH);
        setHeight(HEIGHT);

        if (!card.isVisible()) {
            Rectangle rectangle = new Rectangle(WIDTH, HEIGHT);
            rectangle.setFill(Color.ANTIQUEWHITE);
            rectangle.setStyle("-fx-fill: red; -fx-stroke: black; -fx-stroke-width: 5;");

            ImageView imageView = TileSet.getInstance()
                    .getTile(TileSet.Tile.DEATHCRAWL);
            this.getChildren()
                    .addAll(rectangle, imageView);
        } else {
            Rectangle rectangle = new Rectangle(WIDTH, HEIGHT);
            rectangle.setFill(Color.ANTIQUEWHITE);
            Text h = new Text(card.getName() + "\n" + card.getId());
            h.setFill(Color.BLACK);
            this.getChildren()
                    .addAll(rectangle, h);
        }

        setOnMouseClicked(event -> {
                if (event.getButton()
                        .equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2 && card.isPlayable()) {
                        System.out.println("play " + card);
                    }

                    if (event.getClickCount() == 2 && card.isDrawable()) {

                        GuiState.getInstance().getDrawPile().remove(card);
                        GameState.getInstance().getPlayer().getDraw().remove(card);

                        card.setVisible(true);
                        GuiState.getInstance().getHandHolder().add(card);
                        GameState.getInstance().getPlayer().getHand().add(card);

                    }
                }

            event.consume();
        });

        setOnDragDetected(event -> {
            /* drag was detected, start a drag-and-drop gesture*/
            /* allow any transfer mode */
            if (card.isPlayable()) {
                Dragboard db = this.startDragAndDrop(TransferMode.ANY);

                /* Put a string on a dragboard */
                Map<DataFormat, Object> dbContent = new HashMap<>();
                dbContent.put(cardFormat, card);
                db.setContent(dbContent);
            }
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

            if (card.isDiscarded()) {
                GameState.getInstance().getPlayer().getHand().remove(source);
                GameState.getInstance().getPlayer().getDiscard().add(source);
                GuiState.getInstance().getDiscardPile().add(source);
                GuiState.getInstance()
                        .getHandHolder()
                        .remove(source);




            }
            event.consume();
        });

    }

    public Card getCard() {
        return card;
    }

}
