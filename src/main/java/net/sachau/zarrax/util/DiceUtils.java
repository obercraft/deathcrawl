package net.sachau.zarrax.util;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.v2.GEngine;
import net.sachau.zarrax.v2.GState;

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

        Catalog catalog = GEngine.getBean(Catalog.class);
        List<Card> characters = catalog.get(Character.class);

        while (player.getParty().size() < Player.PARTY_SIZE) {
            Card c = getRandomCard(characters);
            Card copy = CardUtils.copyCard(c);
            copy.setOwner(player);
            copy.setVisible(true);
            player.getParty()
                    .add(copy);
        }

    }


}
