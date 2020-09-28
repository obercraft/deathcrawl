package net.sachau.zarrax.gui.screen;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.engine.GameComponent;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.GameEvent;
import net.sachau.zarrax.engine.GameEventContainer;

import javax.annotation.Resource;

@GameComponent
public class CreateGameScreen extends ScreenWithSidebar {

    @Resource
    private GameEngine gameEngine;

    public CreateGameScreen() {
        super();

        HBox welcome = new HBox();
        welcome.setAlignment(Pos.BASELINE_CENTER);
        welcome.getChildren().add(new Text("Hallo"));
        getMainArea().getChildren().add(welcome);


        Button button = new Button();
        button.setText("Start Game");

        getSideArea().getChildren().add(button);

        button.setOnMouseClicked(event -> {
            gameEngine.createGame();
            GameEvent.getInstance().send(GameEventContainer.Type.START_TURN);
        });

    }

}
