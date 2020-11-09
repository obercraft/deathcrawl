package net.sachau.zarrax.gui.screen;

import javafx.scene.control.Button;
import net.sachau.zarrax.engine.GameComponent;
import net.sachau.zarrax.engine.GameEvent;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.engine.GuiComponent;
import net.sachau.zarrax.gui.map.WorldMap;

@GuiComponent
public class MovementScreen extends ScreenWithSidebar {

    final private WorldMap worldMap;

    @Autowired
    public MovementScreen(WorldMap worldMap) {
        super();
        this.worldMap = worldMap;

        getMainArea().getChildren().add(this.worldMap);


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
