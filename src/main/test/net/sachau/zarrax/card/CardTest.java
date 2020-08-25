package net.sachau.zarrax.card;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.effect.*;
import net.sachau.zarrax.card.type.Action;
import net.sachau.zarrax.card.type.AdvancedAction;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.card.type.LimitedUsage;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.card.command.Command;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.engine.Player;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

@RunWith(JUnit4.class)
public class CardTest {

    @Test
    public void copyCard() {

        Character char1 = new Character();
        char1.setName("Char2");


        Character char2 = new Character(char1);

        char1.setName("Char1");
        char1.setHits(2);
        char1.setMaxHits(2);
        char2.setDamage(2);


        Assert.assertNotEquals(char1.getName(), char2.getName());
        Assert.assertNotEquals(char1.getId(), char2.getId());
        Assert.assertNotEquals(char1.getHits(), char2.getHits());
        Assert.assertNotEquals(char1.getMaxHits(), char2.getMaxHits());
        Assert.assertNotEquals(char1.getDamage(), char2.getDamage());

        Assert.assertEquals(char1.getKeywords(), char1.getKeywords());
        char1.addKeyword(Keyword.SIMPLE);
        Assert.assertNotEquals(char1.getKeywords(), char2.getKeywords());

        Assert.assertEquals(char1.getEffects(), char2.getEffects());

        char1.getEffects().add(new CardEffect() {
            @Override
            public void trigger(Card targetCard) {

            }

            @Override
            public void remove(Card card) {

            }

        });

        Assert.assertNotEquals(char1.getEffects(), char2.getEffects());

    }
    @Test
    public void testActions() {
        Player player = new Player();
        GameEngine.getInstance().setPlayer(player);
        GameEngine.getInstance().setInitiativeOrder(new ArrayList<>());
        Catalog.initForTesting();
        Card thief = Catalog.copyOf("Thief");
        player.addToParty(thief);
        GameEngine.getInstance().getInitiativeOrder().add(thief);

        Assert.assertEquals(thief.getId(), player.getParty().get(0).getId());
        Assert.assertEquals(player.getParty().get(0).getName(), "Thief");


        Card gold = Catalog.copyOf("Gold");

        player.addCardToHand(gold);

        Card draw = new Action();
        draw.setOwner(player);
        draw.setCommand("draw 1");
        draw.addKeyword(Keyword.SIMPLE);

        // Draw
        boolean result = Command.execute(draw, null);
        Assert.assertTrue(result);

        // Gold
        result = Command.execute(player.getHand().get(0), null);
        Assert.assertTrue(result);
        Assert.assertEquals(1, player.getGold());

        // Attack
        Card goblin = Catalog.copyOf("Goblin");
        result = Command.execute(thief, goblin);
        Assert.assertTrue(result);
        Assert.assertNotEquals(goblin.getHits(), goblin.getMaxHits());
        Assert.assertTrue(player.getDraw().getDiscards().contains(gold));
        Assert.assertTrue(((Character)thief).getSelectedCard().hasKeyword(Keyword.EXHAUSTED));

        // try again will fail (exhaustion)
        result = Command.execute(thief, goblin);
        Assert.assertFalse(result);


        // Shield
        Card wizard = Catalog.copyOf("wizard");
        player.addToParty(wizard);
        Card shield = Catalog.copyOf("Shield");
        player.addCardToHand(shield);

        GameEngine.getInstance().getInitiativeOrder().add(wizard);
        GameEngine.getInstance().setCurrentInitiative(1);
        result = Command.execute(shield, wizard);
        Assert.assertTrue(result);
        Assert.assertTrue(wizard.hasKeyword(Keyword.ARMOR));

        // Attacking Shield
        result = Command.execute(goblin, wizard);
        Assert.assertTrue(result);
        Assert.assertFalse(wizard.hasKeyword(Keyword.ARMOR));
        Assert.assertEquals(wizard.getHits(), wizard.getMaxHits());

        result = Command.execute(goblin, wizard);
        Assert.assertTrue(result);
        Assert.assertNotEquals(wizard.getHits(), wizard.getMaxHits());

        Card potion = Catalog.copyOf("Healing Potion");
        player.addCardToHand(potion);
        result = Command.execute(potion, wizard);
        Assert.assertTrue(result);
        Assert.assertEquals(wizard.getHits(), wizard.getMaxHits());

        Card horse = Catalog.copyOf("Horse");
        player.addCardToHand(horse);
        result = Command.execute(horse, null);
        Assert.assertTrue(result);
        Assert.assertTrue(player.getParty().contains(horse));

        Card randomAttack = new Action();
        randomAttack.setCommand("attack random");
        randomAttack.setDamage(2);

        result = Command.execute(randomAttack, thief);
        Assert.assertTrue(result);
        Assert.assertTrue((thief.getHits() +2 == thief.getMaxHits()) || (wizard.getHits() +2  == wizard.getMaxHits()));

        Card warrior = Catalog.copyOf("Warrior");
        // trigger events without warrior being in party
        GameEngine.getInstance().triggerEffects(GameEventContainer.Type.STARTENCOUNTER);
        Assert.assertFalse(warrior.hasKeyword(Keyword.ARMOR));
        // trigger again with warrior being in party, this time warrior should have guard
        player.addToParty(warrior);
        GameEngine.getInstance().triggerEffects(GameEventContainer.Type.STARTENCOUNTER);
        Assert.assertTrue(warrior.hasKeyword(Keyword.ARMOR));

        // attacking wizard with warrior guard should fail
        result = Command.execute(goblin, wizard);
        Assert.assertFalse(result);

        goblin.addKeyword(Keyword.RANGED);
        // attacking wizard with ranged goblin
        result = Command.execute(goblin, wizard);
        Assert.assertTrue(result);



        // killing warrior
        Card kill = new Action();
        kill.setCommand("attack");
        kill.setDamage(1000);
        result = Command.execute(kill, warrior);
        Assert.assertTrue(result);
        Assert.assertEquals(0, warrior.getHits());
        // and attacking again
        result = Command.execute(goblin, wizard);
        Assert.assertTrue(result);
        Assert.assertNotEquals(wizard.getHits(), wizard.getMaxHits());

        AdvancedAction healFull = new AdvancedAction() {
            @Override
            public boolean execute(Card targetCard) {
                Logger.debug("fully healing " + targetCard);
                targetCard.setHits(targetCard.getMaxHits());
                return true;
            }
        };
        result = Command.execute(healFull, warrior);
        Assert.assertTrue(result);
        Assert.assertEquals(warrior.getHits(), warrior.getMaxHits());

        // some reverse test
        Card goblinWithGuard = Catalog.copyOf("Goblin");
        goblinWithGuard.hasKeyword(Keyword.GUARDED);

        player.addToHazards(goblin);
        player.addToHazards(goblinWithGuard);
        result = Command.execute(warrior, goblin);
        Assert.assertFalse(result);

        result = Command.execute(warrior, goblinWithGuard);
        Assert.assertTrue(result);

        // target self?
        warrior.getKeywords().remove(Keyword.EXHAUSTED);
        result = Command.execute(warrior, warrior);
        Assert.assertFalse(result);

        // target friendly?
        warrior.getKeywords().remove(Keyword.EXHAUSTED);
        result = Command.execute(warrior, wizard);
        Assert.assertFalse(result);

        // attack prone?
        warrior.getKeywords().remove(Keyword.EXHAUSTED);
        warrior.addKeyword(Keyword.PRONE);
        result = Command.execute(warrior, wizard);
        Assert.assertFalse(result);

        // retaliate
        goblinWithGuard.addKeyword(Keyword.RETALIATE);
        warrior.getKeywords().remove(Keyword.PRONE);
        result = Command.execute(warrior, goblinWithGuard);
        Assert.assertTrue(result);

        // retaliate with kill
        goblinWithGuard.addKeyword(Keyword.RETALIATE);
        goblinWithGuard.setDamage(1000);
        warrior.getKeywords().remove(Keyword.PRONE);
        warrior.getKeywords().remove(Keyword.EXHAUSTED);
        result = Command.execute(warrior, goblinWithGuard);
        Assert.assertTrue(result);

        // attack with prone
        Card goblinProner = Catalog.copyOf("Goblin");
        goblinProner.setCommand("attack;prone");
        goblinProner.setDamage(2);
        result = Command.execute(healFull, warrior);
        Assert.assertTrue(result);
        result = Command.execute(goblinProner, warrior);
        Assert.assertTrue(result);
        Assert.assertTrue(warrior.hasKeyword(Keyword.PRONE));
        Assert.assertNotEquals(warrior.getHits(), warrior.getMaxHits());


        // Limited Usage
        LimitedUsage limitedUsage = new LimitedUsage();
        limitedUsage.setOwner(player);
        limitedUsage.setUses(2);
        limitedUsage.setDamage(1);

        limitedUsage.setUsageCardName("Healing Potion");
        player.addToParty(limitedUsage);


        GameEngine.getInstance().setCurrentInitiative(0);
        result = Command.execute(limitedUsage, warrior);
        Assert.assertTrue(result);
        Assert.assertEquals(warrior.getHits(), warrior.getMaxHits() -1);
        Assert.assertEquals(1, limitedUsage.getUses());
        result = Command.execute(limitedUsage, warrior);
        Assert.assertTrue(result);
        Assert.assertEquals(warrior.getHits(), warrior.getMaxHits());
        Assert.assertEquals(0, limitedUsage.getUses());
        result = Command.execute(limitedUsage, warrior);
        Assert.assertFalse(result);


        Card magicMissile = Catalog.copyOf("Magic Missile");
        magicMissile.setOwner(player);
        Command.execute(magicMissile, goblin);





    }

}
