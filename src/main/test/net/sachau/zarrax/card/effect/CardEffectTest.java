package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.command.Command;
import net.sachau.zarrax.card.command.CommandResult;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.card.type.Horse;
import net.sachau.zarrax.engine.ApplicationContext;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.engine.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CardEffectTest {

    private Catalog catalog = new Catalog();
    private GameEngine gameEngine = new GameEngine();

    Player player;

    @Before
    public void init() throws Exception {
        ApplicationContext.put(GameEngine.class, gameEngine);
        ApplicationContext.put(Catalog.class, catalog);

        player = new Player();
        gameEngine
                .setPlayer(player);
        gameEngine
                .setInitiativeOrder(new ArrayList<>());

        catalog.initForTesting();
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
            public void start(Card targetCard) {
                targetCard.addKeyword(Keyword.SIMPLE);
            }

            @Override
            public void end(Card card) {
                card.removeKeyword(Keyword.SIMPLE);
            }
        };
        EffectTiming timing = new EffectTiming(GameEventContainer.Type.START_ENCOUNTER, GameEventContainer.Type.END_ENCOUNTER);
        effect.setEffectTiming(timing);
        testCard.getEffects().add(effect);

        testCard.triggerStartEffects(GameEventContainer.Type.START_ENCOUNTER);
        Assert.assertTrue(testCard.hasKeyword(Keyword.SIMPLE));
        testCard.triggerEndEffects(GameEventContainer.Type.END_ENCOUNTER);
        Assert.assertFalse(testCard.hasKeyword(Keyword.SIMPLE));

        // adding keyword twice
        testCard.addKeyword(Keyword.PRONE);
        testCard.addKeyword(Keyword.PRONE);
        Assert.assertTrue(testCard.hasKeyword(Keyword.PRONE));
        testCard.removeKeyword(Keyword.PRONE);

        Assert.assertFalse(testCard.hasKeyword(Keyword.PRONE));

        // adding same effect twice
        testCard.getEffects().add(effect);
        testCard.getEffects().add(effect);

        // still only one effect
        Assert.assertEquals(1,testCard.getEffects().size());





    }

    @Test
    public void testGuard() {

        Card thief = catalog.copyOf("thief");
        Card warrior = catalog.copyOf("warrior");
        Card wizard = catalog.copyOf("wizard");

        Assert.assertEquals(2, thief.getKeywords().size());
        Assert.assertTrue(thief.hasKeyword(Keyword.ROGUE));
        Assert.assertTrue(thief.hasKeyword(Keyword.CREATURE));

        player.addToParty(warrior);
        player.addToParty(thief);
        player.addToParty(wizard);

        gameEngine.setCurrentInitiative(0); // warrior
        for (Card c : player.getParty()) {
            c.triggerStartEffects(GameEventContainer.Type.START_ENCOUNTER);
        }

        Assert.assertTrue(thief.hasKeyword(Keyword.GUARDED));
        Assert.assertTrue(wizard.hasKeyword(Keyword.GUARDED));
        Assert.assertFalse(warrior.hasKeyword(Keyword.GUARDED));

        int warriorEffects = warrior.getEffects()
                .size();

        for (Card c : player.getParty()) {
            c.triggerEndEffects(GameEventContainer.Type.END_ENCOUNTER);
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

        Card paladin = catalog.copyOf("paladin");
        gameEngine.setCurrentInitiative(0);
        gameEngine.getInitiativeOrder().add(paladin);

        CommandResult commandResult = Command.execute(horse, null);
        Assert.assertTrue(commandResult.isSuccessful());
        int before = player.getMoves();
        horse.triggerStartEffects(GameEventContainer.Type.START_TURN);
        Assert.assertEquals(before + 1, player.getMoves());
        horse.triggerEndEffects(GameEventContainer.Type.END_TURN);
        Assert.assertEquals(before, player.getMoves());
    }


}