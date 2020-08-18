package net.sachau.zarrax.card;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UniqueCardListTest  {

    @Test
    public void addCard() {
        UniqueCardList uniqueList = new UniqueCardList();

        Card unique = new Card() {
        };

        unique.setUniqueId("u");

        Assert.assertTrue(uniqueList.add(unique));

        Assert.assertFalse(uniqueList.add(unique));



    }

}