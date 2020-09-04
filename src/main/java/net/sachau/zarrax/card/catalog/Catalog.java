package net.sachau.zarrax.card.catalog;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.text.TextFlow;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.CardParser;
import net.sachau.zarrax.util.CardUtils;
import org.reflections.Reflections;

import java.util.*;

public class Catalog {

    private final Map<Class<? extends Card>, CardCache> caches = new HashMap<>();

    private final CardCache allCards = new CardCache();

    private final Map<String, TextFlow> texts = new HashMap<>();

    private MapProperty<Long, Card> idCache;
    //private Map<Long, Card> idCache = new HashMap<>();


    private boolean loaded;

    private static Catalog catalog;

    private Catalog() {
        ObservableMap<Long, Card> observableMap = FXCollections.observableHashMap();
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

    public static void init() throws Exception {
        init(true);
    }

    public static void initForTesting() throws Exception {
        init(false);
    }


    private static void init(boolean withTexts) throws Exception {

        if (Catalog.getInstance().isLoaded()) {
            return;
        }
        Logger.debug("reading catalogs");

        Reflections reflections = new Reflections(Catalog.class.getCanonicalName().replace("Catalog", ""));
        Set<Class<? extends CatalogResource>> resourceClasses = reflections.getSubTypesOf(CatalogResource.class);


        LinkedList<CatalogResource> catalogResources = new LinkedList<>();
        for (Class<? extends CatalogResource> resource : resourceClasses) {
            Basic basic = resource.getAnnotation(Basic.class);
            if (basic != null) {
                catalogResources.push(resource.newInstance());
            } else {
                catalogResources.add(resource.newInstance());
            }
        }

        for (CatalogResource catalogResource : catalogResources) {
            Logger.debug("reading " + catalogResource.getClass().getSimpleName());
            catalogResource.init(withTexts);
        }

        Logger.debug("reading catalog done");
        Catalog.getInstance().setLoaded(true);

//        try {
//            List<Card> allCards = CardParser.parse(Catalog.class
//                    .getResourceAsStream("/cards/cards.xml"), withTexts);
//            Catalog.getInstance()
//                    .add(allCards);
//
//            List<Card> monsters = CardParser.parse(Catalog.class
//                    .getResourceAsStream("/cards/monsters.xml"), withTexts);
//            Catalog.getInstance()
//                    .add(monsters);
//
//            List<Card> environments = CardParser.parse(Catalog.class
//                    .getResourceAsStream("/cards/environments.xml"), withTexts);
//            Catalog.getInstance()
//                    .add(environments);
//
//            List<Card> events = CardParser.parse(Catalog.class
//                    .getResourceAsStream("/cards/events.xml"), withTexts);
//            Catalog.getInstance()
//                    .add(events);
//
//
//            List<Card> basic = CardParser.parse(Catalog.class
//                    .getResourceAsStream("/cards/characters.xml"), withTexts);
//            Catalog.getInstance()
//                    .add(basic);
//
//            Logger.debug("reading catalog done");
//            Catalog.getInstance().setLoaded(true);
//        } catch (Exception e) {
//            Logger.error("init catalog failed", e);
//        }


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

    public Card getCopy(String cardName) {
        Card card = get(cardName);
        return card != null ? CardUtils.copyCard(card) : null;

    }

    public static Card getById(long id) {
        return getInstance().getIdCache().get(id);
    }


    public static void putById(Card card) {
        getInstance().getIdCache().put(card.getId(), card);
    }

    public static List<Class<? extends Card>> getCategories() {
        return new ArrayList<>(getInstance().caches.keySet());
    }

    public static Card copyOf(String name) {
        return CardUtils.copyCard(Catalog.getInstance().get(name));
    }

    public static void putText(String name, TextFlow textFlow) {
        getInstance().texts.put(name, textFlow);
    }

    public static TextFlow getText(String name) {
        return getInstance().texts.get(name);
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
