package net.sachau.zarrax.gui.screen;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.engine.ApplicationContext;
import net.sachau.zarrax.engine.GameComponent;
import net.sachau.zarrax.engine.GameEvent;
import net.sachau.zarrax.engine.GameEventContainer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Observer;

@GameComponent
public class WelcomeScreen extends ScreenWithSidebar {

    @Resource
    private Catalog catalog;

    @PostConstruct
    public void init() {
        HBox welcome = new HBox();
        welcome.setAlignment(Pos.BASELINE_CENTER);
        welcome.getChildren().add(catalog.getText("intro"));
        getMainArea().getChildren().add(welcome);


        Button newGame = new Button();
        newGame.setText("NEW GAME");

        getSideArea().getChildren().add(newGame);

        newGame.setOnMouseClicked(event -> {
            GameEvent.getInstance().send(GameEventContainer.Type.CREATE_GAME);
        });
    }

}
