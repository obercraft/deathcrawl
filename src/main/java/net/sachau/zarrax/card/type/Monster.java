package net.sachau.zarrax.card.type;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.command.CommandStage;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.util.DiceUtils;

import java.util.List;

public class Monster extends Card {

    private int xp;
    private int gold;
    private int regenerate;

    private Card targetedCard;

    private List<CommandStage> commandStages;

    private List<String> randomCommands;

    public Monster() {
        addKeyword(Keyword.MONSTER);
        addKeyword(Keyword.CREATURE);
    }

    public Monster(Monster card) {
        super(card);
        addKeyword(Keyword.CREATURE);
        addKeyword(Keyword.MONSTER);
        this.xp = card.xp;
        this.gold = card.gold;
        this.regenerate = card.regenerate;
    }

    @Override
    public String getCommand() {
        if (randomCommands != null && randomCommands.size() > 0) {
            int index = DiceUtils.get(randomCommands.size());
            return randomCommands.get(index);
        } else if (commandStages != null && commandStages.size() > 0) {
            int currentHits = getHits();
            for (CommandStage commandStage : commandStages) {
                if (currentHits >= commandStage.getHits()) {
                    return commandStage.getCommand();
                }
            }
            return commandStages.get(0).getCommand();
        } else {
            return super.getCommand();
        }
    }


    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getRegenerate() {
        return regenerate;
    }

    public void setRegenerate(int regenerate) {
        this.regenerate = regenerate;
    }

    public Card getTargetedCard() {
        return targetedCard;
    }

    public void setTargetedCard(Card targetedCard) {
        this.targetedCard = targetedCard;
    }

    public List<CommandStage> getCommandStages() {
        return commandStages;
    }

    public void setCommandStages(List<CommandStage> commandStages) {
        this.commandStages = commandStages;
    }

    public List<String> getRandomCommands() {
        return randomCommands;
    }

    public void setRandomCommands(List<String> randomCommands) {
        this.randomCommands = randomCommands;
    }
}
