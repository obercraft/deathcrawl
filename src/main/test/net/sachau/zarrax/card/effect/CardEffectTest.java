package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.command.Command;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.card.type.Horse;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.engine.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CardEffectTest {

    Player player;

    @Before
    public void init() {
        player = new Player();
        GameEngine.getInstance()
                .setPlayer(player);
        GameEngine.getInstance()
                .setInitiativeOrder(new ArrayList<>());
        Catalog.initForTesting();


    }

    @Test
    public void keyword() {

        Card testCard = new Card() {

        };
        testCard.getEffects().add(new KeywordEffect(Keyword.SIMPLE));
        Assert.assertTrue(testCard.hasKeyword(Keyword.SIMPLE));

        testCard.removeKeyword(Keyword.SIMPLE);
        Assert.assertFalse(testCard.hasKeyword(Keyword.SIMPLE));


        CardEffect effect = new CardEffect() {
            @Override
            public void trigger(Card targetCard) {
                targetCard.addKeyword(Keyword.SIMPLE);
            }

            @Override
            public void remove(Card card) {
                card.removeKeyword(Keyword.SIMPLE);
            }
        };
        EffectTiming timing = new EffectTiming(GameEventContainer.Type.STARTENCOUNTER, GameEventContainer.Type.ENDENCOUNTER);
        effect.setEffectTiming(timing);
        testCard.getEffects().add(effect);

        testCard.triggerStartEffects(GameEventContainer.Type.STARTENCOUNTER);
        Assert.assertTrue(testCard.hasKeyword(Keyword.SIMPLE));
        testCard.triggerEndEffects(GameEventContainer.Type.ENDENCOUNTER);
        Assert.assertFalse(testCard.hasKeyword(Keyword.SIMPLE));



    }

    @Test
    public void testGuard() {

        Card thief = Catalog.copyOf("thief");
        Card warrior = Catalog.copyOf("warrior");
        Card wizard = Catalog.copyOf("wizard");

        Assert.assertEquals(2, thief.getKeywords().size());
        Assert.assertTrue(thief.hasKeyword(Keyword.ROGUE));
        Assert.assertTrue(thief.hasKeyword(Keyword.CREATURE));

        player.addToParty(warrior);
        player.addToParty(thief);
        player.addToParty(wizard);

        GameEngine.getInstance().setCurrentInitiative(0); // warrior
        for (Card c : player.getParty()) {
            c.triggerStartEffects(GameEventContainer.Type.STARTENCOUNTER);
        }

        Assert.assertTrue(thief.hasKeyword(Keyword.GUARDED));
        Assert.assertTrue(wizard.hasKeyword(Keyword.GUARDED));
        Assert.assertFalse(warrior.hasKeyword(Keyword.GUARDED));

        int warriorEffects = warrior.getEffects()
                .size();

        for (Card c : player.getParty()) {
            c.triggerEndEffects(GameEventContainer.Type.ENDENCOUNTER);
        }
        Assert.assertFalse(thief.hasKeyword(Keyword.GUARDED));
        Assert.assertFalse(wizard.hasKeyword(Keyword.GUARDED));
        Assert.assertFalse(warrior.hasKeyword(Keyword.GUARDED));
        Assert.assertEquals(warriorEffects, warrior.getEffects().size());

    }

    @Test
    public void trigger() {

        Horse horse = new Horse();

        player.addCardToHand(horse);

        Card paladin = Catalog.copyOf("paladin");
        GameEngine.getInstance().setCurrentInitiative(0);
        GameEngine.getInstance().getInitiativeOrder().add(paladin);

        boolean result = Command.execute(horse, null);
        Assert.assertTrue(result);
        int before = player.getMoves();
        horse.triggerStartEffects(GameEventContainer.Type.STARTTURN);
        Assert.assertEquals(before + 1, player.getMoves());
        horse.triggerEndEffects(GameEventContainer.Type.ENDTURN);
        Assert.assertEquals(before, player.getMoves());
    }


}