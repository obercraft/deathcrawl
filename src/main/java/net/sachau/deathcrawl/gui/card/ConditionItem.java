package net.sachau.deathcrawl.gui.card;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import net.sachau.deathcrawl.effects.CardEffect;
import net.sachau.deathcrawl.gui.images.TileSet;

public class ConditionItem extends HBox {

    public ConditionItem(CardEffect condition) {
        super();
        getChildren().add(TileSet.getInstance().getTile(condition.getTile()));
        if (condition.getAmount() > 1) {
            Text h = new Text("" + condition.getAmount());
            h.setTextAlignment(TextAlignment.CENTER);
            getChildren().add(h);
        }

    }
}
