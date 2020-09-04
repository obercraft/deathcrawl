package net.sachau.zarrax.map;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.encounter.EncounterMatrix;
import net.sachau.zarrax.map.lands.*;
import net.sachau.zarrax.util.DiceUtils;

import java.util.*;

public class World {

    private int rows; // how many rows of tiles should be created
    private int columns; // the amount of tiles that are contained in each row

    private Map<Class<? extends Land>, List<Land>> map = new HashMap<>();

    public World() {
        init(20, 20);
    }

    public World(int columns, int rows) {
        init(columns, rows);
    }

    private void init(int columns, int rows) {
        this.rows = rows;
        this.columns = columns;

        Noise noise = new Noise(null, 1, columns, rows);
        noise.initialise();
        float[][] noiseMap = noise.getGrid();

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {

                Land land = new Valley();
                float value = noiseMap[x][y];

                if (value < -0.3f) {
                    System.out.println(value);
                    land = new Water();
                }

                if (value > 0.5f) {
                    land = new Woods();

                }
                if (value > 0.6f) {
                    land = new Hill();
                }

                if (value > 0.7f) {
                    land = new Mountain();
                }

                land.setColumn(x);
                land.setRow(y);

                if (map.get(land.getClass()) == null) {
                    map.put(land.getClass(), new ArrayList<>());
                }
                map.get(land.getClass()).add(land);
            }


        }
        Set<Site> sites = EncounterMatrix.getInstance().getSites();
        for (Site site : sites) {
            boolean landFound = false;
            LandList landTypes = site.getPossibleLandTypes();
            for (Land landType : landTypes) {
                Land land = getRandomLandForType(landType);
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
    }

    private Land getRandomLandForType(Land landType) {
        List<Land> lands = getMap().get(landType.getClass());
        return lands.get(DiceUtils.get(lands.size()));
    }

    public Map<Class<? extends Land>, List<Land>> getMap() {
        return map;
    }

    @Override
    public String toString() {
        String [][]m = new String[this.rows][this.columns];
        for (Map.Entry<Class<? extends Land>, List<Land>>  entry : map.entrySet()) {
            for (Land l : entry.getValue()) {
                if (l.getSites().size() > 0) {
                    m[l.getRow()][l.getColumn()] = getSiteChar(l.getSites());
                } else {
                    m[l.getRow()][l.getColumn()] = getLandChar(entry.getKey());
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int r = 0 ; r < rows; r++) {
            for (int c = 0; c < columns; c++) {

                sb.append(m[r][c]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getSiteChar(Set<Site> sites) {
        return sites.toArray()[0].getClass().getSimpleName().substring(0,1);
    }

    private String getLandChar(Class<? extends Land> key) {
        if (key == Water.class) {
            return "=";
        }
        if (key == Mountain.class) {
            return "^";
        }
        if (key == Valley.class) {
            return ".";
        }
        if (key == Woods.class) {
            return "&";
        }
        if (key == Hill.class) {
            return "#";
        }


        return key.getSimpleName().substring(0,1);
    }
}
