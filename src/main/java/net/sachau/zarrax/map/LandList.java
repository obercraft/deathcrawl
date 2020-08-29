package net.sachau.zarrax.map;

import net.sachau.zarrax.card.Card;
import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

public class LandList extends HashSet<Land> {

    public static final String typePrefix = Land.class.getCanonicalName()
            .replace("Land", "lands");


    private LandList() {
        super();
    }

    public static LandList build() {
        return new LandList();
    }

    public LandList addAll() {
        return addType("*");
    }

    public LandList addType (Class<? extends Land> landClass) {
        return addType(landClass.getSimpleName());
    }

    public LandList addType(String landName) {

        Reflections reflections = new Reflections(typePrefix);
        Set<Class<? extends Land>> landClasses = reflections.getSubTypesOf(Land.class);

        for (Class<? extends Land> landClass: landClasses) {
            if ("*".equalsIgnoreCase(landName) || landName.equalsIgnoreCase(landClass.getSimpleName())) {
                try {
                    super.add(landClass.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }


    public LandList create() {
        return this;
    }

}
