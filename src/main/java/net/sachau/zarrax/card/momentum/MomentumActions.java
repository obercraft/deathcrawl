package net.sachau.zarrax.card.momentum;

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
