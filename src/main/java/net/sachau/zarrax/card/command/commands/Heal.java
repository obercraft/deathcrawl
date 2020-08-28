package net.sachau.zarrax.card.command.commands;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.command.*;
import net.sachau.zarrax.util.CardUtils;

import java.util.ArrayList;
import java.util.List;

public class Heal implements CardCommand {

    @Override
    public CommandResult isAllowed(Card sourceCard, Card targetCard) {
        return CommandStatus.build(CommandType.SPELL).notEnemy().check(sourceCard, targetCard);
    }

    @Override
    public CommandResult execute(Card source, Card target, CommandParameter commandParameter) {
        int count = commandParameter.getInt(0, 1);

        List<Card> targets = CardUtils.getPossibleFriendlyTargets(source, target, commandParameter.getTarget(), count);
        List<CommandResult> result = new ArrayList<>();
        if (target != null) {
            for (Card t : targets) {
                result.add(source.heal(t, source.getDamage()));
            }
        }
        return CommandResult.build(result);

    }
}
