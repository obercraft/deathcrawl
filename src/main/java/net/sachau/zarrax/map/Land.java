package net.sachau.zarrax.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Land  {

    private Map<Warning, Encounters> encounters = new HashMap<>();
    private Set<Site> sites = new HashSet<>();
    private int column;
    private int row;


    public Map<Warning, Encounters> getEncounters() {
        return encounters;
    }

    public void setEncounters(Map<Warning, Encounters> encounters) {
        this.encounters = encounters;
    }

    public abstract int getMoveCost();

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Set<Site> getSites() {
        return sites;
    }

    public void setSites(Set<Site> sites) {
        this.sites = sites;
    }
}
