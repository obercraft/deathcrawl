package net.sachau.zarrax.encounter;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.CardParser;
import net.sachau.zarrax.card.UniqueCardList;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.type.Encounter;
import net.sachau.zarrax.card.type.Environment;
import net.sachau.zarrax.util.CardUtils;
import net.sachau.zarrax.util.DiceUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.reflections.Reflections;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Deprecated
public class EncounterGenerator {

    private static final Pattern countMatcher = Pattern.compile("^\\[(\\d+)-(\\d+)\\](.+)$");

    private final List<Card> eventCards;
    private final List<Card> environments;

    private final UniqueCardList hazards;

    private EncounterGenerator(){
        eventCards = Catalog.getInstance()
                .get(Encounter.class);

        environments = Catalog.getInstance()
                .get(Environment.class);

        hazards = new UniqueCardList();

    }

    public static EncounterGenerator builder() {
        return new EncounterGenerator();
    }



    public List<Card> getCards() {
        return this.hazards;
    }

    public EncounterGenerator addRandomEnvironment() {
        /*
        if (DiceUtils.percentage(50)) {
            return this;
        }
         */
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
        UniqueCardList encounter = new UniqueCardList();
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

            List<Card> typeSelection = null;
            if (cardName.trim().startsWith("type:")) {
                String type = cardName.trim().replaceFirst("type:", "");
                Reflections reflections = new Reflections(CardParser.typePrefix);
                Set<Class<? extends Card>> allClasses = reflections.getSubTypesOf(Card.class);
                for (Class<? extends Card> clazz : allClasses) {
                    if (clazz.getSimpleName()
                            .toLowerCase()
                            .matches(type.toLowerCase())) {
                        typeSelection = Catalog.getInstance().get(clazz);
                        break;

                    }
                }
            }
            
            
            try {
                for (int r = 0; r < amount; r++) {
                    Card c;
                    if (typeSelection != null && typeSelection.size() > 0) {
                        c = DiceUtils.getRandomCard(typeSelection);
                    } else {
                        c = Catalog.getInstance()
                                .get(cardName);
                    }
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



