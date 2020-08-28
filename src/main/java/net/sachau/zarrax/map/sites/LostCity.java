package net.sachau.zarrax.map.sites;

import net.sachau.zarrax.map.LandTypeList;
import net.sachau.zarrax.map.Site;

public class LostCity implements Site {
    @Override
    public LandTypeList getPossibleLandTypes() {
        return LandTypeList.build().addAll().create();
    }
}
