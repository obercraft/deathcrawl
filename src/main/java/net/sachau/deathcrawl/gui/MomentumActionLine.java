package net.sachau.deathcrawl.gui;

import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.catalog.Catalog;
import net.sachau.deathcrawl.engine.Player;
import net.sachau.deathcrawl.gui.card.CardTile;
import net.sachau.deathcrawl.card.momentum.MomentumAction;
import net.sachau.deathcrawl.gui.card.DragEvents;

import java.util.HashMap;
import java.util.Map;

public class MomentumActionLine extends HBox {

    public MomentumActionLine(Player player, Card card, MomentumAction action) {

        Text text = new Text("[" +  action.getCost() + "] " + action.getCard().getClass().getSimpleName());


        getChildren().add(text);

        setOnMouseClicked(event -> {

        });

        setOnDragDetected(event -> {

            if (player.getMomentum() >= action.getCost()) {
                Dragboard db = this.startDragAndDrop(TransferMode.ANY);

                Map<DataFormat, Object> map = new HashMap<>();
                action.getCard().setOwner(player);
                Catalog.putById(action.getCard());
                map.put(DragEvents.momentumFormat, action.getCard()
                        .getId() + "," +  action.getCost());
                db.setContent(map);
            }
            event.consume();
        });



    }
}
