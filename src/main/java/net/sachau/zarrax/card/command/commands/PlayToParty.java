package net.sachau.zarrax.card.command.commands;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.command.*;
import net.sachau.zarrax.card.effect.KeywordEffect;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.engine.Player;

public class PlayToParty implements CardCommand {
    @Override
    public CommandResult isAllowed(Card sourceCard, Card targetCard) {
        return CommandStatus.build(CommandType.ACTION).check(sourceCard, targetCard);
    }

    @Override
    public CommandResult execute(Card source, Card targetCard, CommandParameter commandParameter) {
        Player p = source.getPlayer();
        if (p != null) {
            source.getEffects().add(new KeywordEffect(Keyword.PERMANENT));
            p.getParty()
                    .add(source);
            //source.getDeck().remove(source);
            return CommandResult.successful();
        }
        return CommandResult.notAllowed("player is null");
    }
}
