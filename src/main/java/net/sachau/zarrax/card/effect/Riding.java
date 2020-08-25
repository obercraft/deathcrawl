package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.engine.Player;

public class Riding extends CardEffect {

    public Riding() {
        this.setEffectTiming(new EffectTiming(GameEventContainer.Type.STARTTURN, GameEventContainer.Type.ENDTURN));
    }

    @Override
    public void trigger(Card targetCard) {
        Player p = (Player) targetCard.getOwner();
        if (p != null) {
            int m = p.getMoves() + 1;
            p.setMoves(m);
        }
    }

    @Override
    public void remove(Card card) {
        Player p = (Player) card.getOwner();
        if (p != null) {
            int m = p.getMoves() - 1;
            p.setMoves(m);
        }

    }
}
