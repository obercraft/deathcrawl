package net.sachau.zarrax.card.command.commands;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.command.*;
import net.sachau.zarrax.card.effect.Guard;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.card.type.Illumination;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.util.CardUtils;
import net.sachau.zarrax.util.DiceUtils;
import net.sachau.zarrax.v2.GEngine;
import net.sachau.zarrax.v2.GState;

import java.util.ArrayList;
import java.util.List;

public class Attack implements CardCommand {

    @Override
    public CommandResult isAllowed(Card sourceCard, Card targetCard) {
        return CommandStatus.build(CommandType.ATTACK).notProne().notSelf().notFriendly().check(sourceCard, targetCard);
    }

    public CommandResult execute( Card source, Card target, CommandParameter commandParameter) {
        if (target == null) {
            return CommandResult.notAllowed("target is null");
        }

        int count = commandParameter.getInt(0, 1);
        List<Card> targets = CardUtils.getPossibleTargets(source, target, commandParameter.getTarget(), count);
        if (targets.size() == 0) {
            return CommandResult.notAllowed(source +  " has no targets");
        }


        List<CommandResult> result = new ArrayList<>();
        for (Card t : targets) {
            result.add(source.attack(t, source.getDamage()));

        }
        return CommandResult.build(result);
    }

    public CommandResult attack(Card source, Card target, int attackValue) {
        return attack(source, target, attackValue, false);
    }
    private  CommandResult attack(Card source, Card target, int attackValue, boolean isRetaliate) {

        if (target == null) {
            return CommandResult.notAllowed("target is null");
        }

        if (source.hasKeyword(Keyword.PRONE)) {
            return CommandResult.notAllowed(this + " is prone");
        }

        if (attackValue <= 0) {
            return CommandResult.notAllowed("damage is less than 1");
        }


        boolean ranged = source.hasKeyword(Keyword.RANGED);

        GState state = GEngine.getBean(GState.class);
        if (ranged) {
            int rangeHitChance = 100; // percent
            if (target.hasKeyword(Keyword.SMALL)) {
                rangeHitChance -= 25;
            }
            boolean hasBadLight = false;
            if (source.hasKeyword(Keyword.BLIND)) {
                rangeHitChance -= 60;
                hasBadLight = true;
            } else if (source.hasKeyword(Keyword.DARKNESS)) {
                rangeHitChance -= 30;
                hasBadLight = true;
            }

            if (hasBadLight) {
                Player player = state.getPlayer();
                if (source.getOwner() instanceof Player) {

                    for (Card c : player.getParty()) {
                        if (c instanceof Illumination) {
                            rangeHitChance += ((Illumination) c).getIlluminationValue();
                        }
                    }
                } else {
                    for (Card c : player.getHazards()) {
                        if (c instanceof Illumination) {
                            rangeHitChance += ((Illumination) c).getIlluminationValue();
                        }
                    }

                }
            }

            int hitChance = Math.min(100, rangeHitChance);
            if (!DiceUtils.percentage(hitChance)) {
                return CommandResult.notSuccessful("ranged " + this + "attack on "+ target + " failed", hitChance);
            }
        }

        if (target.hasKeyword(Keyword.FLYING) && !ranged) {
            return CommandResult.notAllowed("cannot attack flying target with ranged");
        }

        // not ranged and target is not a guard and target and target is guarded
        if (!ranged && !target.hasEffect(Guard.class) && target.hasKeyword(Keyword.GUARDED)) {
            return CommandResult.notAllowed(target + " is guarded");
        }


//
//            List<Card> possibleGuards;
//            if (target.getOwner() instanceof Player) {
//                possibleGuards = ((Player) target.getOwner()).getParty();
//            } else {
//                possibleGuards = GameEngine.getInstance()
//                        .getPlayer()
//                        .getHazards();
//            }
//
//            if (possibleGuards != null) {
//                for (Card guard : possibleGuards) {
//                    if (guard.isAlive() && guard.getId() != target.getId() && guard.hasKeyword(Keyword.GUARDED)) {
//                        Logger.debug(target + " is guarded by " + guard);
//                        return false;
//                    }
//                }
//            }
//        }

        if (target.hasKeyword(Keyword.ARMOR)) {
            target.removeKeyword(Keyword.ARMOR);
            return CommandResult.successful(target + " saved by armor");
        }

        int hits = target.getHits();

        if (attackValue > 0) {
            hits -= attackValue;
        }

        target.setHits(Math.max(0,hits));
        Logger.debug(this + (isRetaliate ? " retaliates " : " attacks " ) + target + " for " + attackValue + " damage");
        if (target.hasKeyword(Keyword.RETALIATE)) {

            Card retaliateTarget = state.getCurrentCard();
            if (retaliateTarget != null && retaliateTarget.hasKeyword(Keyword.CREATURE)) {
                attack(target, retaliateTarget, target.getDamage(), true);
            } else {
                Logger.debug("cannot retaliate " + retaliateTarget);
            }
        }
        return CommandResult.successful();

    }

}
