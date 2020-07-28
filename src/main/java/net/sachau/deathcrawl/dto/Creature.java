package net.sachau.deathcrawl.dto;

import javafx.beans.property.SimpleIntegerProperty;
import net.sachau.deathcrawl.keywords.Keyword;

import java.util.Set;

public abstract class Creature {

    private SimpleIntegerProperty xp = new SimpleIntegerProperty();
    private SimpleIntegerProperty gold = new SimpleIntegerProperty();

    public int getXp() {
        return xp.get();
    }

    public SimpleIntegerProperty xpProperty() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp.set(xp);
    }

    public int getGold() {
        return gold.get();
    }

    public SimpleIntegerProperty goldProperty() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold.set(gold);
    }
}
