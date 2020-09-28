package net.sachau.zarrax.gui.screen;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import net.sachau.zarrax.engine.GameComponent;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.GameEvent;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.gui.map.WorldMap;

import javax.annotation.Resource;

@GameComponent
public class MovementScreen extends ScreenWithSidebar {

    @Resource
    private WorldMap worldMap;

    public MovementScreen() {
        super();

        getMainArea().getChildren().add(worldMap);


        Button button = new Button();
        button.setText("Start Game");

        getSideArea().getChildren().add(button);

        button.setOnMouseClicked(event -> {
            GameEvent.getInstance().send(GameEventContainer.Type.START_TURN);
        });

    }

    public void init() {
        worldMap.init();
    }
}
