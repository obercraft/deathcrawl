package net.sachau.deathcrawl.cards;

import javafx.scene.effect.Effect;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.Event;
import net.sachau.deathcrawl.cards.types.Character;
import net.sachau.deathcrawl.gui.card.CardText;
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

    public static List<Card> parse(String fileName) throws Exception {

        InputStream resource = CardParser.class.getClass().getResourceAsStream(fileName);

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        builder.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                if (systemId != null && systemId.contains("card.dtd")) {
                    InputSource source = new InputSource(this.getClass().getResourceAsStream("/card-design/card.dtd"));
                    return source;
                }
                return null;
            }
        });

        Document document = builder.parse(resource);

        Element root = document.getDocumentElement();

        List<Card> result = new LinkedList<>();
        for (int i = 0; i < root.getElementsByTagName("card").getLength(); i++) {
            result.add(parse(root.getElementsByTagName("card").item(i)));
        }
        return result;
    }

    private static Card parse(Node node) throws IllegalAccessException, InstantiationException {
        Card card = null;

        String cardType = "Standard";

        for (int a = 0; a < node.getChildNodes().getLength(); a++) {
            Node cardNode = node.getChildNodes().item(a);

            if (cardNode.getNodeName().equals("type"))
            {
                
                if (type != null && type.getLength() > 0) {
                    cardType = type.item(0).getTextContent().trim();
                }
            }
            Reflections reflections = new Reflections("net.sachau.deathcrawl.cards.types");

            Set<Class<? extends Card>> allClasses = reflections.getSubTypesOf(Card.class);
            for (Class<? extends Card> clazz : allClasses) {
                if (clazz.getSimpleName().toLowerCase().matches(cardType.toLowerCase())) {
                    card = clazz.newInstance();
                }
            }

            {
                NodeList name = cardNode.root.getElementsByTagName("name");
                if (name != null && name.getLength() > 0) {
                    card.setName(name.item(0).getTextContent().trim());
                    NamedNodeMap attributes = name.item(0).getAttributes();
                    for (int i = 0; i < attributes.getLength(); i++) {
                        Node attribute = attributes.item(0);
                        if (attribute.getNodeName().equals("uniqueId")) {
                            String uniqueId = attribute.getTextContent().trim();
                            if (StringUtils.isNotEmpty(uniqueId)) {
                                card.setUniqueId(uniqueId);
                            }
                        }
                    }
                }
            }

            {
                NodeList hits = root.getElementsByTagName("hits");
                if (hits != null && hits.getLength() > 0) {
                    int h = Integer.parseInt(hits.item(0).getTextContent().trim());
                    card.initHits(h);
                }
            }

            {
                NodeList damage = root.getElementsByTagName("damage");
                if (damage != null && damage.getLength() > 0) {
                    int d = Integer.parseInt(damage.item(0).getTextContent().trim());
                    card.initDamage(d);
                }
            }

            {
                NodeList command = root.getElementsByTagName("command");
                if (command != null && command.getLength() > 0) {
                    card.setCommand(command.item(0).getTextContent().trim().toLowerCase());
                }
            }


            if (card instanceof Character) {
                NodeList startingCards = root.getElementsByTagName("starting-cards");
                if (startingCards != null) {
                    String[] cards = startingCards.item(0).getTextContent().trim().split(",", -1);
                    Character characterCard = (Character) card;
                    characterCard.setStartingCards(new Deck());
                    for (String startingCard : cards) {
                        characterCard.getStartingCards().add(parseFor("/cards/basic", startingCard.toLowerCase() + ".xml"));
                    }
                }

            }

            {
                NodeList keywords = root.getElementsByTagName("keywords");
                if (keywords.getLength() > 0) {
                    String keyWordsArgs[] = keywords.item(0).getTextContent().trim().toUpperCase().split(",", -1);
                    for (String kw : keyWordsArgs) {
                        card.addKeywords(Keyword.valueOf(kw.trim()));
                    }

                }
            }

            { // net.sachau.deathcrawl.effects
                NodeList effectNodes = root.getElementsByTagName("effects");
                if (effectNodes.getLength() > 0) {
                    for (int i = 0; i < effectNodes.getLength(); i++) {
                        NodeList childNodes = effectNodes.item(0).getChildNodes();
                        for (int j = 0; j < childNodes.getLength(); j++) {
                            Node effectNode = childNodes.item(j);
                            if (effectNode.getAttributes() != null) {
                                for (int k = 0; k < effectNode.getAttributes().getLength(); k++) {
                                    Node atribute = effectNode.getAttributes().item(k);
                                    if (atribute.getNodeName().equals("event")) {

                                        String event = atribute.getTextContent().trim().toUpperCase();
                                        String effectName = effectNode.getTextContent().trim().toLowerCase();
                                        Reflections refEffect = new Reflections("net.sachau.deathcrawl.effects");
                                        Set<Class<? extends CardEffect>> effects = refEffect.getSubTypesOf(CardEffect.class);
                                        for (Class<? extends CardEffect> effectClass : effects) {
                                            if (effectClass.getSimpleName().toLowerCase().equals(effectName)) {
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

            if (!CardText.containsText(card.getName())) {

                NodeList text = root.getElementsByTagName("text");
                for (int i = 0; i < text.getLength(); i++) {
                    Node t = text.item(i);
                    NodeList nodeList = t.getChildNodes();
                    List<javafx.scene.text.Text> texts = new LinkedList<>();
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Node content = nodeList.item(j);
                        String nodeName = content.getNodeName().toLowerCase();
                        if ("#text".equals(nodeName)) {
                            String value = content.getNodeValue().trim();
                            texts.add(new javafx.scene.text.Text(texts.size() > 0 ? " " + value : value));
                        } else if ("b".equals(nodeName)) {
                            String value = content.getTextContent().trim();
                            Text txt = new Text(texts.size() > 0 ? " " + value : value);
                            txt.getStyleClass().add("card-text-bold");
                            texts.add(txt);
                            //
                            //cardDesign.getCardText().add(txt);
                        }
                    }
                    CardText.addText(card.getName(), texts);
                }
            }

            cache.put(qualifiedName, card);
            return card;

        }

        public static Card parseFor (String path, String fileName) throws Exception {

            final String qualifiedName = path + "/" + fileName;

            if (cache.get(qualifiedName) != null) {
                return cache.get(qualifiedName).getClass().newInstance();
            }

            InputStream resource = CardParser.class.getClass().getResourceAsStream(qualifiedName);

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();


            builder.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                    if (systemId != null && systemId.contains("card.dtd")) {
                        InputSource source = new InputSource(this.getClass().getResourceAsStream("/card-design/card.dtd"));
                        return source;
                    }
                    return null;
                }
            });

            Document document = builder.parse(resource);

            Element root = document.getDocumentElement();

            Card card = null;

            String cardType = "Standard";
            {
                NodeList type = root.getElementsByTagName("type");
                if (type != null && type.getLength() > 0) {
                    cardType = type.item(0).getTextContent().trim();
                }
            }
            Reflections reflections = new Reflections("net.sachau.deathcrawl.cards.types");

            Set<Class<? extends Card>> allClasses = reflections.getSubTypesOf(Card.class);
            for (Class<? extends Card> clazz : allClasses) {
                if (clazz.getSimpleName().toLowerCase().matches(cardType.toLowerCase())) {
                    card = clazz.newInstance();
                }
            }

            {
                card.setName(StringUtils.capitalize(fileName.toLowerCase().replaceFirst(".xml", "")));
                NodeList name = root.getElementsByTagName("name");
                if (name != null && name.getLength() > 0) {
                    card.setName(name.item(0).getTextContent().trim());
                    NamedNodeMap attributes = name.item(0).getAttributes();
                    for (int i = 0; i < attributes.getLength(); i++) {
                        Node attribute = attributes.item(0);
                        if (attribute.getNodeName().equals("uniqueId")) {
                            String uniqueId = attribute.getTextContent().trim();
                            if (StringUtils.isNotEmpty(uniqueId)) {
                                card.setUniqueId(uniqueId);
                            }
                        }
                    }
                }
            }

            {
                NodeList hits = root.getElementsByTagName("hits");
                if (hits != null && hits.getLength() > 0) {
                    int h = Integer.parseInt(hits.item(0).getTextContent().trim());
                    card.initHits(h);
                }
            }

            {
                NodeList damage = root.getElementsByTagName("damage");
                if (damage != null && damage.getLength() > 0) {
                    int d = Integer.parseInt(damage.item(0).getTextContent().trim());
                    card.initDamage(d);
                }
            }

            {
                NodeList command = root.getElementsByTagName("command");
                if (command != null && command.getLength() > 0) {
                    card.setCommand(command.item(0).getTextContent().trim().toLowerCase());
                }
            }


            if (card instanceof Character) {
                NodeList startingCards = root.getElementsByTagName("starting-cards");
                if (startingCards != null) {
                    String[] cards = startingCards.item(0).getTextContent().trim().split(",", -1);
                    Character characterCard = (Character) card;
                    characterCard.setStartingCards(new Deck());
                    for (String startingCard : cards) {
                        characterCard.getStartingCards().add(parseFor("/cards/basic", startingCard.toLowerCase() + ".xml"));
                    }
                }

            }

            {
                NodeList keywords = root.getElementsByTagName("keywords");
                if (keywords.getLength() > 0) {
                    String keyWordsArgs[] = keywords.item(0).getTextContent().trim().toUpperCase().split(",", -1);
                    for (String kw : keyWordsArgs) {
                        card.addKeywords(Keyword.valueOf(kw.trim()));
                    }

                }
            }

            { // net.sachau.deathcrawl.effects
                NodeList effectNodes = root.getElementsByTagName("effects");
                if (effectNodes.getLength() > 0) {
                    for (int i = 0; i < effectNodes.getLength(); i++) {
                        NodeList childNodes = effectNodes.item(0).getChildNodes();
                        for (int j = 0; j < childNodes.getLength(); j++) {
                            Node effectNode = childNodes.item(j);
                            if (effectNode.getAttributes() != null) {
                                for (int k = 0; k < effectNode.getAttributes().getLength(); k++) {
                                    Node atribute = effectNode.getAttributes().item(k);
                                    if (atribute.getNodeName().equals("event")) {

                                        String event = atribute.getTextContent().trim().toUpperCase();
                                        String effectName = effectNode.getTextContent().trim().toLowerCase();
                                        Reflections refEffect = new Reflections("net.sachau.deathcrawl.effects");
                                        Set<Class<? extends CardEffect>> effects = refEffect.getSubTypesOf(CardEffect.class);
                                        for (Class<? extends CardEffect> effectClass : effects) {
                                            if (effectClass.getSimpleName().toLowerCase().equals(effectName)) {
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

            if (!CardText.containsText(card.getName())) {

                NodeList text = root.getElementsByTagName("text");
                for (int i = 0; i < text.getLength(); i++) {
                    Node t = text.item(i);
                    NodeList nodeList = t.getChildNodes();
                    List<javafx.scene.text.Text> texts = new LinkedList<>();
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Node content = nodeList.item(j);
                        String nodeName = content.getNodeName().toLowerCase();
                        if ("#text".equals(nodeName)) {
                            String value = content.getNodeValue().trim();
                            texts.add(new javafx.scene.text.Text(texts.size() > 0 ? " " + value : value));
                        } else if ("b".equals(nodeName)) {
                            String value = content.getTextContent().trim();
                            Text txt = new Text(texts.size() > 0 ? " " + value : value);
                            txt.getStyleClass().add("card-text-bold");
                            texts.add(txt);
                            //
                            //cardDesign.getCardText().add(txt);
                        }
                    }
                    CardText.addText(card.getName(), texts);
                }
            }

        }
        cache.put(qualifiedName, card);
        return card;
    }

}
