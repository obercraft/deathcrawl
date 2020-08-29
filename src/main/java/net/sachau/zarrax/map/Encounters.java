package net.sachau.zarrax.map;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.type.Encounter;

import java.util.LinkedList;
import java.util.List;

public class Encounters {

    List<Card> encounters;

    public Encounters() {
        super();
        encounters = new LinkedList<>();
    }

    public void addEncounters(String encounterString) {
        String [] encounterArgs = encounterString.split(",", -1);
        for (String encounterArg : encounterArgs) {
            Card card = Catalog.getInstance().get(encounterArg.trim());
            if (card != null) {
                addEncounter(card);
            } else {
                Logger.debug("Encounter " +encounterArg + " not found");
            }
        }
    }


    private void addEncounter(Card encounter) {
        encounters.add(encounter);
    }
}
