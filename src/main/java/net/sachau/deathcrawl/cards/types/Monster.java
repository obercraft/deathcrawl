package net.sachau.deathcrawl.cards.types;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

public class Monster extends Card {

    private int xp;
    private int gold;

    public Monster() {
        addKeywords(Keyword.CREATURE);
    }

    public Monster(Monster card) {
        super(card);
        addKeywords(Keyword.CREATURE);
        this.xp = card.xp;
        this.gold = card.gold;
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
