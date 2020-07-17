package net.sachau.deathcrawl.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.gui.card.CardTile;
import net.sachau.deathcrawl.momentum.MomentumAction;

public class MomentumBox extends ScrollPane {
    private Player player;
    private Deck deck;


    public MomentumBox(Player player) {
        super();

        VBox container = new VBox();
        setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setHeight(CardTile.HEIGHT + 20);
        setMaxHeight(CardTile.HEIGHT + 20);
        setMaxWidth(CardTile.WIDTH);
        setFitToHeight(true);
        setFitToWidth(true);
        container.setAlignment(Pos.TOP_LEFT);
        this.player = player;
        this.deck = player.getParty();



        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setMinHeight(CardTile.HEIGHT);
        setMinWidth(CardTile.WIDTH);
        setMaxHeight(CardTile.HEIGHT);
        setMaxWidth(CardTile.WIDTH);
//setAlignment(Pos.CENTER);


        HBox momentum = new HBox();
        Text momentumText = new Text(getMomentum());
        momentum.setAlignment(Pos.TOP_LEFT);
        momentum.getChildren().add(momentumText);

        container.getChildren()
                .add(momentum);


        for (Card card : deck.getCards()) {
            if (card.getMomentumActions().size() > 0) {
                for (MomentumAction action : card.getMomentumActions()) {
                    MomentumActionLine momentumActionLine = new MomentumActionLine(player, card, action);
                    container.getChildren()
                            .add(momentumActionLine);
                }
            }
        }

        setContent(container);


        player.momentumProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                momentumText.setText(getMomentum());
            }
        });
    }


    private String getMomentum() {
        return "Momentum : " + player.getMomentum();
    }

}
