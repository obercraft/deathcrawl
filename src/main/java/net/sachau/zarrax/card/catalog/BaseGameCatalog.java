package net.sachau.zarrax.card.catalog;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.CardParser;

import java.util.List;

@Basic
public class BaseGameCatalog implements CatalogResource {

    @Override
    public void init(boolean withTexts) throws Exception {
        List<Card> allCards = CardParser.parse(Catalog.class
                .getResourceAsStream("/cards/cards.xml"), withTexts);
        Catalog.getInstance()
                .add(allCards);

        List<Card> monsters = CardParser.parse(Catalog.class
                .getResourceAsStream("/cards/monsters.xml"), withTexts);
        Catalog.getInstance()
                .add(monsters);

        List<Card> environments = CardParser.parse(Catalog.class
                .getResourceAsStream("/cards/environments.xml"), withTexts);
        Catalog.getInstance()
                .add(environments);

        List<Card> events = CardParser.parse(Catalog.class
                .getResourceAsStream("/cards/events.xml"), withTexts);
        Catalog.getInstance()
                .add(events);


        List<Card> basic = CardParser.parse(Catalog.class
                .getResourceAsStream("/cards/characters.xml"), withTexts);
        Catalog.getInstance()
                .add(basic);

    }
}
