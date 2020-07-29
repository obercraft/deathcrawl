package net.sachau.deathcrawl.cards;

import net.sachau.deathcrawl.cards.types.StartingCharacter;
import net.sachau.deathcrawl.effects.CardEffect;
import net.sachau.deathcrawl.keywords.Keyword;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CardTest {

    @Test
    public void copyCard() {

        StartingCharacter char1 = new StartingCharacter();
        char1.setName("Char2");


        StartingCharacter char2 = new StartingCharacter(char1);

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
}
