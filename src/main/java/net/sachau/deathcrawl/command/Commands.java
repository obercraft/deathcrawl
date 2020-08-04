package net.sachau.deathcrawl.command;

public enum Commands {
    ATTACK (CommandType.ATTACK),
    DRAW (CommandType.ACTION),
    GOLD (CommandType.ACTION),
    RANDOM_ATTACK (CommandType.ATTACK),
    SHIELD (CommandType.ACTION),
    HEAL (CommandType.SPELL),
    POISON_ITEM  (CommandType.ACTION),
    MOMENTUM  (CommandType.ACTION),
    PLAY_TO_PARTY  (CommandType.ACTION),
    PRONE (CommandType.ACTION),
    EXHAUST (CommandType.ACTION),
    EXHAUST_RANDOM (CommandType.ACTION),
    ADD_CARD (CommandType.ACTION),
    ;
    private CommandType type;

    Commands(CommandType type) {
        this.type = type;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }
}
