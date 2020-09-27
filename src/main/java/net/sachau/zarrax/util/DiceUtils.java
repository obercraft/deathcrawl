package net.sachau.zarrax.util;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.engine.ApplicationContext;
import net.sachau.zarrax.engine.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DiceUtils {

    public static boolean percentage(int percent) {

        return percent >= ThreadLocalRandom.current()
                .nextInt(1, 101);

    }

    public static int get(int maxSize) {
        return ThreadLocalRandom.current()
                .nextInt(0, maxSize);
    }

    public static Card getRandomCard(List<Card> cards) {
        int index = ThreadLocalRandom.current()
                .nextInt(0, cards != null ? cards.size() : 0);
        return cards.get(index);
    }


    public static void createRandomParty(Player player) {

        List<Card> characters = ApplicationContext.getCatalog()
                .get(Character.class);

        while (player.getParty().size() < Player.PARTY_SIZE) {
            Card copy = CardUtils.copyCard(getRandomCard(characters));
            copy.setOwner(player);
            copy.setVisible(true);
            player.getParty()
                    .add(copy);
        }

    }


}
