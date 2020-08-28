package net.sachau.zarrax.card.command.commands;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.command.*;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.util.CardUtils;

import java.util.List;

public class AddCard implements CardCommand {

    @Override
    public CommandResult isAllowed(Card sourceCard, Card targetCard) {
        return CommandStatus.build(CommandType.ACTION).check(sourceCard, targetCard);
    }

    @Override
    public CommandResult execute(Card sourceCard, Card targetCard, CommandParameter commandParameter) {
        List<Card> cards;
        if ("hand".equalsIgnoreCase(commandParameter.getString(0).trim())) {
            cards = GameEngine.getInstance().getPlayer().getHand();
        } else {
            cards = GameEngine.getInstance().getPlayer().getHazards();
        }
        Card card = Catalog.getInstance()
                .get(commandParameter.getString(1).trim());
        if (card != null && cards != null) {
            cards.add(CardUtils.copyCard(card));
            return CommandResult.successful();
        } else {
            return CommandResult.notAllowed("cannot ADD Card");
        }
    }

}
