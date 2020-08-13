package net.sachau.deathcrawl.gui;

import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.engine.Player;
import net.sachau.deathcrawl.gui.card.CardTile;
import net.sachau.deathcrawl.gui.card.CornerValueBox;
import net.sachau.deathcrawl.gui.card.DragEvents;

public class PartyPaneRow extends HBox {



    public PartyPaneRow(Card card) {
        super();
        setHeight(30);
        setAlignment(Pos.BASELINE_LEFT);

        CornerValueBox cornerValueBox = new CornerValueBox(card.hitsProperty(), card.maxHitsProperty());

        TextFlow name = CardText.builder().add(card.getName()).write();

        this.getChildren().addAll(name, cornerValueBox);

        setOnDragOver(new DragEvents().dragOver());
        setOnDragDropped(new DragEvents().dragDropped(card));

    }

}
