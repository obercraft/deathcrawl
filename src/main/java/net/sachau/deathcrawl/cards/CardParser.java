package net.sachau.deathcrawl.cards;

import net.sachau.deathcrawl.Event;
import net.sachau.deathcrawl.cards.types.Character;
import net.sachau.deathcrawl.cards.types.EventDeck;
import net.sachau.deathcrawl.cards.types.Monster;
import net.sachau.deathcrawl.effects.CardEffect;
import net.sachau.deathcrawl.keywords.Keyword;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.w3c.dom.*;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CardParser {

    public static Map<String, Card> cache = new ConcurrentHashMap<>();

    public static List<Card> parse(InputStream inputStream) throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        builder.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                if (systemId != null && systemId.contains("cards.dtd")) {
                    InputSource source = new InputSource(this.getClass()
                            .getResourceAsStream("/cards/cards.dtd"));
                    return source;
                }
                return null;
            }
        });

        Document document = builder.parse(inputStream);

        Element root = document.getDocumentElement();

        List<Card> result = new LinkedList<>();
        for (int i = 0; i < root.getElementsByTagName("card")
                .getLength(); i++) {
            result.add(parse(root.getElementsByTagName("card")
                    .item(i)));
        }
        return result;
    }

    private static Card parse(Node node) throws IllegalAccessException, InstantiationException {
        Card card = null;

        String cardType = "Action";

        for (int a = 0; a < node.getChildNodes()
                .getLength(); a++) {
            Node cardNode = node.getChildNodes()
                    .item(a);
            if (cardNode.getNodeName()
                    .equals("type")) {
                cardType = cardNode.getTextContent().trim();
            }
        }

        Reflections reflections = new Reflections("net.sachau.deathcrawl.cards.types");
        Set<Class<? extends Card>> allClasses = reflections.getSubTypesOf(Card.class);
        for (Class<? extends Card> clazz : allClasses) {
            if (clazz.getSimpleName()
                    .toLowerCase()
                    .matches(cardType.toLowerCase())) {
                System.out.println(clazz.getSimpleName());
                card = clazz.newInstance();
            }
        }


        for (int a = 0; a < node.getChildNodes()
                .getLength(); a++) {
            Node cardNode = node.getChildNodes()
                    .item(a);



            if (cardNode.getNodeName()
                    .equals("name")) {
                try {
                    card.setName(cardNode.getTextContent()
                            .trim());
                } catch (Exception e) {

                    e.printStackTrace();
                }
                NamedNodeMap attributes = cardNode.getAttributes();
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attribute = attributes.item(0);
                    if (attribute.getNodeName()
                            .equals("uniqueId")) {
                        String uniqueId = attribute.getTextContent()
                                .trim();
                        if (StringUtils.isNotEmpty(uniqueId)) {
                            card.setUniqueId(uniqueId);
                        }
                    }
                }
            }

            if (cardNode.getNodeName()
                    .equals("deck")) {
                if (card instanceof EventDeck) {
                    EventDeck eventDeck = (EventDeck) card;
                    String [] cards = cardNode.getTextContent().trim().split(",", -1);
                    for (String cardName : cards) {
                        eventDeck.getDeck().add(Cards.get(cardName));
                    }
                }
            }


            if (cardNode.getNodeName()
                    .equals("hits")) {
                int h = Integer.parseInt(cardNode.getTextContent()
                        .trim());
                card.initHits(h);
            }

            if (cardNode.getNodeName()
                    .equals("damage")) {
                int d = Integer.parseInt(cardNode.getTextContent()
                        .trim());
                card.initDamage(d);
            }

            if (cardNode.getNodeName()
                    .equals("experience")) {
                if (card instanceof Monster) {
                    int xp = Integer.parseInt(cardNode.getTextContent()
                            .trim());
                    ((Monster) card).setXp(xp);
                }
            }

            if (cardNode.getNodeName()
                    .equals("gold")) {
                if (card instanceof Monster) {
                    int gold = Integer.parseInt(cardNode.getTextContent()
                            .trim());
                    ((Monster) card).setGold(gold);
                }
            }


            if (cardNode.getNodeName()
                    .equals("command")) {
                card.setCommand(cardNode.getTextContent()
                        .trim()
                        .toLowerCase());
            }


            if (cardNode.getNodeName()
                    .equals("starting-cards")) {
                if (card instanceof Character) {

                    String[] cards = cardNode.getTextContent()
                            .trim()
                            .split(",", -1);
                    Character characterCard = (Character) card;
                    characterCard.setStartingCards(new Deck());
                    for (String startingCard : cards) {
                        characterCard.getStartingCards()
                                .add(Cards.get(startingCard));
                    }
                }

            }


            if (cardNode.getNodeName()
                    .equals("keywords")) {
                String keyWordsArgs[] = cardNode
                        .getTextContent()
                        .trim()
                        .toUpperCase()
                        .split(",", -1);
                for (String kw : keyWordsArgs) {
                    card.addKeywords(Keyword.valueOf(kw.trim()));
                }
            }

            if (cardNode.getNodeName()
                    .equals("effects")) { // net.sachau.deathcrawl.effects
                NodeList effectNodes = cardNode.getChildNodes();
                if (effectNodes.getLength() > 0) {
                    for (int i = 0; i < effectNodes.getLength(); i++) {
                        NodeList childNodes = effectNodes.item(0)
                                .getChildNodes();
                        for (int j = 0; j < childNodes.getLength(); j++) {
                            Node effectNode = childNodes.item(j);
                            if (effectNode.getAttributes() != null) {
                                for (int k = 0; k < effectNode.getAttributes()
                                        .getLength(); k++) {
                                    Node atribute = effectNode.getAttributes()
                                            .item(k);
                                    if (atribute.getNodeName()
                                            .equals("event")) {

                                        String event = atribute.getTextContent()
                                                .trim()
                                                .toUpperCase();
                                        String effectName = effectNode.getTextContent()
                                                .trim()
                                                .toLowerCase();
                                        Reflections refEffect = new Reflections("net.sachau.deathcrawl.effects");
                                        Set<Class<? extends CardEffect>> effects = refEffect.getSubTypesOf(CardEffect.class);
                                        for (Class<? extends CardEffect> effectClass : effects) {
                                            if (effectClass.getSimpleName()
                                                    .toLowerCase()
                                                    .equals(effectName)) {
                                                card.addEffect(Event.valueOf(event), effectClass.newInstance());
                                            }
                                        }
                                    }


                                }
                            }
                        }
                    }

                }

            }

            /*
            if (!CardText.containsText(card.getName())) {

                NodeList text = root.getElementsByTagName("text");
                for (int i = 0; i < text.getLength(); i++) {
                    Node t = text.item(i);
                    NodeList nodeList = t.getChildNodes();
                    List<javafx.scene.text.Text> texts = new LinkedList<>();
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Node content = nodeList.item(j);
                        String nodeName = content.getNodeName()
                                .toLowerCase();
                        if ("#text".equals(nodeName)) {
                            String value = content.getNodeValue()
                                    .trim();
                            texts.add(new javafx.scene.text.Text(texts.size() > 0 ? " " + value : value));
                        } else if ("b".equals(nodeName)) {
                            String value = content.getTextContent()
                                    .trim();
                            Text txt = new Text(texts.size() > 0 ? " " + value : value);
                            txt.getStyleClass()
                                    .add("card-text-bold");
                            texts.add(txt);
                            //
                            //cardDesign.getCardText().add(txt);
                        }
                    }
                    CardText.addText(card.getName(), texts);
                }
            }

             */


        }
        if (card != null) {
            Cards.put(card);
        }
        return card;
    }
}