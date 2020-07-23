package net.sachau.deathcrawl.momentum;

import net.sachau.deathcrawl.cards.Card;

import java.util.HashSet;

public class MomentumActions extends HashSet<MomentumAction> {

    public MomentumActions() {
    }

    public static MomentumActions builder() {
        return new MomentumActions();
    }

    public MomentumActions addAction(MomentumAction momentumAction) {
        this.add(momentumAction);
        return this;
    }
}
