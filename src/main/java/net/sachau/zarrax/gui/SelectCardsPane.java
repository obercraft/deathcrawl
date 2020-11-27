package net.sachau.zarrax.gui;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.UniqueCardList;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.gui.card.CardInfoView;
import net.sachau.zarrax.gui.card.CardTile;

import java.util.List;

public class SelectCardsPane extends VBox {

    private SimpleListProperty<Card> availableList;

    private SimpleListProperty<Card> selectedList;

    private HBox availableContainer = new HBox();
    private HBox selectedContainer = new HBox();

    public SelectCardsPane(List<Card> availableCards, int numberOfCards) {
        super();

        availableList = new SimpleListProperty<>(FXCollections.observableArrayList(availableCards));
        selectedList = new SimpleListProperty<>(FXCollections.observableArrayList());

        ScrollPane availableCardRow = createCardRow("card", 5);
        availableContainer.setAlignment(Pos.TOP_LEFT);
        availableCardRow.setContent(availableContainer);

        ScrollPane selectedCardRow = createCardRow("card", 5);

        selectedContainer.setAlignment(Pos.TOP_LEFT);
        selectedCardRow.setContent(selectedContainer);


        this.getChildren().addAll(availableCardRow, selectedCardRow);

        for (Card card : availableCards) {
            try {
                CardInfoView sourceView = new AvailableView(card, "card", null);
                availableContainer.getChildren()
                        .add(sourceView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private ScrollPane createCardRow(String cssClass, int length) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinHeight(CardTile.HEIGHT);
        setMinHeight((cssClass.contains("small") ? CardTile.HEIGHT_SMALL : CardTile.HEIGHT) +20);

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setMinWidth(CardTile.WIDTH * length);


        return scrollPane;
    }

    public ObservableList<Card> getSelectedList() {
        return selectedList.get();
    }

    public SimpleListProperty<Card> selectedListProperty() {
        return selectedList;
    }

    private class AvailableView extends CardInfoView {

        public AvailableView(Card card, String cssClass, EventHandler<? super MouseEvent> mouseClicked) {
            super(card, cssClass, mouseClicked);

            setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Object source = event.getSource();
                    if (source instanceof CardInfoView) {
                        AvailableView cardInfoView = (AvailableView) source;
                        if (selectedList.size() < Player.PARTY_SIZE) {
                            selectedContainer.getChildren()
                                    .add(new SelectedView(cardInfoView.getCard(), "card", null));
                            selectedList.add(cardInfoView.getCard());
                        }
                    }
                }
            });
        }
    }

    private class SelectedView extends CardInfoView {

        public SelectedView(Card card, String cssClass, EventHandler<? super MouseEvent> mouseClicked) {
            super(card, cssClass, mouseClicked);

            setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Object source = event.getSource();
                    if (source instanceof SelectedView) {
                        SelectedView cardInfoView = (SelectedView) source;
                        if (selectedList.contains(((SelectedView) source).getCard())) {
                            selectedContainer.getChildren()
                                    .remove(cardInfoView);
                            selectedList.remove(cardInfoView.getCard());
                        }
                    }
                }
            });
        }

    }

}
