package net.sachau.zarrax.card;

import net.sachau.zarrax.Logger;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.CopyOnWriteArrayList;

public class UniqueCardList extends CopyOnWriteArrayList<Card> {

    public boolean add(Card card) {
        if (card == null) {
            return false;
        }
        if (StringUtils.isEmpty(card.getUniqueId())) {
            super.add(card);
            return true;
        }
        for (Card c : this) {
            if (card.getUniqueId().equalsIgnoreCase(c.getUniqueId())) {
                Logger.debug("already contains a " + card.getUniqueId());
                return false;
            }
        }
        super.add(card);
        return true;
    }
}
