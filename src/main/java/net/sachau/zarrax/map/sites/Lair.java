package net.sachau.zarrax.map.sites;

import net.sachau.zarrax.map.LandType;
import net.sachau.zarrax.map.LandTypeList;
import net.sachau.zarrax.map.Site;

public class Lair implements Site {
    @Override
    public LandTypeList getPossibleLandTypes() {
        return  LandTypeList.build().addType(LandType.MOUNTAINS).create();
    }
}
