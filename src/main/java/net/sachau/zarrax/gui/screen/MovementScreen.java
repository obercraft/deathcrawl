package net.sachau.zarrax.gui.screen;

import javafx.scene.control.Button;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.engine.GuiComponent;
import net.sachau.zarrax.gui.map.WorldMap;
import net.sachau.zarrax.v2.GEvents;

@GuiComponent
public class MovementScreen extends ScreenWithSidebar {

    final private WorldMap worldMap;
    private final GEvents events;

    @Autowired
    public MovementScreen(WorldMap worldMap, GEvents events) {
        super();
        this.worldMap = worldMap;
        this.events = events;

        getMainArea().getChildren().add(this.worldMap);


        Button button = new Button();
        button.setText("Start Game");

        getSideArea().getChildren().add(button);

        button.setOnMouseClicked(event -> {
            events.send(GameEventContainer.Type.START_TURN);
        });

    }

    public void init() {
        worldMap.init();
    }
}
