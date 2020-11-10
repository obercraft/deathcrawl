package net.sachau.zarrax.gui.screen;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import net.sachau.zarrax.engine.*;
import net.sachau.zarrax.v2.GEngine;
import net.sachau.zarrax.v2.GEvents;

@GuiComponent
public class CreateGameScreen extends ScreenWithSidebar {

    final private GEngine engine;
    final private GEvents events;

    @Autowired
    public CreateGameScreen(GEngine engine, GEvents events) {
        super();
        this.engine = engine;
        this.events = events;

        HBox welcome = new HBox();
        welcome.setAlignment(Pos.BASELINE_CENTER);
        welcome.getChildren().add(new Text("Hallo"));
        getMainArea().getChildren().add(welcome);


        Button button = new Button();
        button.setText("Start Game");

        getSideArea().getChildren().add(button);

        button.setOnMouseClicked(event -> {
            engine.createGame();
            events.send(GameEventContainer.Type.START_TURN);
        });

    }

}
