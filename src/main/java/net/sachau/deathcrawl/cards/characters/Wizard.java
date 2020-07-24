package net.sachau.deathcrawl.cards.characters;

import net.sachau.deathcrawl.cards.CharacterCard;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.items.Knife;
import net.sachau.deathcrawl.cards.spells.MagicMissile;
import net.sachau.deathcrawl.keywords.Keyword;
import net.sachau.deathcrawl.momentum.MomentumActions;

@StartingCharacter
public class Wizard extends CharacterCard {

    public Wizard() {
        super("Wizard", 6, 0, "Wizard", Deck.builder()
                .add(Knife.class, Knife.class, MagicMissile.class), MomentumActions.builder());
        addKeywords(Keyword.CREATURE, Keyword.CASTER, Keyword.WIZARD);
    }
}

