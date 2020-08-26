package net.sachau.zarrax.card.command;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.keyword.Keyword;

import java.util.LinkedList;
import java.util.List;

public class CommandStatus {

    private CommandType type;
    private boolean prone = true;
    private boolean targetFriendly = true;
    private boolean targetSelf = true;
    private boolean targetEnemy = true;
    // null means all
    private List<Keyword> allowedKeywords = null;
    
    private CommandStatus(CommandType commandType) {
        this.type = commandType;
    }
    
    public static CommandStatus build(CommandType commandType) {
        return new CommandStatus(commandType);
    }

    public CommandResult check(Card source, Card target) {
        if (!prone && source.hasKeyword(Keyword.PRONE)) {
            return CommandResult.notAllowed(source + " is prone");
        }
        if (!targetSelf && source == target) {
            return CommandResult.notAllowed(source + " not allowed to target self");
        }
        if (!targetFriendly &&  target != null && target.getOwner() == source.getOwner()) {
            return CommandResult.notAllowed(source + " not allowed to target friendly card " + target);
        }

        if (!targetEnemy && target != null && target.getOwner() != source.getOwner()) {
            return CommandResult.notAllowed(source + " not allowed to target enemy card " + target);
        }

        if (allowedKeywords != null && target != null && !target.hasAllKeywords(allowedKeywords)) {
            return CommandResult.notAllowed(source + " must have " + allowedKeywords);
        }
        return CommandResult.allowed();

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


    public CommandStatus setAllowedKeywords(List<Keyword> allowedKeywords) {
        this.allowedKeywords = allowedKeywords;
        return this;
    }

    public CommandStatus onlyKeywords(Keyword...keywords) {
        if (keywords != null && keywords.length > 0) {
            this.allowedKeywords = new LinkedList<>();
            for (Keyword k : keywords) {
                this.allowedKeywords.add(k);
            }
        }
        return this;
    }
}
