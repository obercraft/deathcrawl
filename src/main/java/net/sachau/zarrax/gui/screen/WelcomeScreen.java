package net.sachau.zarrax.gui.screen;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.engine.GameEvent;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.engine.GuiComponent;
import net.sachau.zarrax.v2.GEngine;

@GuiComponent
public class WelcomeScreen extends ScreenWithSidebar {

    private final Catalog catalog;

    private final GEngine engine;

    @Autowired
    public WelcomeScreen(Catalog catalog, GEngine engine) {
        super();
        this.catalog = catalog;
        this.engine = engine;
        HBox welcome = new HBox();
        welcome.setAlignment(Pos.BASELINE_CENTER);
        welcome.getChildren().add(this.catalog.getText("intro"));
        getMainArea().getChildren().add(welcome);


        Button newGame = new Button();
        newGame.setText("NEW GAME");

        getSideArea().getChildren().add(newGame);

        newGame.setOnMouseClicked(event -> {
            engine.send(GameEventContainer.Type.CREATE_GAME);
        });
    }

}
