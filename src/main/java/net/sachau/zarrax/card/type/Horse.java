package net.sachau.zarrax.card.type;

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
		addKeywords(Keyword.SIMPLE, Keyword.ITEM, Keyword.PERMANENT);

		setCommand("play_to_party");

		addEffect(GameEventContainer.Type.STARTTURN, new CardEffect() {
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
