package net.sachau.deathcrawl.gui;

import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.gui.card.CardTile;
import net.sachau.deathcrawl.gui.card.CardTileCache;

public class DiscardPile extends StackPane {
    private Player player;
    private Deck deck;

    public DiscardPile(Player player) {
        super();
        this.player = player;
        this.deck = player.getDiscard();



        Button button = new Button(getDiscardText(player.getDiscard().size()));
        button.setDisable(player.getDraw().size() != 0);

        deck.getCards()
                .addListener(new ListChangeListener<Card>() {
                    @Override
                    public void onChanged(Change<? extends Card> change) {
                        while (change.next()) {
                            if (player.getDraw().size() ==  0) {
                                button.setText(getDiscardText(player.getDiscard().size()));
                                button.setDisable(false);
                            } else {
                                button.setText(getDiscardText(player.getDiscard().size()));
                                button.setDisable(true);
                            }
                        }
                    }
                });

        button.setOnMouseClicked(event -> {
            player.getDiscard().moveAll(player.getDraw());
            player.getDraw().shuffe();
            player.endTurn();
        });


        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setMinHeight(CardTile.HEIGHT);
        setMinWidth(CardTile.WIDTH);
        setMaxHeight(CardTile.HEIGHT);
        setMaxWidth(CardTile.WIDTH);
        setAlignment(Pos.CENTER);

        Rectangle rectangle = new Rectangle(CardTile.WIDTH, CardTile.HEIGHT);
        rectangle.setFill(Color.ANTIQUEWHITE);
        this.getChildren()
                .addAll(rectangle, button);

        setOnDragOver(event -> {
            if (event.getDragboard().hasContent(CardTile.cardFormat)) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });

        setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();

            CardTile cardTile = CardTileCache.getTile((Long) db.getContent(CardTile.cardFormat));


            Card card = cardTile.getCard();

            card.getDeck().discard(player.getDiscard());

            System.out.println(player.getDiscard());
            event.consume();
        });

    }

    public Deck getCards() {
        return deck;
    }

    public void setCards(Deck cards) {
        this.deck = cards;
    }

    public String getDiscardText(int size) {
            return "DISCARD [" +size + "]";
    }
}
