package net.sachau.zarrax.card.type;

import net.sachau.zarrax.card.effect.EffectTiming;
import net.sachau.zarrax.card.effect.Riding;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.effect.CardEffect;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.card.keyword.Keyword;

public class Horse extends Card {

	public Horse() {
		super( 0, 1);
		init();
	}

	public Horse(Horse card) {
		super(card);
	}

	private void init() {
		addKeyword(Keyword.SIMPLE);
		addKeyword(Keyword.ITEM);
		addKeyword(Keyword.PERMANENT);

		setCommand("play_to_party");

		this.getEffects().add(new Riding());


	}
}
