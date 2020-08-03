package net.sachau.deathcrawl;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.catalog.Catalog;
import net.sachau.deathcrawl.cards.types.Environment;
import net.sachau.deathcrawl.cards.types.Encounter;

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
            hazards.add(Utils.copyCard(environments.get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Encounter encounter = (Encounter) eventCards.get(0);

        for (Card card : encounter.getCardList()) {
            try {
                hazards.add(Utils.copyCard(card));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return hazards;
    }
}
