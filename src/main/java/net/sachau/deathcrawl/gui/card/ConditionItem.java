package net.sachau.deathcrawl.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.conditions.Condition;
import net.sachau.deathcrawl.gui.images.TileSet;

public class ConditionItem extends HBox {

    public ConditionItem(Condition condition) {
        super();
        getChildren().add(TileSet.getInstance().getTile(condition.getTile()));
        if (condition.getAmount() > 1) {
            Text h = new Text("" + condition.getAmount());
            h.setTextAlignment(TextAlignment.CENTER);
            getChildren().add(h);
        }

    }
}
