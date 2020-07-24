package net.sachau.deathcrawl.cards.items;

import javafx.scene.effect.Effect;
import net.sachau.deathcrawl.Event;
import net.sachau.deathcrawl.cards.Basic;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.CardEffect;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.keywords.Keyword;

@Basic
public class Horse extends Card {

	public Horse() {
		super("Horse", 0, 1);
		addKeywords(Keyword.SIMPLE, Keyword.ITEM, Keyword.PERMANENT);

		setCommand("play_to_party");

		addEffect(Event.STARTTURN, new CardEffect() {
			@Override
			public void trigger(Card targetCard) {

				Player p = getPlayer();
				if (p != null) {
					int m = p.getMomentum() + 1;
					p.setMomentum(m);
				}
			}
		});

	}

}
