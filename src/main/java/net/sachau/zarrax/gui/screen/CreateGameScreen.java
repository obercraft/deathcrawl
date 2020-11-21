package net.sachau.zarrax.gui.screen;

import javafx.scene.control.Button;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.engine.*;
import net.sachau.zarrax.gui.SelectCardsPane;
import net.sachau.zarrax.v2.GEngine;
import net.sachau.zarrax.v2.GEvents;

import java.util.List;

@GuiComponent
public class CreateGameScreen extends ScreenWithSidebar {

    final private GEngine engine;
    final private GEvents events;
    final private Catalog catalog;

    @Autowired
    public CreateGameScreen(GEngine engine, GEvents events, Catalog catalog) {
        super();
        this.engine = engine;
        this.events = events;
        this.catalog = catalog;

        List<Card> characters = catalog.get(Character.class);

        SelectCardsPane selectCardsPane = new SelectCardsPane(characters, 5);
        getMainArea().getChildren().add(selectCardsPane);


        Button randomPartyButton = new Button();
        randomPartyButton.setText("RANDOM PARTY");

        getSideArea().getChildren().add(randomPartyButton);

        randomPartyButton.setOnMouseClicked(event -> {
            engine.startGameWithRandomParty();
            events.send(GameEventContainer.Type.START_TURN);
        });

    }

}
