package net.sachau.zarrax.card.type;

import javafx.beans.property.SimpleIntegerProperty;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.command.Command;
import net.sachau.zarrax.card.command.CommandParser;
import net.sachau.zarrax.card.command.CommandResult;
import net.sachau.zarrax.engine.ApplicationContext;

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

    public CommandResult execute(Card target) {

        if (usageCard == null) {
            Card card = ApplicationContext.getCatalog().copyOf(this.getUsageCardName());
            card.setOwner(this.getOwner());
            card.setVisible(true);
            this.usageCard = card;
        }

        if (getUses() > 0 || getUses() == UNLIMITED) {
            CommandResult result = Command.execute(usageCard, target);
            if (result.isSuccessful()) {
                if (getUses() != UNLIMITED) {
                    setUses(getUses() - 1);
                }
            }
            return result;
        } else {
            return CommandResult.notAllowed("not enough uses=" + getUses());
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
