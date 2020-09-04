package net.sachau.zarrax.encounter;

import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.map.Land;
import net.sachau.zarrax.map.Site;
import net.sachau.zarrax.map.World;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class EncounterMatrixTest {

    Player player;

    @Before
    public void init() throws Exception {
        player = new Player();
        GameEngine.getInstance().setPlayer(player);
        GameEngine.getInstance().setInitiativeOrder(new ArrayList<>());
        Catalog.initForTesting();
    }


    @Test
    public void addSiteEncounter() throws Exception {


        Set<Site> sites = EncounterMatrix.getInstance().getSites();
        Set<Land> lands = EncounterMatrix.getInstance().getLands();
        sites.size();

    }

    @Test
    public void testWorld() {
        World world = new World();
        Map<Class<? extends Land>, List<Land>> map = world.getMap();
        map.size();
        System.out.println(world.toString());

    }
}