package net.sachau.deathcrawl.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.gui.card.CardTile;

public class DrawPile extends StackPane {
    private Player player;
    private Deck draw;
    private Deck discard;

    public DrawPile(Player player) {
        super();
        this.player = player;
        this.draw = player.getDraw();
        this.discard = player.getDiscard();

        Button button = new Button(getDrawText());

        draw.getCards()
                .addListener(new ListChangeListener<Card>() {
                    @Override
                    public void onChanged(Change<? extends Card> change) {
                        while (change.next()) {
                            if (draw.size() > 0 || discard.size() >0) {
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

        HBox momentum = new HBox();
        Text momentumText = new Text(getMomentum());
        momentum.setAlignment(Pos.TOP_LEFT);
        momentum.getChildren()
                .add(momentumText);


        this.getChildren()
                .addAll(rectangle, momentum, button);


        button.setOnMouseClicked(event -> {



        });

        player.momentumProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        momentumText.setText(getMomentum());
                    }
                });
    }

    private String getDrawText() {
        if (draw == null || draw.size() == 0) {
            return "DRAW";
        } else {
            return "DRAW [" + draw.size() + "]";
        }

    }

    private String getMomentum() {
        return "Momentum : " + player.getMomentum();
    }

}
