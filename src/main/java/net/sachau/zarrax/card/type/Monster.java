package net.sachau.zarrax.card.type;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.keyword.Keyword;

public class Monster extends Card {

    private int xp;
    private int gold;
    private int regenerate;

    private Card targetedCard;

    public Monster() {
        addKeywords(Keyword.CREATURE);
    }

    public Monster(Monster card) {
        super(card);
        addKeywords(Keyword.CREATURE, Keyword.MONSTER);
        this.xp = card.xp;
        this.gold = card.gold;
        this.regenerate = card.regenerate;
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

    public int getRegenerate() {
        return regenerate;
    }

    public void setRegenerate(int regenerate) {
        this.regenerate = regenerate;
    }

    public Card getTargetedCard() {
        return targetedCard;
    }

    public void setTargetedCard(Card targetedCard) {
        this.targetedCard = targetedCard;
    }
}
