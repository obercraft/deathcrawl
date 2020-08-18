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
        char1.addKeywords(Keyword.SIMPLE);
        Assert.assertNotEquals(char1.getKeywords(), char2.getKeywords());

        Assert.assertEquals(char1.getConditions(), char2.getConditions());

        char1.getConditions().add(new CardEffect() {
            @Override
            public void trigger(Card sourceCard, Card targetCard) {

            }

            @Override
            public void remove(Card card) {

            }

        });

        Assert.assertNotEquals(char1.getConditions(), char2.getConditions());

    }
    @Test
    public void testActions() {
        Player player = new Player();
        GameEngine.getInstance().setPlayer(player);
        GameEngine.getInstance().setInitiativeOrder(new ArrayList<>());
        Catalog.init();
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
        draw.addKeywords(Keyword.SIMPLE);

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
        Assert.assertTrue(thief.hasCondition(Exhausted.class));

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
        Assert.assertTrue(wizard.hasCondition(Armor.class));

        // Attacking Shield
        result = Command.execute(goblin, wizard);
        Assert.assertTrue(result);
        Assert.assertFalse(wizard.hasCondition(Armor.class));
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
        Assert.assertFalse(warrior.hasCondition(Guard.class));
        // trigger again with warrior being in party, this time warrior should have guard
        player.addToParty(warrior);
        GameEngine.getInstance().triggerEffects(GameEventContainer.Type.STARTENCOUNTER);
        Assert.assertTrue(warrior.hasCondition(Guard.class));

        // attacking wizard with warrior guard should fail
        result = Command.execute(goblin, wizard);
        Assert.assertFalse(result);

        goblin.addKeywords(Keyword.RANGED);
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
        goblinWithGuard.getConditions().add(new Guard());
        goblinWithGuard.getConditions().add(new Armor());


        player.addToHazards(goblin);
        player.addToHazards(goblinWithGuard);
        result = Command.execute(warrior, goblin);
        Assert.assertFalse(result);

        result = Command.execute(warrior, goblinWithGuard);
        Assert.assertTrue(result);

        // target self?
        warrior.removeCondition(Exhausted.class);
        result = Command.execute(warrior, warrior);
        Assert.assertFalse(result);

        // target friendly?
        warrior.removeCondition(Exhausted.class);
        result = Command.execute(warrior, wizard);
        Assert.assertFalse(false);

        // attack prone?
        warrior.removeCondition(Exhausted.class);
        warrior.getConditions().add(new Prone());
        result = Command.execute(warrior, wizard);
        Assert.assertFalse(false);

        // retaliate
        goblinWithGuard.addKeywords(Keyword.RETALIATE);
        warrior.removeCondition(Prone.class);
        result = Command.execute(warrior, goblinWithGuard);
        Assert.assertTrue(result);

        // retaliate with kill
        goblinWithGuard.addKeywords(Keyword.RETALIATE);
        goblinWithGuard.setDamage(1000);
        warrior.removeCondition(Prone.class);
        warrior.removeCondition(Exhausted.class);
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
        Assert.assertTrue(warrior.hasCondition(Prone.class));
        Assert.assertNotEquals(warrior.getHits(), warrior.getMaxHits());


        // Limited Usage
        LimitedUsage limitedUsage = new LimitedUsage();
        limitedUsage.setOwner(player);
        limitedUsage.setUses(2);
        limitedUsage.setDamage(1);

        limitedUsage.setUsageCommand("heal target");
        player.addToParty(limitedUsage);

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
