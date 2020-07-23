package net.sachau.deathcrawl.cards.classes;

import net.sachau.deathcrawl.cards.CharacterCard;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.actions.Momentum;
import net.sachau.deathcrawl.cards.items.Knife;
import net.sachau.deathcrawl.keywords.Keyword;
import net.sachau.deathcrawl.momentum.MomentumActions;

@StartingCharacter
public class Wizard extends CharacterCard {

    public Wizard() {
        super("Wizard", 6, 0, "Wizard", Deck.builder()
                .add(Knife.class, Momentum.class, Momentum.class), MomentumActions.builder());
        addKeywords(Keyword.CREATURE, Keyword.CASTER, Keyword.WIZARD);
    }
}

