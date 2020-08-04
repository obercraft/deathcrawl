package net.sachau.deathcrawl.engine;

import net.sachau.deathcrawl.card.CardUtils;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.catalog.Catalog;
import net.sachau.deathcrawl.card.type.Environment;
import net.sachau.deathcrawl.card.type.Encounter;

import java.util.ArrayList;
import java.util.List;

public class EncounterGenerator {

    private EncounterGenerator(){

    }

    public static List<Card> build() {
        List<Card> eventCards = Catalog.getInstance()
                .get(Encounter.class);

        List<Card> environments = Catalog.getInstance()
                .get(Environment.class);

        List<Card> hazards = new ArrayList<>();

        try {
            hazards.add(CardUtils.copyCard(environments.get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Encounter encounter = (Encounter) eventCards.get(0);

        for (Card card : encounter.getCardList()) {
            try {
                hazards.add(CardUtils.copyCard(card));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return hazards;
    }
}
