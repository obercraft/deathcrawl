package net.sachau.zarrax.card.type;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.keyword.Keyword;

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