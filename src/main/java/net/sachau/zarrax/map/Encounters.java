package net.sachau.zarrax.map;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.engine.GameData;
import net.sachau.zarrax.gui.screen.Autowired;
import net.sachau.zarrax.util.CardUtils;

import java.util.LinkedList;
import java.util.List;

@GameData
public class Encounters {

    private final Catalog catalog;

    List<Card> encounters;

    @Autowired
    public Encounters(Catalog catalog) {
        super();
        this.catalog = catalog;
        encounters = new LinkedList<>();
    }

    public void addEncounters(String encounterString) {
        String [] encounterArgs = encounterString.split(",", -1);
        for (String encounterArg : encounterArgs) {
            Card card = catalog.get(encounterArg.trim());
            if (card != null) {
                addEncounter(CardUtils.copyCard(card));
            } else {
                Logger.debug("Encounter " +encounterArg + " not found");
            }
        }
    }


    private void addEncounter(Card encounter) {
        encounters.add(encounter);
    }

    @Override
    public String toString() {
        return "Encounters{" + encounters + '}';
    }
}
