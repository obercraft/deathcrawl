package net.sachau.zarrax.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.engine.GameComponent;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@GameComponent
public class CatalogView extends HBox {

    @Resource
    private Catalog catalog;

    public CatalogView() {
        super();

    }
    @PostConstruct
    public void init() {
        ListView<Class<? extends Card>> categoryList = new ListView<>();
        ListView<Card> cardList = new ListView<>();

        ObservableList<Class<? extends Card>> categories = FXCollections.observableList(catalog.getCategories());


        Class<? extends Card> startingCategory = categories.get(0);
        ObservableList<Card> cards = FXCollections.observableArrayList(catalog.get(startingCategory));

        categoryList.setItems(categories);
        cardList.setItems(cards);

        this.getChildren().addAll(categoryList, cardList);
        categoryList.setCellFactory(new Callback<ListView<Class<? extends Card>>, ListCell<Class<? extends Card>>>() {
            @Override
            public ListCell<Class<? extends Card>> call(ListView<Class<? extends Card>> param) {
                return new CategoryCell();
            }
        });

        cardList.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            @Override
            public ListCell<Card> call(ListView<Card> param) {
                return new CardCell();
            }
        });

        categoryList.setOnMouseClicked(event -> {
            Class<? extends Card> selectedItem = categoryList.getSelectionModel().getSelectedItem();
            Logger.debug("-> " + selectedItem);
            List<Class<? extends Card>> cats = catalog.getCategories();
            if (cardList.getItems().size() > 0) {
                cardList.getItems()
                        .remove(0, cardList.getItems().size());
            }
            cardList.getItems().addAll(catalog.get(selectedItem));
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


    private class CardCell extends ListCell<Card> {

        @Override
        public void updateItem(Card card, boolean empty) {
            super.updateItem(card, empty);
            if (card != null) {
                setText(card.getName() + "@" + card.getId());
            }
        }

    }

}
