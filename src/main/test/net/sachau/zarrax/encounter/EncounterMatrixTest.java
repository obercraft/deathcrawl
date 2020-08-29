package net.sachau.zarrax.encounter;

import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.map.Land;
import net.sachau.zarrax.map.Site;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class EncounterMatrixTest {

    @Test
    public void addSiteEncounter() throws Exception {


        Set<Site> sites = EncounterMatrix.getInstance().getSites();
        Set<Land> lands = EncounterMatrix.getInstance().getLands();
        sites.size();

    }
}