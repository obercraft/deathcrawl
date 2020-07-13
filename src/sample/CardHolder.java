package sample;

import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class CardHolder extends ScrollPane {

    HBox container = new HBox();
    List<CardTile> cards = new LinkedList<>();

    public CardHolder() {
        setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setVbarPolicy(ScrollBarPolicy.NEVER);
        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setMaxHeight(250);
        setMaxWidth(800);
        container.setAlignment(Pos.CENTER);
        setContent(container);
    }

    public void addCard(String name) {
        CardTile cardTile = CardTile.createCardTile(cards.size(), new Card(name), this);
        cards.add(cardTile);
        container.getChildren().add(cardTile);
    }


    public void remove(int index, CardTile cardTile) {
        container.getChildren().remove(cardTile);
        cards.remove(cardTile);
    }
}
