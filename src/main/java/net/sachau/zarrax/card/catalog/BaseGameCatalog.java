package net.sachau.zarrax.card.catalog;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.CardParser;
import net.sachau.zarrax.engine.ApplicationContext;

import java.util.List;

@Basic
public class BaseGameCatalog implements CatalogResource {

    @Override
    public void init(boolean withTexts) throws Exception {
        List<Card> allCards = CardParser.parse(Catalog.class
                .getResourceAsStream("/cards/cards.xml"), withTexts);
        ApplicationContext.getCatalog()
                .add(allCards);

        List<Card> monsters = CardParser.parse(Catalog.class
                .getResourceAsStream("/cards/monsters.xml"), withTexts);
        ApplicationContext.getCatalog()
                .add(monsters);

        List<Card> environments = CardParser.parse(Catalog.class
                .getResourceAsStream("/cards/environments.xml"), withTexts);
        ApplicationContext.getCatalog()
                .add(environments);

        List<Card> events = CardParser.parse(Catalog.class
                .getResourceAsStream("/cards/events.xml"), withTexts);
        ApplicationContext.getCatalog()
                .add(events);


        List<Card> basic = CardParser.parse(Catalog.class
                .getResourceAsStream("/cards/characters.xml"), withTexts);
        ApplicationContext.getCatalog()
                .add(basic);

    }
}
