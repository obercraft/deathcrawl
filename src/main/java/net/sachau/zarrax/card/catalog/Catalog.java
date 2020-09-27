package net.sachau.zarrax.card.catalog;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.text.TextFlow;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.engine.GameComponent;
import net.sachau.zarrax.engine.GameData;
import net.sachau.zarrax.util.CardUtils;
import org.reflections.Reflections;

import javax.annotation.PostConstruct;
import java.util.*;

@GameData
public class Catalog {

    private final Map<Class<? extends Card>, CardCache> caches = new HashMap<>();

    private final CardCache allCards = new CardCache();

    private final Map<String, TextFlow> texts = new HashMap<>();

    private MapProperty<Long, Card> idCache;
    //private Map<Long, Card> idCache = new HashMap<>();


    private boolean loaded;

    public Catalog() {
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

    @PostConstruct
    public void init() throws Exception {
        init(true);
    }

    public void initForTesting() throws Exception {
        init(false);
    }


    private void init(boolean withTexts) throws Exception {

        if (isLoaded()) {
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
        setLoaded(true);

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

    public void add(Card card) {
        Class<? extends Card> clazz = card.getClass();
        if (caches.get(clazz) == null) {
            caches.put(clazz, new CardCache());
        }
        String cardName = card.getName()
                .toLowerCase()
                .replaceAll(" ", "");
        caches.get(clazz).put(cardName, card);
        allCards.put(cardName, card);
    }

    public void add(Collection<Card> cards) {
        for (Card card : cards) {
            add(card);
        }
    }

    public List<Card> get(Class<? extends Card> type) {
        CardCache cache = caches.get(type);
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

    public Card getById(long id) {
        return getIdCache().get(id);
    }


    public void putById(Card card) {
        getIdCache().put(card.getId(), card);
    }

    public List<Class<? extends Card>> getCategories() {
        return new ArrayList<>(caches.keySet());
    }

    public Card copyOf(String name) {
        return CardUtils.copyCard(get(name));
    }

    public void putText(String name, TextFlow textFlow) {
        texts.put(name, textFlow);
    }

    public TextFlow getText(String name) {
        return texts.get(name);
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
