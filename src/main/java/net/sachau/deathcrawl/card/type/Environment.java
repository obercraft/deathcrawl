package net.sachau.deathcrawl.card.type;

import javafx.beans.property.SimpleIntegerProperty;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.ThreatListener;

public class Environment extends Card {

    private SimpleIntegerProperty threat = new SimpleIntegerProperty(1);

    public Environment() {
        super();
        threat.addListener(new ThreatListener(this));
    }

    public Environment(Environment card) {
        super(card);
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

}
