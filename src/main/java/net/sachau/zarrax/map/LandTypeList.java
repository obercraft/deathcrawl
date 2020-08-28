package net.sachau.zarrax.map;

import java.util.HashSet;

public class LandTypeList extends HashSet<LandType> {


    private LandTypeList() {
        super();
    }

    public static LandTypeList build() {
        return new LandTypeList();
    }

    public LandTypeList addType(LandType landType) {
        super.add(landType);
        return this;
    }

    public LandTypeList addAll() {
        for (LandType landType: LandType.values()) {
            super.add(landType);
        }
        return this;
    }


    public LandTypeList create() {
        return this;
    }

}
