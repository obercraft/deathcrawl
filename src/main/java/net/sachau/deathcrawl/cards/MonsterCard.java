package net.sachau.deathcrawl.cards;

public abstract class MonsterCard extends Card {

    private int xp;
    private int gold;

    public MonsterCard(int initialHits, int initialDamage, int exp, int gold) {
        super(initialHits, initialDamage);
        this.gold = gold;
        this.xp = exp;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
