package net.sachau.deathcrawl.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.GameState;
import net.sachau.deathcrawl.GuiState;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;

import java.util.HashSet;
import java.util.Set;

public class DiscardPile extends StackPane {
    private Deck cards;

    public DiscardPile() {
        super();


        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setMinHeight(CardTile.HEIGHT);
        setMinWidth(CardTile.WIDTH);
        setMaxHeight(CardTile.HEIGHT);
        setMaxWidth(CardTile.WIDTH);
        setAlignment(Pos.CENTER);

        Rectangle rectangle = new Rectangle(CardTile.WIDTH, CardTile.HEIGHT);
        rectangle.setFill(Color.ANTIQUEWHITE);
        Text h = new Text("DISCARD");
        h.setFill(Color.BLACK);
        this.getChildren()
                .addAll(rectangle, h);

        setOnDragOver(event -> {
            if (event.getDragboard().hasContent(CardTile.cardFormat)) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });

        setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();

            Card source = (Card) db.getContent(CardTile.cardFormat);



            GameState.getInstance().getPlayer().getHand().discard(GameState.getInstance().getPlayer().getDiscard());
            add(source);
            GuiState.getInstance()
                    .getHandHolder().remove(source);



            // Deathcrawl.getInstance().getCardHolders().get(this.getCard().getPosition().getHolderIndex()).remove(this.getCard().getPosition().getCardIndex());
            event.consume();
        });

    }

    public void add(Card card) {
        CardTile cardTile = GuiState.getInstance().getCardTile(card);
        getChildren().add(cardTile);
    }


    public void remove(Card card) {
        CardTile targetCardTile = null;
        for (Node node : getChildren()) {
            if (node instanceof CardTile) {
                CardTile ct = (CardTile) node;
                if (ct.getCard().getId() == card.getId()) {
                    targetCardTile = ct;
                }
            }
            if (targetCardTile != null) {
                getChildren().remove(targetCardTile);
            }
        }
    }

    public void removeAll() {
        Set<Node> childs = new HashSet<>();
        if (cards.size() != 0) {
            for (Card c : cards.getAll()) {
                for (Node node : getChildren()) {
                    if (node instanceof CardTile) {
                        CardTile ct = (CardTile) node;
                        if (ct.getCard().getId() == c.getId()) {
                            childs.add(ct);
                        }
                    }
                }
            }
        }
        getChildren().removeAll(childs);
    }

    public Deck getCards() {
        return cards;
    }

    public void setCards(Deck cards) {
        this.cards = cards;
    }
}
