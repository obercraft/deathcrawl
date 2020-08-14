package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.gui.images.Tile;

public class Armor extends CardEffect {
    public Armor() {
        super();
        this.setTile(Tile.ARMOR);
    }

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
        targetCard.getConditions().add(new Armor());
    }

    @Override
    public void remove(Card card) {

    }
}
