package net.sachau.zarrax.gui.screen;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.engine.GameEvent;
import net.sachau.zarrax.engine.GameEventContainer;

import java.util.Observer;

public class WelcomeScreen extends ScreenWithSidebar {

    public WelcomeScreen(double width, double height) {
        super(width, height);

        HBox welcome = new HBox();
        welcome.setAlignment(Pos.BASELINE_CENTER);
        welcome.getChildren().add(Catalog.getText("intro"));
        getMainArea().getChildren().add(welcome);


        Button newGame = new Button();
        newGame.setText("NEW GAME");

        getSideArea().getChildren().add(newGame);

        newGame.setOnMouseClicked(event -> {
            GameEvent.getInstance().send(GameEventContainer.Type.CREATE_GAME);
        });

    }

}
