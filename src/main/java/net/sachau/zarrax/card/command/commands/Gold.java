package net.sachau.zarrax.card.command.commands;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.command.*;
import net.sachau.zarrax.engine.Player;

public class Gold implements CardCommand {
    @Override
    public CommandResult isAllowed(Card sourceCard, Card targetCard) {
        return CommandStatus.build(CommandType.ACTION).check(sourceCard, targetCard);
    }

    @Override
    public CommandResult execute(Card source, Card targetCard, CommandParameter commandParameter) {
        Player p = source.getPlayer();
        if (p != null) {
            int g = p.getGold() + 1;
            p.setGold(g);
        }
        return CommandResult.successful();
    }
}
