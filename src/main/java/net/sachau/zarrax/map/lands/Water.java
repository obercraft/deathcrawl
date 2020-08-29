package net.sachau.zarrax.map.lands;

import net.sachau.zarrax.map.Land;

public class Water extends Land {
    @Override
    public int getMoveCost() {
        return 3;
    }
}
