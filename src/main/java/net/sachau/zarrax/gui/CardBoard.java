package net.sachau.zarrax.gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.engine.Player;

public class CardBoard extends VBox {

    public CardBoard(Player player) {

        HBox row1 = new HBox();
        HBox row2 = new HBox();
        HBox row3 = new HBox();

        DeckPane playArea = new DeckPane(player.hazardsProperty(), 5, "card-small");
        DeckPane hand = new DeckPane(player.handProperty(), 5, "card-small");
        //DeckPane partyArea = new DeckPane(player.partyProperty(), 5, "card-small");
        PartyPane partyPane = new PartyPane(player);
        ActiveCardPane activeCardPane = new ActiveCardPane();


        row1.getChildren().addAll(partyPane, playArea);
        row2.getChildren().addAll(activeCardPane, hand);
        row3.getChildren().addAll(Logger.getConsole());

        getChildren().addAll(row1, row2, row3);

    }


}
