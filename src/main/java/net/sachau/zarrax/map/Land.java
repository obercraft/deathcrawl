package net.sachau.zarrax.map;

import java.util.HashMap;
import java.util.Map;

public abstract class Land  {

    private Map<Warning, Encounters> encounters = new HashMap<>();

    public Map<Warning, Encounters> getEncounters() {
        return encounters;
    }

    public void setEncounters(Map<Warning, Encounters> encounters) {
        this.encounters = encounters;
    }

    public abstract int getMoveCost();



}
