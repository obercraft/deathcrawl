package net.sachau.zarrax.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextFlow;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.gui.card.CornerValueBox;
import net.sachau.zarrax.gui.card.DragEvents;
import net.sachau.zarrax.gui.text.CardText;

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
