package net.sachau.zarrax.card.catalog;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.CardParser;
import net.sachau.zarrax.engine.ApplicationContext;

import java.util.List;

@Basic
public class BaseGameCatalog implements CatalogResource {

    @Override
    public void init(Catalog catalog, boolean withTexts) throws Exception {
        List<Card> allCards = CardParser.parse(catalog, Catalog.class
                .getResourceAsStream("/cards/cards.xml"), withTexts);

                catalog.add(allCards);

        List<Card> monsters = CardParser.parse(catalog, Catalog.class
                .getResourceAsStream("/cards/monsters.xml"), withTexts);
                catalog.add(monsters);

        List<Card> environments = CardParser.parse(catalog, Catalog.class
                .getResourceAsStream("/cards/environments.xml"), withTexts);

                catalog.add(environments);

        List<Card> events = CardParser.parse(catalog, Catalog.class
                .getResourceAsStream("/cards/events.xml"), withTexts);
        catalog.add(events);


        List<Card> basic = CardParser.parse(catalog, Catalog.class
                .getResourceAsStream("/cards/characters.xml"), withTexts);
        catalog
                .add(basic);

    }
}
