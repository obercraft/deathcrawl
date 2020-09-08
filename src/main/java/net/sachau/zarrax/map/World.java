package net.sachau.zarrax.map;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.encounter.EncounterMatrixGenerator;
import net.sachau.zarrax.util.DiceUtils;

import java.util.*;

public class World {

    private int rows; // how many rows of tiles should be created
    private int columns; // the amount of tiles that are contained in each row

    private Land [][] map;

    private Map<Terrain, List<Land>> terrainMapping = new HashMap<>();

    public World() {
        init(20, 20);
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
                Land land = getRandomLandForTerrain(terrain);
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

        return;
    }

    private Land getRandomLandForTerrain(Terrain terrain) {
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
                } else {
                    sb.append(map[x][y].getTerrain().name().substring(0, 1));
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
