package net.sachau.deathcrawl.cards.types;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import net.sachau.deathcrawl.GameEngine;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.ThreatListener;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.effects.CardEffect;
import net.sachau.deathcrawl.events.Event;

public class Environment extends Card implements AdvancedAction {

    private SimpleIntegerProperty threat = new SimpleIntegerProperty(1);

    public Environment() {
        super();
        threat.addListener(new ThreatListener(this));
    }

    public Environment(Environment card) {
        super();
        this.setThreat(card.getThreat());
        threat.addListener(new ThreatListener(card));
    }


    public int getThreat() {
        return threat.get();
    }

    public SimpleIntegerProperty threatProperty() {
        return threat;
    }

    public void setThreat(int threat) {
        this.threat.set(threat);
    }

    @Override
    public boolean execute() {

        return false;
    }
}
