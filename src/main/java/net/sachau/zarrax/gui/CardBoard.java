package net.sachau.zarrax.gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sachau.zarrax.engine.GuiComponent;
import net.sachau.zarrax.gui.screen.Autowired;
import net.sachau.zarrax.v2.GEngine;
import net.sachau.zarrax.v2.GEvents;
import net.sachau.zarrax.v2.GState;

import java.util.Observable;
import java.util.Observer;

@GuiComponent
public class CardBoard extends VBox implements Observer {

    private final GEvents events;
    private final GState state;

    HBox row1 = new HBox();
    HBox row2 = new HBox();
    HBox row3 = new HBox();

    @Autowired
    public CardBoard(GEvents events, GState state) {
        this.events = events;
        this.state = state;


        getChildren().addAll(row1, row2, row3);

        this.events.addObserver(this);

    }

    public void init() {
        DeckPane playArea = new DeckPane(state.getPlayer().hazardsProperty(), 5, "card-small");
        DeckPane hand = new DeckPane(state.getPlayer().handProperty(), 5, "card-small");
        //DeckPane partyArea = new DeckPane(player.partyProperty(), 5, "card-small");
        PartyPane partyPane = new PartyPane();
        ActiveCardPane activeCardPane = new ActiveCardPane();

        row1.getChildren().clear();
        row2.getChildren().clear();
        row3.getChildren().clear();
        row1.getChildren().addAll(partyPane, playArea);
        row2.getChildren().addAll(activeCardPane, hand);

        Console console = GEngine.getBean(Console.class);
        if (console != null) {
            row3.getChildren().addAll(console);
        }

    }


    @Override
    public void update(Observable o, Object arg) {
        switch (events.getType(arg)) {
            case GUI_STARTENCOUNTER:
                // TODO
                return;

        }
    }
}
