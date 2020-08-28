package net.sachau.zarrax.card.effect;


import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.gui.images.Tile;

public abstract class CardEffect {

	private CardEffect sourceEffect;
	private Card sourceCard;
	private EffectTiming effectTiming;
	private long id;
	private Tile tile;
	private int amount = 1;
	private int ticks = 0;

	public CardEffect() {
		super();
		id = GameEngine.createId();
	}

	public boolean tick(Card card) {
		if (ticks > 0) {
			ticks --;
			return false;
		}
		end(card);
		return true;
	}

	abstract public void start(Card targetCard);
	abstract public void end(Card card);

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public long getId() {
		return id;
	}

	public int getTicks() {
		return ticks;
	}

	public void setTicks(int ticks) {
		this.ticks = ticks;
	}

	public EffectTiming getEffectTiming() {
		return effectTiming;
	}

	public void setEffectTiming(EffectTiming effectTiming) {
		this.effectTiming = effectTiming;
	}

	public Card getSourceCard() {
		return sourceCard;
	}

	public void setSourceCard(Card sourceCard) {
		this.sourceCard = sourceCard;
	}

//	@Override
//	public boolean equals(Object o) {
//		if (this == o) return true;
//		if (o == null || getClass() != o.getClass()) return false;
//		CardEffect effect = (CardEffect) o;
//		return Objects.equals(sourceCard, effect.sourceCard) &&
//				Objects.equals(effectTiming, effect.effectTiming);
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(sourceCard, effectTiming);
//	}

	public CardEffect getSourceEffect() {
		return sourceEffect;
	}

	public void setSourceEffect(CardEffect sourceEffect) {
		this.sourceEffect = sourceEffect;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		if (this instanceof KeywordEffect) {
			sb.append(((KeywordEffect) this).getKeyword().name());
		} else {
			sb.append(this.getClass().getSimpleName());
		}
		sb.append("]");
		return sb.toString();
	}
}
