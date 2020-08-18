package net.sachau.zarrax.card.level;

import java.util.LinkedList;
import java.util.List;

public class Level {

    public enum Advancement {
        INCREASE_HITS,
        INCREASE_BUY_POINTS;
    }

    private int experienceNeeded;
    private List<Advancement> advancements = new LinkedList<>();

    public Level() {
        advancements = new LinkedList<>();
    }

    public Level(int experienceNeeded) {
        this.experienceNeeded = experienceNeeded;
    }

    public Level(int experienceNeeded, Advancement ...advancements) {
        this.experienceNeeded = experienceNeeded;
        for (Advancement adv: advancements) {
            this.advancements.add(adv);
        }
    }


    public int getExperienceNeeded() {
        return experienceNeeded;
    }

    public void setExperienceNeeded(int experienceNeeded) {
        this.experienceNeeded = experienceNeeded;
    }

    public List<Advancement> getAdvancements() {
        return advancements;
    }

    public void setAdvancements(List<Advancement> advancements) {
        this.advancements = advancements;
    }
}
