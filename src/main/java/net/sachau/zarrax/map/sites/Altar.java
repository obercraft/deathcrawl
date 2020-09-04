package net.sachau.zarrax.map.sites;

import net.sachau.zarrax.map.LandList;
import net.sachau.zarrax.map.Site;
import net.sachau.zarrax.map.lands.Hill;
import net.sachau.zarrax.map.lands.Mountain;
import net.sachau.zarrax.map.lands.Water;

public class Altar extends Site {

    @Override
    public LandList getPossibleLandTypes() {
        return LandList.build().allExcept(Water.class);
    }
}
