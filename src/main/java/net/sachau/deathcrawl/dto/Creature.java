package net.sachau.deathcrawl.dto;

import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import net.sachau.deathcrawl.effects.CardEffect;

import java.util.HashSet;

public abstract class Creature {

    private SimpleIntegerProperty xp = new SimpleIntegerProperty();
    private SimpleIntegerProperty gold = new SimpleIntegerProperty();

    private SetProperty<CardEffect> conditions;

    public Creature() {
        super();
        ObservableSet<CardEffect> observableSet = FXCollections.observableSet(new HashSet<>());
        this.conditions = new SimpleSetProperty<>(observableSet);
    }


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
