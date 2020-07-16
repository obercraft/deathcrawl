package net.sachau.deathcrawl.gui;

import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.sachau.deathcrawl.GameState;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.gui.card.CardTile;

public class DrawPile extends StackPane {
    private Player player;
    private Deck deck;

    public DrawPile(Player player) {
        super();
        this.player = player;
        this.deck = player.getDraw();

        Button button = new Button(getDrawText());

        deck.getCards()
                .addListener(new ListChangeListener<Card>() {
                    @Override
                    public void onChanged(Change<? extends Card> change) {
                        while (change.next()) {
                            if (deck.size() > 0) {
                                button.setText(getDrawText());
                                button.setDisable(false);
                            } else {
                                button.setText(getDrawText());
                                button.setDisable(true);
                            }
                        }
                    }
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


        button.setOnMouseClicked(event -> {

            if (deck.size() > 0) {
                deck.draw(player.getHand());
            }


        });
    }

    private String getDrawText() {
        if (deck == null || deck.size() == 0) {
            return "DRAW";
        } else {
            return "DRAW [" + deck.size() + "]";
        }

    }

}
