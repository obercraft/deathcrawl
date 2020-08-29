package net.sachau.zarrax.map.sites;

import net.sachau.zarrax.map.LandList;
import net.sachau.zarrax.map.Site;
import net.sachau.zarrax.map.Warning;

import java.util.Set;

public class LostCity extends Site {

    @Override
    public LandList getPossibleLandTypes() {
        return LandList.build().addAll().create();
    }
}
