package net.sachau.deathcrawl.gui.screens;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.catalog.Catalog;
import net.sachau.deathcrawl.gui.card.CardView;

import java.util.Observable;

public class WelcomeScreen extends StackPane {

    public WelcomeScreen(double width, double height) {
        setMinHeight(height);
        setMinWidth(width);

        // getChildren().add(new Text("Welcome to deathcrawl"));
        Card thief = Catalog.getInstance()
                .get("thief");

        CardView cardview = new CardView(thief, "") {
            @Override
            public void update(Observable o, Object arg) {

            }
        };
        this.getChildren().add(cardview);
    }
}
