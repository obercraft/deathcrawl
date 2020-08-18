package net.sachau.zarrax;

import net.sachau.zarrax.card.Card;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DiceUtils {

    public static boolean percentage(int percent) {

        return percent <= ThreadLocalRandom.current().nextInt(1, 101);

    }

    public static int get(int maxSize) {
        return ThreadLocalRandom.current().nextInt(0,  maxSize);
    }

    public static Card getRandomCard(List<Card> cards) {
        int index = ThreadLocalRandom.current().nextInt(0,  cards != null ? cards.size() : 0);
        return cards.get(index);
    }



}
