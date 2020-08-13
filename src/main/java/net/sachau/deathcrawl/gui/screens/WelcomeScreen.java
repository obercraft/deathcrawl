package net.sachau.deathcrawl.gui.screens;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.catalog.Catalog;
import net.sachau.deathcrawl.gui.AttackLine;
import net.sachau.deathcrawl.gui.card.CardView;

import java.util.Observable;

public class WelcomeScreen extends HBox {

    public WelcomeScreen(double width, double height) {
        setMinHeight(height);
        setMinWidth(width);

        // getChildren().add(new Text("Welcome to deathcrawl"));
        Card thief = Catalog.getInstance()
                .get("thief");

        Card warrior = Catalog.getInstance()
                .get("warrior");


        CardView cardview = new CardView(thief, "") {
            @Override
            public void update(Observable o, Object arg) {

            }
        };

        CardView cardview1 = new CardView(warrior, "") {
            @Override
            public void update(Observable o, Object arg) {

            }
        };

        this.getChildren().addAll(cardview, cardview1);

        new AttackLine().connect(this, cardview, cardview1);
    }
}
