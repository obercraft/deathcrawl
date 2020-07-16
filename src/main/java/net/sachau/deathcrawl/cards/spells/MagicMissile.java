package net.sachau.deathcrawl.cards.spells;

import net.sachau.deathcrawl.cards.Basic;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

@Basic
public class MagicMissile extends Card {

	public MagicMissile() {
		super("Magic Missile", 0, 1);
		setText("Magic Missile");
		setCommand("random_attack_many 3 1");
		addKeywords(Keyword.WIZARD, Keyword.BASIC);

	}


}

