package net.sachau.zarrax.gui.screen;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.engine.*;
import net.sachau.zarrax.gui.Console;
import net.sachau.zarrax.gui.SelectCardsPane;
import net.sachau.zarrax.v2.GEngine;
import net.sachau.zarrax.v2.GEvents;

import java.util.List;

@GuiComponent
public class CreateGameScreen extends ScreenWithSidebar {

    final private GEngine engine;
    final private GEvents events;
    final private Catalog catalog;

    private Button selectPartyButton;

    @Autowired
    public CreateGameScreen(GEngine engine, GEvents events, Catalog catalog) {
        super();
        this.engine = engine;
        this.events = events;
        this.catalog = catalog;

        List<Card> characters = catalog.get(Character.class);

        SelectCardsPane selectCardsPane = new SelectCardsPane(characters, 5);
        getMainArea().getChildren().add(selectCardsPane);

        Console console = GEngine.getBean(Console.class);

            getMainArea().getChildren().addAll(console);




        Button randomPartyButton = new Button();
        randomPartyButton.setText("RANDOM PARTY");

        selectPartyButton = new Button();
        selectPartyButton.setText("SELECT DONE");
        selectPartyButton.setDisable(true);



        getSideArea().getChildren().addAll(randomPartyButton, selectPartyButton, catalog.getText("creategame"));

        randomPartyButton.setOnMouseClicked(event -> {
            engine.startGameWithRandomParty();
            events.send(GameEventContainer.Type.START_TURN);
        });

        selectPartyButton.setOnMouseClicked(event -> {
            engine.startGameWithParty(selectCardsPane.getSelectedList());
            events.send(GameEventContainer.Type.START_TURN);
        });

        selectCardsPane.selectedListProperty().addListener(new ChangeListener<ObservableList<Card>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<Card>> observable, ObservableList<Card> oldValue, ObservableList<Card> newValue) {

                if (Player.PARTY_SIZE == newValue.size()) {
                    selectPartyButton.setDisable(false);
                } else {
                    selectPartyButton.setDisable(true);
                }

            }
        });

    }

}
