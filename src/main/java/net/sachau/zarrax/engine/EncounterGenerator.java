package net.sachau.zarrax.engine;

import net.sachau.zarrax.DiceUtils;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.CardUtils;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.type.Environment;
import net.sachau.zarrax.card.type.Encounter;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncounterGenerator {

    private static final Pattern countMatcher = Pattern.compile("^\\[(\\d+)-(\\d+)\\](.+)$");

    private final List<Card> eventCards;
    private final List<Card> environments;

    private final List<Card> hazards;

    private EncounterGenerator(){
        eventCards = Catalog.getInstance()
                .get(Encounter.class);

        environments = Catalog.getInstance()
                .get(Environment.class);

        hazards = new LinkedList<>();

    }

    public static EncounterGenerator builder() {
        return new EncounterGenerator();
    }



    public List<Card> getCards() {
        return this.hazards;
    }

    public EncounterGenerator addRandomEnvironment() {
        if (DiceUtils.percentage(50)) {
            return this;
        }
        this.hazards.add(CardUtils.copyCard(DiceUtils.getRandomCard(environments)));
        return this;
    }

    public EncounterGenerator addRandomEvent() {
        Encounter encounter = (Encounter) CardUtils.copyCard(DiceUtils.getRandomCard(eventCards));
        this.hazards.addAll(createEncounterCards(encounter.getCardString()));
        return this;
    }

    private List<Card> createEncounterCards(String cardString) {
        String[] cards = cardString.trim().split(",", -1);
        List<Card> encounter = new LinkedList<>();
        for (String cardName : cards) {

            int amount = 1;
            Matcher matcher = countMatcher.matcher(cardName.trim());
            if (matcher.find()) {
                int from = NumberUtils.toInt(matcher.group(1), 0);
                int to = 1 + NumberUtils.toInt(matcher.group(2), 0);
                amount = ThreadLocalRandom.current().nextInt(from, to);
                cardName = matcher.group(3);
                Logger.debug("adding " + cardName + " " + amount + " times");
            }

            try {
                for (int r = 0; r < amount; r++) {
                    Card c = Catalog.getInstance()
                            .get(cardName);
                    c.setVisible(true);
                    c.setOwner(null);
                    encounter.add(CardUtils.copyCard(c));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return encounter;
    }

}



