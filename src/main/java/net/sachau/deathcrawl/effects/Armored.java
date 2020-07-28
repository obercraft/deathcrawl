package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.gui.images.Tile;

public class Armored extends CardEffect {
    public Armored() {
        super();
        this.setTile(Tile.ARMOR);
    }

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
        targetCard.getConditions().add(new Armored());
    }

    @Override
    public void remove(Card card) {

    }
}
