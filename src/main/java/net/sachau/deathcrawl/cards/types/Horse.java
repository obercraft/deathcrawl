package net.sachau.deathcrawl.cards.types;

import net.sachau.deathcrawl.Event;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.effects.CardEffect;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.keywords.Keyword;

public class Horse extends Card {

	public Horse() {
		super( 0, 1);
		init();
	}

	public Horse(Horse card) {
		super(card);
	}

	private void init() {
		addKeywords(Keyword.SIMPLE, Keyword.ITEM, Keyword.PERMANENT);

		setCommand("play_to_party");

		addEffect(Event.STARTTURN, new CardEffect() {
			@Override
			public void trigger(Card sourceCard, Card targetCard) {

				Player p = getPlayer();
				if (p != null) {
					int m = p.getMomentum() + 1;
					p.setMomentum(m);
				}
			}

			@Override
			public void remove(Card card) {

			}
		});


	}
}
