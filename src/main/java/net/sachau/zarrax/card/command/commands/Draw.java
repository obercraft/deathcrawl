package net.sachau.zarrax.card.command.commands;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.command.*;
import net.sachau.zarrax.engine.Player;

public class Draw implements CardCommand {

    @Override
    public CommandResult isAllowed(Card sourceCard, Card targetCard) {
        return CommandStatus.build(CommandType.ACTION).check(sourceCard, targetCard);
    }

    public CommandResult execute( Card sourceCard, Card targetCard, CommandParameter commandParameter) {
        int amount = commandParameter.getInt(0, 1);
        Player p = sourceCard.getPlayer();
        if (p != null) {
            int max = Math.min(amount, p.getDraw().size() + p.getDraw().getDiscards().size());
            for (int i = 0; i< max; i++) {
                Card card = p.draw();
                p.addCardToHand(card);
            }
        }
        return CommandResult.successful();
    }
}
