package net.sachau.zarrax.card.command;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.effect.Prone;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.card.keyword.Keywords;

public class CommandStatus {

    private CommandType type;
    private boolean prone = true;
    private boolean targetFriendly = true;
    private boolean targetSelf = true;
    private boolean targetEnemy = true;
    // null means all
    private Keywords allowedKeywords = null;
    
    private CommandStatus(CommandType commandType) {
        this.type = commandType;
    }
    
    public static CommandStatus build(CommandType commandType) {
        return new CommandStatus(commandType);
    }

    public boolean check(Card source, Card target) {
        if (!prone && source.hasCondition(Prone.class)) {
            Logger.debug(source + " is prone");
            return false;
        }
        if (!targetSelf && source == target) {
            Logger.debug(source + " not allowed to target self");
            return false;
        }
        if (!targetFriendly &&  target != null && target.getOwner() == source.getOwner()) {
            Logger.debug(source + " not allowed to target friendly card " + target);
            return false;
        }

        if (!targetEnemy && target != null && target.getOwner() != source.getOwner()) {
            Logger.debug(source + " not allowed to target enemy card " + target);
            return false;
        }

        if (allowedKeywords != null && !target.hasAllKeywords(allowedKeywords)) {
            Logger.debug(source + " must have " + allowedKeywords);
            return false;
        }
        return true;

    }

    public CommandStatus setProne(boolean prone) {
        this.prone = prone;
        return this;
    }

    public CommandStatus setTargetFriendly(boolean targetFriendly) {
        this.targetFriendly = targetFriendly;
        return this;
    }

    public CommandStatus setTargetSelf(boolean targetSelf) {
        this.targetSelf = targetSelf;
        return this;
    }

    public boolean isProne() {
        return prone;
    }

    public boolean isTargetFriendly() {
        return targetFriendly;
    }

    public boolean isTargetSelf() {
        return targetSelf;
    }

    public CommandStatus notProne() {
        return setProne(false);
    }

    public CommandStatus notSelf() {
        return setTargetSelf(false);
    }

    public CommandStatus notFriendly() {
        return setTargetFriendly(false);
    }

    public CommandType getType() {
        return type;
    }

    public CommandStatus setType(CommandType type) {
        this.type = type;
        return this;
    }

    public boolean isTargetEnemy() {
        return targetEnemy;
    }

    public CommandStatus setTargetEnemy(boolean targetEnemy) {
        this.targetEnemy = targetEnemy;
        return this;
    }

    public CommandStatus notEnemy() {
        return setTargetEnemy(false);
    }

    public Keywords getAllowedKeywords() {
        return allowedKeywords;
    }

    public CommandStatus setAllowedKeywords(Keywords allowedKeywords) {
        this.allowedKeywords = allowedKeywords;
        return this;
    }

    public CommandStatus onlyKeywords(Keyword...keywords) {
        return setAllowedKeywords(Keywords.build(keywords));
    }
}
