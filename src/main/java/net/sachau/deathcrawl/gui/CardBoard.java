package net.sachau.deathcrawl.gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sachau.deathcrawl.dto.Player;

public class CardBoard extends VBox {

    public CardBoard(Player player) {

        HBox row1 = new HBox();
        HBox row2 = new HBox();
        HBox row3 = new HBox();


        DeckPane playArea = new DeckPane(player.getHazard(), 5, "card-small");
        DrawPile drawPile = new DrawPile(player);
        DiscardPile discardPile = new DiscardPile(player);
        DeckPane hand = new DeckPane(player.getHand(), 5, "card-small");
        DeckPane partyArea = new DeckPane(player.getParty(), 5, "card-small");

        MomentumBox momentumBox = new MomentumBox(player);

        row1.getChildren().addAll(playArea);
        row2.getChildren().addAll(hand);
        row3.getChildren().addAll(partyArea, momentumBox);

        getChildren().addAll(row1, row2, row3);

    }


}
