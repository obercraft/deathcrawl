package net.sachau.deathcrawl.card.keyword;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;

public class Keywords extends HashSet<Keyword> {


    public static Keywords build(Keyword ... keyword) {
        Keywords keywords = new Keywords();
        for (Keyword kw : keyword) {
            keywords.add(kw);
        }
        return keywords;
    }

    @Override
    public String toString() {
        return "[" + StringUtils.join(this, ", ") + "]";
    }
}
