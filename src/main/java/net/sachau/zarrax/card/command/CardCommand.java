package net.sachau.zarrax.card.command;

import net.sachau.zarrax.card.Card;

import java.util.List;

public interface CardCommand {

    CommandResult isAllowed(Card sourceCard, Card targetCard);
    CommandResult execute( Card sourceCard, Card targetCard, CommandParameter commandParameter);

}
