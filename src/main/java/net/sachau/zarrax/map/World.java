package net.sachau.zarrax.map;

import net.sachau.zarrax.Configuration;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.encounter.EncounterGenerator;
import net.sachau.zarrax.encounter.EncounterMatrixGenerator;
import net.sachau.zarrax.util.DiceUtils;

import java.util.*;

public class World {

    private int rows; // how many rows of tiles should be created
    private int columns; // the amount of tiles that are contained in each row

    private Land [][] map;

    private Map<Terrain, List<Land>> terrainMapping = new HashMap<>();

    public World() {
        init(Configuration.getInstance().getInt("zarrax.map.columns"), Configuration.getInstance().getInt("zarrax.map.rows"));
    }

    public World(int columns, int rows) {
        init(columns, rows);
    }

    private void init(int columns, int rows) {
        EncounterMatrixGenerator.getInstance();
        this.rows = rows;
        this.columns = columns;

        this.map = new Land[columns][rows];

        Noise noise = new Noise(null, 1, columns, rows);
        noise.initialise();
        float[][] noiseMap = noise.getGrid();



        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {

                Land land = new Land();
                land.setTerrain(Terrain.VALLEY);
                float value = noiseMap[x][y];
                for (Terrain t : Terrain.values()) {
                    if (value >= t.getLeft() && value <= t.getRight() ) {
                        land.setTerrain(t);
                    }
                }

                map[x][y] = land;
                if (terrainMapping.get(land.getTerrain()) == null) {
                    terrainMapping.put(land.getTerrain(), new LinkedList<>());
                }
                terrainMapping.get(land.getTerrain()).add(land);
            }


        }
        for (Site site : Site.values()) {
            boolean landFound = false;
            TerrainList landTypes = site.getPossibleTerrains();
            if (landTypes == null) {
                continue;
            }
            for (Terrain terrain : landTypes) {
                Land land = getRandomLandForSite(terrain);
                if (land != null) {
                    land.getSites().add(site);
                    landFound = true;
                    break;
                }
            }
            if (!landFound) {
                String msg = "no land found for site " + site;
                Logger.error(msg);
                throw new RuntimeException(msg);
            }

        }

        // TODO msachau cities
        int numberOfCities = 5;
        for (int i = 0; i < numberOfCities; i++) {
            Land land = getRandomLandForCity();
            if (land != null) {
                land.setCity(new StandardCity());
            }
        }

        return;
    }

    private Land getRandomLandForCity() {
        Land land = null;
        int tries = 0;
        do {
            List<Land> lands = terrainMapping.get(Terrain.VALLEY);
            Land cityLand = lands.get(DiceUtils.get(lands.size()));
            if (cityLand.getCity() == null) {
                return cityLand;
            }
            tries ++;

        } while (tries < 10);
        return land;
    }

    private Land getRandomLandForSite(Terrain terrain) {
        List<Land> lands = terrainMapping.get(terrain);
        return lands.get(DiceUtils.get(lands.size()));
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                if (map[x][y].getSites().size() > 0) {
                    sb.append("!");
                } else
                if (map[x][y].getCity() != null) {
                    sb.append("*");
                } else {
                    sb.append(map[x][y].getTerrain().name().substring(0, 1));
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Land[][] getMap() {
        return map;
    }

    public List<Card> getHazards(int x, int y) {
        return EncounterGenerator.builder().addRandomEnvironment().addRandomEvent().getCards();
    }
}
