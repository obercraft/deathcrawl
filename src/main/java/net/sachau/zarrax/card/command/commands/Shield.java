package net.sachau.zarrax.card.command.commands;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.command.*;
import net.sachau.zarrax.card.effect.KeywordEffect;
import net.sachau.zarrax.card.keyword.Keyword;

public class Shield implements CardCommand {
    @Override
    public CommandResult isAllowed(Card sourceCard, Card targetCard) {
        return CommandStatus.build(CommandType.SPELL).notEnemy().check(sourceCard, targetCard);
    }

    @Override
    public CommandResult execute(Card source, Card target, CommandParameter commandParameter) {
        if (target != null && target.getKeywords()
                .contains(Keyword.CREATURE)) {
            target.getEffects().add(new KeywordEffect(Keyword.ARMOR, target));
        }
        return CommandResult.successful();

    }
}
