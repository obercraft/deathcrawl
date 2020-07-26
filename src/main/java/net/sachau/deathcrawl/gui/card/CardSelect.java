package net.sachau.deathcrawl.gui.card;

import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.types.Character;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.gui.PartySelection;

import java.util.Observable;

public class CardSelect extends CardView {


    public CardSelect(PartySelection partySelection, Character card, Deck target) {
        super(card);


        if (card.getStartingCards() != null) {
            Tooltip tooltip = new Tooltip();
            HBox tooltipBar = new HBox();
            for (Card c : card.getStartingCards()
                    .getCards()) {
                c.setVisible(true);
                CardView cardView = new CardView(c) {
                    @Override
                    public void update(Observable o, Object arg) {

                    }

                    @Override
                    public Card getCard() {
                        return super.getCard();
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
                    cardtToAdd.setOwner(partySelection.getPlayer());
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
