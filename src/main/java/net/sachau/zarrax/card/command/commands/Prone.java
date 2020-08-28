package net.sachau.zarrax.card.command.commands;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.command.*;
import net.sachau.zarrax.card.effect.KeywordEffect;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.util.CardUtils;

import java.util.List;

public class Prone implements CardCommand {

    @Override
    public CommandResult isAllowed(Card sourceCard, Card targetCard) {
        return CommandStatus.build(CommandType.ATTACK).notSelf().notFriendly().notProne().check(sourceCard, targetCard);
    }

    @Override
    public CommandResult execute(Card source, Card target, CommandParameter commandParameter) {
        int count = commandParameter.getInt(0, 1);
        List<Card> targets = CardUtils.getPossibleTargets(source, target, commandParameter.getTarget(), count);
        if (targets.size() == 0) {
            return CommandResult.notAllowed(source + " has no targets");

        }
        for (Card t : targets) {
            t.getEffects().add(new KeywordEffect(Keyword.PRONE));
            Logger.debug(source + " prones" + t);
        }
        return CommandResult.successful();
    }
}
