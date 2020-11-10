package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.v2.GEngine;
import net.sachau.zarrax.v2.GState;

import java.util.List;

public class Guard extends CardEffect {

    private GState state = GEngine.getBean(GState.class);

    public Guard() {
        super();
        this.setEffectTiming(new EffectTiming(GameEventContainer.Type.START_ENCOUNTER, GameEventContainer.Type.END_ENCOUNTER));
    }

    @Override
    public void start(Card targetCard) {
        Logger.debug(targetCard + "triggers Guard");
        Player player = state.getPlayer();
        List<Card> cards = targetCard.getOwner() instanceof Player ? player.getParty() : player.getHazards();

        for (Card card : cards) {
            if (card.getId() != targetCard.getId()) {
                card.getEffects()
                        .add(new KeywordEffect(Keyword.GUARDED, targetCard, this));
            }
        }

    }

    @Override
    public void end(Card targetCard) {
        Player player = state.getPlayer();
        List<Card> cards = targetCard.getOwner() instanceof Player ? player.getParty() : player.getHazards();
        for (Card card : cards) {
            if (card.getId() != targetCard.getId()) {
             card.removeEffectWithSourceId(this.getId());
            }
        }

    }

}
