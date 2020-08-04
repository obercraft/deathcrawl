package net.sachau.deathcrawl.card.keyword;

public enum Keyword {
    //BASIC, // these are allowed in the starting deck
    CASTER,

    FIGHTER(true),
    ROGUE(true),
    WIZARD(true),
    CLERIC(true),
    PALADIN(true),

    SIMPLE, // simple cards can be played by all classes
    ITEM (true),
    WEAPON (true),
    POTION (true),
    SPELL (true),
    MONSTER, CREATURE, ACTION, MOMENTUM,
    RANGED(true), PERMANENT(), GOBLIN();

    private boolean onCard;

    Keyword(boolean onCard) {
        this.onCard = onCard;
    }

    Keyword() {
    }

    public boolean isOnCard() {
        return onCard;
    }

    public void setOnCard(boolean onCard) {
        this.onCard = onCard;
    }
}
