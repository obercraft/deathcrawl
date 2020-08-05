package net.sachau.deathcrawl.command;

import net.sachau.deathcrawl.card.keyword.Keyword;

public enum Commands {
    ATTACK (CommandStatus.build(CommandType.ATTACK).notProne().notSelf().notFriendly()),
    DRAW (CommandStatus.build(CommandType.ACTION)),
    GOLD (CommandStatus.build(CommandType.ACTION)),
    RANDOM_ATTACK (CommandStatus.build(CommandType.ATTACK).notProne().notSelf().notFriendly()),
    SHIELD (CommandStatus.build(CommandType.SPELL).notEnemy()),
    HEAL (CommandStatus.build(CommandType.SPELL).notEnemy()),
    POISON_ITEM  (CommandStatus.build(CommandType.ACTION).notEnemy().notProne().onlyKeywords(Keyword.ITEM)),
    MOMENTUM  (CommandStatus.build(CommandType.ACTION)),
    PLAY_TO_PARTY  (CommandStatus.build(CommandType.ACTION)),
    PRONE (CommandStatus.build(CommandType.ATTACK).notSelf().notFriendly().notProne()),
    EXHAUST (CommandStatus.build(CommandType.ATTACK).notSelf().notFriendly()),
    EXHAUST_RANDOM (CommandStatus.build(CommandType.ATTACK).notSelf().notFriendly()),
    ADD_CARD (CommandStatus.build(CommandType.ACTION)),
    ;
    private CommandStatus status;

    Commands(CommandStatus status) {
        this.status = status;
    }

    public CommandStatus getStatus() {
        return status;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }
}
