package net.sachau.zarrax.card.command.commands;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.command.*;
import net.sachau.zarrax.card.effect.KeywordEffect;
import net.sachau.zarrax.card.keyword.Keyword;

public class Exhaust implements CardCommand {
    @Override
    public CommandResult isAllowed(Card sourceCard, Card targetCard) {
        return CommandResult.successful();
    }

    @Override
    public CommandResult execute(Card sourceCard, Card targetCard, CommandParameter commandParameter) {
        sourceCard.getEffects().add(new KeywordEffect(Keyword.EXHAUSTED));
        return CommandResult.successful();
    }
}
