package net.sachau.zarrax.gui;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.UniqueCardList;

import java.util.List;

public class SelectCardsPane extends VBox {

    private SimpleListProperty<Card> availableList;

    private SimpleListProperty<Card> selectedList;


    public SelectCardsPane(List<Card> availableCards, int numberOfCards) {
        super();

        availableList = new SimpleListProperty<>(FXCollections.observableArrayList(availableCards));
        selectedList = new SimpleListProperty<>(FXCollections.observableArrayList());

        CardRow availableCardRow = new CardRow(availableList, 5, "");

        CardRow selectedCardRow = new CardRow(selectedList, 5, "");

        this.getChildren().addAll(availableCardRow, selectedCardRow);


    }

}
