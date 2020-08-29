package net.sachau.zarrax.map.sites;

import net.sachau.zarrax.map.LandList;
import net.sachau.zarrax.map.Site;
import net.sachau.zarrax.map.Warning;
import net.sachau.zarrax.map.lands.Hill;
import net.sachau.zarrax.map.lands.Mountain;

import java.util.Set;

public class Hoard extends Site {

    @Override
    public LandList getPossibleLandTypes() {
        return LandList.build().addType(Mountain.class).addType(Hill.class).create();
    }
}
