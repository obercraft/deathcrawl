package net.sachau.zarrax.map;

import java.util.HashSet;

public class TerrainList extends HashSet<Terrain> {


    private TerrainList() {
        super();
    }

    public static TerrainList build() {
        return new TerrainList();
    }

    public TerrainList allExcept(Terrain ...exceptedTerrain) {
        TerrainList allLands = new TerrainList();
        for (Terrain t : Terrain.values()) {
            for (Terrain et : exceptedTerrain) {
                if (t == et) {
                    continue;
                }
            }
            allLands.add(t);
        }
        return allLands;
    }

    public TerrainList addTerrain(Terrain terrain) {
        super.add(terrain);
        return this;
    }

    public TerrainList addFromString(String terrainString) {

        String [] terrainArgs = terrainString.split(",", -1);
        for (String terrainArg : terrainArgs) {
            String ts = terrainArg.trim().toUpperCase();
            if ("*".equals(ts)) {
                for (Terrain t: Terrain.values()) {
                    addTerrain(t);
                }
                return this;
            }
            Terrain t = Terrain.valueOf(ts);
            if (t != null) {
                addTerrain(t);
            }
        }
        return this;
    }
    public TerrainList create() {
        return this;
    }

}
