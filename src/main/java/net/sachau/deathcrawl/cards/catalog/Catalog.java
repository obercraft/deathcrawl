package net.sachau.deathcrawl.cards.catalog;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.CardParser;

import java.util.*;

public class Catalog {

    private final Map<Class<? extends Card>, CardCache> caches = new HashMap<>();

    private final CardCache allCards = new CardCache();

    private MapProperty<Long, Card> idCache;
    //private Map<Long, Card> idCache = new HashMap<>();


    private static Catalog catalog;

    private Catalog() {
        ObservableMap<Long,Card> observableMap = FXCollections.observableHashMap();
        idCache = new SimpleMapProperty<>(observableMap);
    }

    public ObservableMap<Long, Card> getIdCache() {
        return idCache.get();
    }

    public MapProperty<Long, Card> idCacheProperty() {
        return idCache;
    }

    public void setIdCache(ObservableMap<Long, Card> idCache) {
        this.idCache.set(idCache);
    }

    public static void init() {
        try {
            List<Card> allCards = CardParser.parse(Catalog.class
                    .getResourceAsStream("/cards/cards.xml"));
            Catalog.getInstance()
                    .add(allCards);

            List<Card> monsters = CardParser.parse(Catalog.class
                    .getResourceAsStream("/cards/monsters.xml"));
            Catalog.getInstance()
                    .add(monsters);

            List<Card> environments = CardParser.parse(Catalog.class
                    .getResourceAsStream("/cards/environments.xml"));
            Catalog.getInstance()
                    .add(environments);

            List<Card> events = CardParser.parse(Catalog.class
                    .getResourceAsStream("/cards/events.xml"));
            Catalog.getInstance()
                    .add(events);


            List<Card> basic = CardParser.parse(Catalog.class
                    .getResourceAsStream("/cards/starting-characters.xml"));
            Catalog.getInstance()
                    .add(basic);

        } catch (Exception e) {
            Logger.error("init catalog failed", e);
        }



    }

    public static Catalog getInstance() {
        if (catalog == null) {
            catalog = new Catalog();
        }
        return catalog;
    }

    public void add(Card card) {
        Class<? extends Card> clazz = card.getClass();
        if (catalog.caches.get(clazz) == null) {
            catalog.caches.put(clazz, new CardCache());
        }
        String cardName = card.getName()
                .toLowerCase()
                .replaceAll(" ", "");
        catalog.caches.get(clazz).put(cardName, card);
        catalog.allCards.put(cardName, card);
    }

    public void add(Collection<Card> cards) {
        for (Card card : cards) {
            catalog.add(card);
        }
    }

    public List<Card> get(Class<? extends Card> type) {
        CardCache cache = catalog.caches.get(type);
        if (cache != null) {
            return new LinkedList<>(cache.values());
        }
        return null;
    }

    public Card get(String cardName) {
        return allCards.get(cardName
                .toLowerCase()
                .replaceAll(" ", ""));
    }

    public static Card getById(long id) {
        return getInstance().getIdCache().get(id);
    }


    public static void putById(Card card) {
        getInstance().getIdCache().put(card.getId(), card);
    }

    public static List<Class<? extends Card>> getCategories() {
        return new ArrayList<Class<? extends Card>>(getInstance().caches.keySet());
    }
}
