package net.sachau.zarrax.gui.screens;

import javafx.scene.layout.HBox;
import javafx.scene.text.TextFlow;
import net.sachau.zarrax.Main;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.gui.AttackLine;
import net.sachau.zarrax.gui.card.CardView;
import net.sachau.zarrax.gui.text.TextParser;

import java.util.Map;
import java.util.Observable;

public class WelcomeScreen extends HBox {

    public WelcomeScreen(double width, double height) {
        setMinHeight(height);
        setMinWidth(width);

        try {
            getChildren().add(Catalog.getText("intro"));
        } catch (Exception e) {

        }

//        // getChildren().add(new Text("Welcome to deathcrawl"));
//        Card thief = Catalog.getInstance()
//                .get("thief");
//
//        Card warrior = Catalog.getInstance()
//                .get("warrior");
//
//
//        CardView cardview = new CardView(thief, "") {
//            @Override
//            public void update(Observable o, Object arg) {
//
//            }
//        };
//
//        CardView cardview1 = new CardView(warrior, "") {
//            @Override
//            public void update(Observable o, Object arg) {
//
//            }
//        };
//
//        this.getChildren().addAll(cardview, cardview1);
//
//        new AttackLine().connect(this, cardview, cardview1);
    }
}
