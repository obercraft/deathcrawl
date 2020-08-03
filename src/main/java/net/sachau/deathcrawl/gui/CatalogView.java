package net.sachau.deathcrawl.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.catalog.Catalog;

import java.util.ArrayList;

public class CatalogView extends HBox {

    public CatalogView() {
        super();

        ListView<Class<? extends Card>> categoryList = new ListView<>();
        ListView<Card> cardList = new ListView<>();

        ObservableList<Class<? extends Card>> categories = FXCollections.observableList(Catalog.getInstance().getCategories());


        Class<? extends Card> startingCategory = categories.get(0);
        ObservableList<Card> cards = FXCollections.observableArrayList(Catalog.getInstance().get(startingCategory));

        categoryList.setItems(categories);
        cardList.setItems(cards);

        this.getChildren().add(categoryList);
        categoryList.setCellFactory(new Callback<ListView<Class<? extends Card>>, ListCell<Class<? extends Card>>>() {
            @Override
            public ListCell<Class<? extends Card>> call(ListView<Class<? extends Card>> param) {
                return new CategoryCell();
            }
        });

        categoryList.setOnMouseClicked(event -> {
            Class<? extends Card> selectedItem = categoryList.getSelectionModel().getSelectedItem();
        });
    }

    private class CategoryCell extends ListCell<Class<? extends Card>> {

        @Override
        public void updateItem(Class<? extends Card> cardClass, boolean empty) {
            super.updateItem(cardClass, empty);
            if (cardClass != null) {
                setText(cardClass.getSimpleName());
            }
        }

    }

    private class CategoryCell extends ListCell<Class<? extends Card>> {

        @Override
        public void updateItem(Class<? extends Card> cardClass, boolean empty) {
            super.updateItem(cardClass, empty);
            if (cardClass != null) {
                setText(cardClass.getSimpleName());
            }
        }

    }

}
