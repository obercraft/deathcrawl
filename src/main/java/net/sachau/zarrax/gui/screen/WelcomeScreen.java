package net.sachau.zarrax.gui.screen;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.engine.GuiComponent;
import net.sachau.zarrax.v2.GEngine;
import net.sachau.zarrax.v2.GEvents;

@GuiComponent
public class WelcomeScreen extends ScreenWithSidebar {

    private final Catalog catalog;

    private final GEvents events;

    @Autowired
    public WelcomeScreen(Catalog catalog, GEvents events) {
        super();
        this.catalog = catalog;
        this.events = events;
        HBox welcome = new HBox();
        welcome.setAlignment(Pos.BASELINE_CENTER);
        welcome.getChildren().add(this.catalog.getText("intro"));
        getMainArea().getChildren().add(welcome);


        Button newGame = new Button();
        newGame.setText("NEW GAME");

        getSideArea().getChildren().add(newGame);

        newGame.setOnMouseClicked(event -> {
            events.send(GameEventContainer.Type.CREATE_GAME);
        });
    }

}
