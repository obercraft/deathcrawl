package net.sachau.zarrax.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Land {

    private Map<Warning, Encounters> encounters = new HashMap<>();
    private Set<Site> sites = new HashSet<>();
    private City city;
    private Terrain terrain;

    public Land() {
    }

    public Map<Warning, Encounters> getEncounters() {
        return encounters;
    }

    public void setEncounters(Map<Warning, Encounters> encounters) {
        this.encounters = encounters;
    }

    public Set<Site> getSites() {
        return sites;
    }

    public void setSites(Set<Site> sites) {
        this.sites = sites;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public int getMoveCost() {
        return this.terrain.getMoveCost();
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(terrain.toString());
        if (city != null) {
            sb.append(", ").append("city");
        }

        return sb.toString();
    }
}
