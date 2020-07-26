package net.sachau.deathcrawl.gui.card;

import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CardText {

    private Map<String, List<Text>> textCache;
    private Map<String, List<Text>> flavorCache;

    private static final CardText cache = new CardText();

    private CardText() {
        textCache = new HashMap<>();
        flavorCache = new HashMap<>();
    }

    public static CardText cache() {
        return cache;
    }

    public static CardText addText(String cardName, List<Text> texts) {
        cache().textCache.put(cardName, texts);
        return cache();
    }

    public static CardText addFlavor(String cardName, List<Text> flavors) {
        cache().flavorCache.put(cardName, flavors);
        return cache();
    }

    public static boolean containsText(String cardName) {
        return cache().textCache.containsKey(cardName);
    }

    public static boolean containsFlavor(String cardName) {
        return cache().flavorCache.containsKey(cardName);
    }

    public static List<Text> getText(String cardName) {
        return cache().textCache.get(cardName);
    }




}
