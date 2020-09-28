package net.sachau.zarrax.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import net.sachau.zarrax.engine.*;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.type.Monster;

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
                Player player = ApplicationContext.getPlayer();
                Monster mc = (Monster) card;
                int gold = mc.getGold() + player.getGold();
                player.setGold(gold);
                int xp = mc.getXp() + player.getXp();
                player.setXp(xp);

                Logger.debug(player + " gains " + gold + " gold & " + xp + " XP");
            }
            GameEvent.getInstance().send(new GameEventContainer(GameEventContainer.Type.CHARACTERDEATH, card));
        }

    }
}
