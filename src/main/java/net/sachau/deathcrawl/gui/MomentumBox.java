package net.sachau.deathcrawl.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.type.StartingCharacter;
import net.sachau.deathcrawl.engine.Player;
import net.sachau.deathcrawl.gui.card.CardTile;
import net.sachau.deathcrawl.card.momentum.MomentumAction;

public class MomentumBox extends ScrollPane {
    private Player player;

    public MomentumBox(Player player) {
        super();

        VBox container = new VBox();
        setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setMaxWidth(CardTile.WIDTH);
        setFitToHeight(true);
        setFitToWidth(true);
        container.setAlignment(Pos.TOP_LEFT);
        this.player = player;

        HBox momentum = new HBox();
        Text momentumText = new Text(getMomentum());
        momentum.setAlignment(Pos.TOP_LEFT);
        momentum.getChildren().add(momentumText);

        container.getChildren()
                .add(momentum);


        for (Card card : player.getParty()) {
            if (card instanceof StartingCharacter) {
                StartingCharacter startingCharacter = (StartingCharacter) card;
                if (startingCharacter.getMomentumActions()
                        .size() > 0) {
                    for (MomentumAction action : startingCharacter.getMomentumActions()) {
                        MomentumActionLine momentumActionLine = new MomentumActionLine(player, card, action);
                        container.getChildren()
                                .add(momentumActionLine);
                    }
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
