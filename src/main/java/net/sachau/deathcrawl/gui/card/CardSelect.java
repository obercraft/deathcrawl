package net.sachau.deathcrawl.gui.card;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.gui.PartySelection;

import java.util.HashSet;
import java.util.Set;

public class CardSelect extends VBox {



    public CardSelect(PartySelection partySelection, Card card, Deck target, Button partyDone) {

        Button action = new Button();
        action.setText("SELECT");

        getChildren().addAll(new CardTile(card), action);

        action.setOnMouseClicked(event -> {
            try {
                if (partySelection.getUniqueIds().contains(card.getUniqueId())) {
                    Logger.log("deck already contains a " +card.getUniqueId()  + " card");
                    return;
                }




                if (target.size() < 4) {
                    if (target.size() == 3) {
                        partyDone.setDisable(false);
                    } else {
                        partyDone.setDisable(true);
                    }
                    partySelection.getUniqueIds()
                            .add(card.getUniqueId());
                    Card cardtToAdd = card.getClass()
                            .newInstance();
                    cardtToAdd.setOwner(partySelection.getPlayer());
                    target.add(cardtToAdd);
                }  else {
                    Logger.log("party already has four members");
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }
}
