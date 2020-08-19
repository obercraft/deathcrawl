package net.sachau.zarrax.card.type;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.keyword.Keyword;

public class Illumination extends Card {

    private int illuminationValue = 0;

    public Illumination() {
        super();
        addKeywords(Keyword.ITEM);
    }

    public Illumination(Illumination action) {
        super(action);
    }

    public int getIlluminationValue() {
        return illuminationValue;
    }

    public void setIlluminationValue(int illuminationValue) {
        this.illuminationValue = illuminationValue;
    }
}
