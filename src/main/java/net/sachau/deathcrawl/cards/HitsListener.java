package net.sachau.deathcrawl.cards;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import net.sachau.deathcrawl.events.Event;
import net.sachau.deathcrawl.GameEngine;
import net.sachau.deathcrawl.events.GameEvent;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.cards.types.Monster;
import net.sachau.deathcrawl.dto.Player;

public class HitsListener implements ChangeListener<Number> {

    private Card card;

    public HitsListener(Card card) {
        this.card = card;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if (newValue != null && newValue.intValue() <= 0) {

            Logger.debug(card + " killed");

            if (card instanceof Monster) {
                Player player = GameEngine.getInstance().getPlayer();
                Monster mc = (Monster) card;
                int gold = mc.getGold() + player.getGold();
                player.setGold(gold);
                int xp = mc.getXp() + player.getXp();
                player.setXp(xp);

                Logger.debug(player + " gains " + gold + " gold & " + xp + " XP");
            }
            GameEvent.getInstance().send(new Event(Event.Type.CHARACTERDEATH, card));
        }

    }
}
