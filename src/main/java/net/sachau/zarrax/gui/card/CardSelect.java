package net.sachau.zarrax.gui.card;

import javafx.beans.property.SimpleListProperty;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.gui.PartySelection;

import java.util.Observable;

@Deprecated
public class CardSelect extends CardView {


    public CardSelect(PartySelection partySelection, Character card, SimpleListProperty<Card> target, String cssClass) {
        super(card, cssClass);


        if (card.getStartingCards() != null) {
            Tooltip tooltip = new Tooltip();
            HBox tooltipBar = new HBox();
            for (Card c : card.getStartingCards()) {
                c.setVisible(true);
                CardView cardView = new CardView(c, cssClass) {
                    @Override
                    public void update(Observable o, Object arg) {

                    }

                };

                tooltipBar.getChildren()
                        .add(cardView);
            }
            tooltip.setGraphic(tooltipBar);
            Tooltip.install(this, tooltip);

        }

        setOnMouseClicked(event -> {
            try {
                if (partySelection.getUniqueIds()
                        .contains(card.getUniqueId())) {
                    Logger.info("deck already contains a " + card.getUniqueId() + " card");
                    return;
                }


                if (target.size() < 4) {
                    if (target.size() == 3) {
                        //partyDone.setDisable(false);
                    } else {
                        //partyDone.setDisable(true);
                    }
                    partySelection.getUniqueIds()
                            .add(card.getUniqueId());
                    Card cardtToAdd = card.getClass()
                            .newInstance();
                    cardtToAdd.setOwner(GameEngine.getInstance().getPlayer());
                    target.add(cardtToAdd);
                } else {
                    Logger.info("party already has four members");
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
