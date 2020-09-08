package net.sachau.zarrax.encounter;

import net.sachau.zarrax.map.Terrain;
import net.sachau.zarrax.map.Warning;

import java.util.Objects;

public class TerrainWarning {

    private Terrain terrain;
    private Warning warning;

    public TerrainWarning(Terrain terrain, Warning warning) {
        this.terrain = terrain;
        this.warning = warning;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Warning getWarning() {
        return warning;
    }

    public void setWarning(Warning warning) {
        this.warning = warning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TerrainWarning that = (TerrainWarning) o;
        return terrain == that.terrain &&
                warning == that.warning;
    }

    @Override
    public int hashCode() {
        return Objects.hash(terrain, warning);
    }

    @Override
    public String toString() {
        return "[" + terrain +
                "->" + warning +
                ']';
    }
}
