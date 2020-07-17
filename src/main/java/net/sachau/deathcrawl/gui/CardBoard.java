package net.sachau.deathcrawl.gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sachau.deathcrawl.dto.Player;

public class CardBoard extends VBox {

    public CardBoard(Player player) {

        HBox row1 = new HBox();
        HBox row2 = new HBox();
        HBox row3 = new HBox();


        PlayArea playArea = new PlayArea(player, 5);
        DrawPile drawPile = new DrawPile(player);
        DiscardPile discardPile = new DiscardPile(player);
        HandHold hand = new HandHold(player, 5);
        PartyArea partyArea = new PartyArea(player, 5);

        MomentumBox momentumBox = new MomentumBox(player);

        row1.getChildren().addAll(playArea, drawPile);
        row2.getChildren().addAll(hand, discardPile);
        row3.getChildren().addAll(partyArea, momentumBox);

        getChildren().addAll(row1, row2, row3);

    }


}
