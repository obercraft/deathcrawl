package net.sachau.deathcrawl.gui;

import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.CardCache;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.gui.card.CardTile;
import net.sachau.deathcrawl.momentum.MomentumAction;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MomentumActionLine extends HBox {

    public MomentumActionLine(Player player, Card card, MomentumAction action) {

        Text text = new Text("[" +  action.getCost() + "] " + action.getCard().getName());


        getChildren().add(text);

        setOnMouseClicked(event -> {

        });

        setOnDragDetected(event -> {

            if (player.getMomentum() >= action.getCost()) {
                Dragboard db = this.startDragAndDrop(TransferMode.ANY);

                Map<DataFormat, Object> map = new HashMap<>();
                action.getCard().setOwner(player);
                CardCache.put(action.getCard());
                map.put(CardTile.momentumFormat, action.getCard()
                        .getId());
                db.setContent(map);
            }
            event.consume();
        });



    }
}
