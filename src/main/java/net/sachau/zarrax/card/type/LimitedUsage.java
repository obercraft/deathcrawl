package net.sachau.zarrax.card.type;

import javafx.beans.property.SimpleIntegerProperty;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.command.Command;
import net.sachau.zarrax.card.command.CommandParser;

public class LimitedUsage extends Card {

    private static final int UNLIMITED =  -1;
    private SimpleIntegerProperty uses = new SimpleIntegerProperty();
    private String usageCardName;
    private Card usageCard;

    public LimitedUsage() {
        super();
    }

    public LimitedUsage(LimitedUsage card) {
        super(card);
        this.uses.set(card.getUses());
        this.usageCardName = card.getUsageCardName();
        this.usageCard = card.getUsageCard();
    }

    public boolean execute(Card target) {

        if (usageCard == null) {
            Card card = Catalog.copyOf(this.getUsageCardName());
            card.setOwner(this.getOwner());
            card.setVisible(true);
            this.usageCard = card;
        }

        if (getUses() > 0 || getUses() == UNLIMITED) {
            boolean result = Command.execute(usageCard, target);
            if (result) {
                if (getUses() != UNLIMITED) {
                    setUses(getUses() - 1);
                }
            }
            return result;
        } else {
            Logger.debug("not enough uses=" + getUses());
            return false;
        }
    }

    public int getUses() {
        return uses.get();
    }

    public SimpleIntegerProperty usesProperty() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses.set(uses);
    }

    public Card getUsageCard() {
        return usageCard;
    }

    public void setUsageCard(Card usageCard) {
        this.usageCard = usageCard;
    }

    public String getUsageCardName() {
        return usageCardName;
    }

    public void setUsageCardName(String usageCardName) {
        this.usageCardName = usageCardName;
    }
}
