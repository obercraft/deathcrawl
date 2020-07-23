package net.sachau.deathcrawl.cards;

import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.Map;

public class CardCache {

    private static Map<Long, Card> cache = new HashMap<>();

    private static Map<String, Font> fontCache = new HashMap();

    public static Card get(long id) {
        return cache.get(id);
    }


    public static Card put(Card card) {
        cache.put(card.getId(), card);
        return card;
    }

    /*
    public static Font get (String fontName, int size) {
        Font font = fontCache.get("name-" + size);

        if (font ==  null) {
            font = Font.loadFont(CardCache.class.getResourceAsStream("/" + fontName + ".ttf"), size);
            fontCache.put("name-" +  size, font);
        }
        return font;
    }

     */
}
