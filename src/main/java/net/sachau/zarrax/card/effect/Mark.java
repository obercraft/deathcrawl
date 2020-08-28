package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.gui.images.Tile;

public class Mark extends CardEffect {
    public Mark() {
        super();
        setTile(Tile.MARKED);
    }

    @Override
    public void start(Card targetCard) {
        int amount = 1;
        if (targetCard != null) {
            amount = Math.max(1, targetCard.getDamage());
        }
    }

    @Override
    public void end(Card card) {

    }
}
