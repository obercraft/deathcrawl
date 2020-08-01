package net.sachau.deathcrawl.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.catalog.Catalog;
import net.sachau.deathcrawl.gui.card.CardInfoView;

import java.util.Observable;

public class CardInfoList extends ListView<Card> {

    public CardInfoList() {
        super();

        ObservableList<Card> items = FXCollections.observableArrayList((Catalog.getInstance().getIdCache().values()));
        this.setItems(items);

        this.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            @Override
            public ListCell<Card> call(ListView<Card> param) {
                return new InfoCell(param);
            }
        });
    }

    private class InfoCell extends ListCell<Card> {

        public InfoCell(Card card) {

        }

    }
}
