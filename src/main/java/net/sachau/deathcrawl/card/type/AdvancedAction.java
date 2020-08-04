package net.sachau.deathcrawl.card.type;

import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.keyword.Keyword;

public abstract class AdvancedAction extends Action {

    public AdvancedAction() {
        super();
        addKeywords(Keyword.ACTION);
    }

    public AdvancedAction(AdvancedAction action) {
        super(action);
    }

    public abstract boolean execute(Card targetCard);
}
