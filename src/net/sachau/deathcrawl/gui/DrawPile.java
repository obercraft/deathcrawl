package net.sachau.deathcrawl.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.GameState;
import net.sachau.deathcrawl.GuiState;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;

public class DrawPile extends StackPane {
    private Deck cards;

    public DrawPile() {
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
        Text h = new Text("DRAW");
        h.setFill(Color.BLACK);
        this.getChildren()
                .addAll(rectangle, h);

        setOnMouseClicked(event -> {
            if (event.getButton()
                    .equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    GameState.getInstance().getPlayer().getDiscard().moveAll(GameState.getInstance().getPlayer().getDraw());
                    GameState.getInstance().getPlayer().getDraw().shuffe();
                }

            }
        });


    }

    public Deck getCards() {
        return cards;
    }

    public void setCards(Deck cards) {
        this.cards = cards;
        for (Card card : cards.getAll()) {
            getChildren().add(GuiState.getInstance().getCardTile(card));
        }
    }

    public void remove(Card card) {
        getChildren().remove(GuiState.getInstance().getCardTile(card));
    }

    public void add(Card card) {
        getChildren().add(GuiState.getInstance().getCardTile(card));
    }
}
