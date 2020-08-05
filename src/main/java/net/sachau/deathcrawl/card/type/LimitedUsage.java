package net.sachau.deathcrawl.card.type;

import javafx.beans.property.SimpleIntegerProperty;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.command.CommandParser;

public class LimitedUsage extends Card {

    private static final int UNLIMITED =  -1;
    private SimpleIntegerProperty uses = new SimpleIntegerProperty();
    private String usageCommand;

    public LimitedUsage() {
        super();
    }

    public LimitedUsage(LimitedUsage card) {
        super(card);
    }

    public boolean execute(Card target) {
        if (getUses() > 0 || getUses() == UNLIMITED) {
            boolean result = CommandParser.executeCommands(usageCommand, this, target);
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

    public String getUsageCommand() {
        return usageCommand;
    }

    public void setUsageCommand(String usageCommand) {
        this.usageCommand = usageCommand;
    }
}
