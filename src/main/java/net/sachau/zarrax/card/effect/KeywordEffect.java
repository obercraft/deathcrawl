package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.keyword.Keyword;


public class KeywordEffect extends CardEffect {

    private Keyword keyword;

    public KeywordEffect(Keyword keyword) {
        this.keyword = keyword;
    }

    public KeywordEffect(Keyword keyword, Card sourceCard) {
        this.keyword = keyword;
        this.setSourceCard(sourceCard);
    }

    public KeywordEffect(Keyword keyword, Card sourceCard, CardEffect sourceEffect) {
        this.keyword = keyword;
        this.setSourceCard(sourceCard);
        this.setSourceEffect(sourceEffect);
    }


    public KeywordEffect() {
        super();
    }

    @Override
    public void start(Card targetCard) {
        this.setSourceCard(targetCard);
        targetCard.getEffects().add(this);
    }

    @Override
    public void end(Card card) {
        card.getKeywords().remove(this);
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }
}
