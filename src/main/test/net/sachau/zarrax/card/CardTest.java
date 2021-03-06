package net.sachau.zarrax.card;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.command.Command;
import net.sachau.zarrax.card.command.CommandResult;
import net.sachau.zarrax.card.command.CommandStage;
import net.sachau.zarrax.card.effect.CardEffect;
import net.sachau.zarrax.card.effect.Guard;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.card.type.*;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.v2.GState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class CardTest {

    private Catalog catalog = new Catalog();
    private GState state = new GState();

    private Player player;

    @Before
    public void init() throws Exception {

        player = new Player();
        state.setPlayer(player);
        state.setInitiativeOrder(new ArrayList<>());

        catalog.initForTesting();
    }

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

        Assert.assertEquals(char1.getKeywords(), char2.getKeywords());
        char1.addKeyword(Keyword.SIMPLE);
        Assert.assertNotEquals(char1.getKeywords(), char2.getKeywords());

        Assert.assertEquals(2, char1.getEffects().size());
        Assert.assertEquals(1, char2.getEffects().size());

        char1.getEffects().add(new CardEffect() {
            @Override
            public void start(Card targetCard) {

            }

            @Override
            public void end(Card card) {

            }

        });

        Assert.assertEquals(3, char1.getEffects().size());
        Assert.assertEquals(1, char2.getEffects().size());

    }
    @Test
    public void testActions() {

        Card thief = catalog.copyOf("Thief");
        player.addToParty(thief);
        state.getInitiativeOrder().add(thief);

        Assert.assertEquals(thief.getId(), player.getParty().get(0).getId());
        Assert.assertEquals(player.getParty().get(0).getName(), "Thief");


        Card gold = catalog.copyOf("Gold");

        player.addCardToHand(gold);

        Card draw = new Action();
        draw.setOwner(player);
        draw.setCommand("draw self 1");
        draw.addKeyword(Keyword.SIMPLE);
        player.addCardToHand(draw);
        // Draw
        CommandResult commandResult = Command.execute(draw, null);
        Assert.assertTrue(commandResult.isSuccessful());

        // Gold
        commandResult = Command.execute(player.getHand().get(0), null);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertEquals(1, player.getGold());

        // Attack
        Card goblin = catalog.copyOf("Goblin");
        commandResult = Command.execute(thief, goblin);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertNotEquals(goblin.getHits(), goblin.getMaxHits());
        Assert.assertTrue(player.getDraw().getDiscardPile().contains(gold));
        Assert.assertTrue(((Character)thief).getSelectedCard().hasKeyword(Keyword.EXHAUSTED));

        // try again will fail (exhaustion)
        commandResult = Command.execute(thief, goblin);
        Assert.assertFalse(commandResult.isSuccessful());


        // Shield
        Card wizard = catalog.copyOf("wizard");
        player.addToParty(wizard);
        Card shield = catalog.copyOf("Shield");
        player.addCardToHand(shield);

        state.getInitiativeOrder().add(wizard);
        state.setCurrentInitiative(1);
        commandResult = Command.execute(shield, wizard);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertTrue(wizard.hasKeyword(Keyword.ARMOR));

        // Attacking Shield
        commandResult = Command.execute(goblin, wizard);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertFalse(wizard.hasKeyword(Keyword.ARMOR));
        Assert.assertEquals(wizard.getHits(), wizard.getMaxHits());

        commandResult = Command.execute(goblin, wizard);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertNotEquals(wizard.getHits(), wizard.getMaxHits());

        Card potion = catalog.copyOf("Healing Potion");
        player.addCardToHand(potion);
        commandResult = Command.execute(potion, wizard);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertEquals(wizard.getHits(), wizard.getMaxHits());

        Card horse = catalog.copyOf("Horse");
        player.addCardToHand(horse);
        commandResult = Command.execute(horse, null);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertTrue(player.getParty().contains(horse));

        Card randomAttack = new Action();
        randomAttack.setCommand("attack random");
        randomAttack.setDamage(2);

        commandResult = Command.execute(randomAttack, thief);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertTrue((thief.getHits() +2 == thief.getMaxHits()) || (wizard.getHits() +2  == wizard.getMaxHits()));

        Card warrior = catalog.copyOf("Warrior");
        // trigger events without warrior being in party
        // TODO msachau gameEngine.triggerStartEffects(GameEventContainer.Type.START_ENCOUNTER);
        Assert.assertFalse(warrior.hasKeyword(Keyword.ARMOR));
        // trigger again with warrior being in party, this time warrior should have guard
        player.addToParty(warrior);
        // TODO msachau gameEngine.triggerStartEffects(GameEventContainer.Type.START_ENCOUNTER);
        Assert.assertTrue(warrior.hasEffect(Guard.class));


        // attacking wizard with warrior guard should fail
        commandResult = Command.execute(goblin, wizard);
        Assert.assertFalse(commandResult.isSuccessful());

        goblin.addKeyword(Keyword.RANGED);
        // attacking wizard with ranged goblin
        commandResult = Command.execute(goblin, wizard);
        Assert.assertTrue(commandResult.isSuccessful());



        // killing warrior
        Card kill = new Action();
        kill.setCommand("attack");
        kill.setDamage(1000);
        commandResult = Command.execute(kill, warrior);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertEquals(0, warrior.getHits());
        // and attacking again
        commandResult = Command.execute(goblin, wizard);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertNotEquals(wizard.getHits(), wizard.getMaxHits());

        AdvancedAction healFull = new AdvancedAction() {
            @Override
            public CommandResult execute(Card targetCard) {
                Logger.debug("fully healing " + targetCard);
                targetCard.setHits(targetCard.getMaxHits());
                return CommandResult.successful();
            }
        };
        commandResult = Command.execute(healFull, warrior);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertEquals(warrior.getHits(), warrior.getMaxHits());

        // some reverse test
        Card goblinWithGuard = catalog.copyOf("Goblin");
        goblinWithGuard.getEffects().add(new Guard());

        player.addToHazards(goblin);
        player.addToHazards(goblinWithGuard);

        // TODO gameEngine.triggerStartEffects(GameEventContainer.Type.START_ENCOUNTER);

        // warrior cannot attack guarded goblin
        warrior.reset();
        commandResult = Command.execute(warrior, goblin);
        Assert.assertFalse(commandResult.isSuccessful());

        // but he can attack a guard
        warrior.reset();
        commandResult = Command.execute(warrior, goblinWithGuard);
        Assert.assertTrue(commandResult.isSuccessful());

        // target self?
        warrior.reset();
        commandResult = Command.execute(warrior, warrior);
        Assert.assertFalse(commandResult.isSuccessful());

        // target friendly?
        warrior.reset();
        commandResult = Command.execute(warrior, wizard);
        Assert.assertFalse(commandResult.isSuccessful());

        // attack prone?
        warrior.reset();
        warrior.addKeyword(Keyword.PRONE);
        commandResult = Command.execute(warrior, wizard);
        Assert.assertFalse(commandResult.isSuccessful());

        // retaliate
        warrior.reset();
        goblinWithGuard.addKeyword(Keyword.RETALIATE);
        warrior.getKeywords().remove(Keyword.PRONE);
        commandResult = Command.execute(warrior, goblinWithGuard);
        Assert.assertTrue(commandResult.isSuccessful());

        // retaliate with kill
        warrior.reset();
        goblinWithGuard.addKeyword(Keyword.RETALIATE);
        goblinWithGuard.setDamage(1000);
        warrior.getKeywords().remove(Keyword.PRONE);
        warrior.getKeywords().remove(Keyword.EXHAUSTED);
        commandResult = Command.execute(warrior, goblinWithGuard);
        Assert.assertTrue(commandResult.isSuccessful());

        // attack with prone
        Card goblinProner = catalog.copyOf("Goblin");
        goblinProner.setCommand("attack;prone");
        goblinProner.setDamage(2);
        commandResult = Command.execute(healFull, warrior);
        Assert.assertTrue(commandResult.isSuccessful());
        commandResult = Command.execute(goblinProner, warrior);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertTrue(warrior.hasKeyword(Keyword.PRONE));
        Assert.assertNotEquals(warrior.getHits(), warrior.getMaxHits());


        // Limited Usage
        LimitedUsage limitedUsage = new LimitedUsage();
        limitedUsage.setOwner(player);
        limitedUsage.setUses(2);
        limitedUsage.setDamage(1);

        limitedUsage.setUsageCardName("Healing Potion");
        player.addToParty(limitedUsage);


        state.setCurrentInitiative(0);
        commandResult = Command.execute(limitedUsage, warrior);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertEquals(warrior.getHits(), warrior.getMaxHits() -1);
        Assert.assertEquals(1, limitedUsage.getUses());
        commandResult = Command.execute(limitedUsage, warrior);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertEquals(warrior.getHits(), warrior.getMaxHits());
        Assert.assertEquals(0, limitedUsage.getUses());
        commandResult = Command.execute(limitedUsage, warrior);
        Assert.assertFalse(commandResult.isSuccessful());


        // check if thief can play magic missile
        Card magicMissile = catalog.copyOf("Magic Missile");
        magicMissile.setOwner(player);
        Command.execute(magicMissile, goblin);

        // check if wizard can play magic missile, this should kill the last goblin
        state.setCurrentInitiative(1);
        commandResult = Command.execute(magicMissile, goblin);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertEquals(0, player.getHazards().size());


    }

    @Test
    public  void testLevelCards() {
        Character thief = (Character) catalog.copyOf("thief");
        Card goblin = catalog.copyOf("Goblin");
        CommandResult commandResult = Command.execute(thief, goblin);
        Assert.assertTrue(commandResult.isSuccessful());
        Assert.assertNotEquals(goblin.getHits(), goblin.getMaxHits());
        Assert.assertTrue(thief.getSelectedCard().hasKeyword(Keyword.EXHAUSTED));
        Assert.assertFalse(goblin.hasKeyword(Keyword.EXHAUSTED));
        thief.reset();
        Assert.assertFalse(thief.getSelectedCard().hasKeyword(Keyword.EXHAUSTED));

    }

    @Test
    public void testMonsterCommands() {

        Monster monster = new Monster();
        monster.setCommand("simple");
        Assert.assertEquals("simple", monster.getCommand());

        monster.setRandomCommands(new ArrayList<>());
        monster.getRandomCommands().add("command1");
        monster.getRandomCommands().add("command2");
        String randomCommand = monster.getCommand();
        Assert.assertTrue("command1".equals(randomCommand) || "command2".equals(randomCommand));

        List<CommandStage> commandStages = new ArrayList<>();
        monster.setCommandStages(commandStages);
        monster.setRandomCommands(null);

        commandStages.add(new CommandStage(10, "stage1"));
        commandStages.add(new CommandStage(5, "stage2"));
        commandStages.add(new CommandStage(1, "stage3"));

        monster.setHits(20);
        Assert.assertEquals("stage1", monster.getCommand());

        monster.setHits(10);
        Assert.assertEquals("stage1", monster.getCommand());

        monster.setHits(9);
        Assert.assertEquals("stage2", monster.getCommand());

        monster.setHits(5);
        Assert.assertEquals("stage2", monster.getCommand());

        monster.setHits(4);
        Assert.assertEquals("stage3", monster.getCommand());

        monster.setHits(1);
        Assert.assertEquals("stage3", monster.getCommand());




    }

    @Test
    public void drawTest() {

        int numberOfCards = 10;
        for (int i = 0; i < numberOfCards; i++) {
            Card card = new Card() {
            };
            card.setName("card " +i);
            player.addCardToDraw(card);
        }
        Assert.assertEquals(numberOfCards, player.getDraw().getSize());

        player.initHand();
        Assert.assertEquals(numberOfCards - (player.getMaxHandsize() + player.getDraw().getDiscardPile().size()), player.getDraw().getSize());
        Assert.assertEquals(player.getMaxHandsize(), player.getHand().size());
        Assert.assertEquals(0, player.getDraw().getDiscardPile().size());


        player.draw();
        Assert.assertEquals(numberOfCards - player.getHand().size(), player.getDraw().getSize());


        player.discard(player.getHand().get(0));
        player.discard(player.getHand().get(0));
        Assert.assertEquals(numberOfCards - (player.getHand().size() + player.getDraw().getSize()), player.getDraw().getSize());
        Assert.assertEquals(player.getMaxHandsize() - 1, player.getHand().size());
        Assert.assertEquals(2, player.getDraw().getSize());

    }

}
