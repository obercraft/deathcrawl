package net.sachau.zarrax.card;


import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.effect.EffectTiming;
import net.sachau.zarrax.card.effect.KeywordEffect;
import net.sachau.zarrax.util.CardUtils;
import net.sachau.zarrax.util.XmlUtils;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.effect.CardEffect;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.card.type.*;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.gui.text.CardText;
import net.sachau.zarrax.gui.text.TextParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.reflections.Reflections;
import org.w3c.dom.*;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CardParser {

    public static final String typePrefix = Card.class.getCanonicalName()
            .replace("Card", "type");
    private static final String effectPrefix = Card.class.getCanonicalName()
            .replace("Card", "effect");

    public static List<Card> parse(InputStream inputStream, boolean withTexts) throws Exception {

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
                    .item(i), withTexts));
        }
        return result;
    }

    private static Card parse(Node node, boolean withTexts) throws IllegalAccessException, InstantiationException {
        Long start = new Date().getTime();
        Card card = null;

        String cardType = "Action";

        for (int a = 0; a < node.getChildNodes()
                .getLength(); a++) {
            Node cardNode = node.getChildNodes()
                    .item(a);
            if (cardNode.getNodeName()
                    .equals("type")) {
                cardType = cardNode.getTextContent()
                        .trim();
            }
        }


        Reflections reflections = new Reflections(typePrefix);
        Set<Class<? extends Card>> allClasses = reflections.getSubTypesOf(Card.class);
        for (Class<? extends Card> clazz : allClasses) {
            if (clazz.getSimpleName()
                    .toLowerCase()
                    .matches(cardType.toLowerCase())) {
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
                    card.setName(XmlUtils.getValue(cardNode));
                } catch (Exception e) {

                    e.printStackTrace();
                }
                String uniqueId = XmlUtils.getAttributes(cardNode)
                        .get("uniqueId");
                if (StringUtils.isNotEmpty(uniqueId)) {
                    card.setUniqueId(uniqueId);
                }
            }

            if (cardNode.getNodeName()
                    .equals("deck")) {
                if (card instanceof Encounter) {
                    Encounter encounter = (Encounter) card;
                    encounter.setCardString(XmlUtils.getValue(cardNode));
                }
            }


            if (cardNode.getNodeName()
                    .equals("hits")) {
                int h = Integer.parseInt(XmlUtils.getValue(cardNode));
                card.initHits(h);

                if (card instanceof Monster) {
                    Monster monster = (Monster) card;
                    String regenerateString = XmlUtils.getAttributes(cardNode)
                            .get("regenerate");
                    monster.setRegenerate(NumberUtils.toInt(regenerateString, 0));
                }
            }

            if (cardNode.getNodeName()
                    .equals("damage")) {
                int d = Integer.parseInt(XmlUtils.getValue(cardNode));
                card.initDamage(d);
            }

            if (cardNode.getNodeName()
                    .equals("skill")) {
                int s = Integer.parseInt(XmlUtils.getValue(cardNode));
                card.initSkill(s);
            }

            if (cardNode.getNodeName()
                    .equals("threat")) {
                if (card instanceof Environment) {
                    int t = Integer.parseInt(XmlUtils.getValue(cardNode));
                    ((Environment) card).setThreat(t);
                }
            }


            if (cardNode.getNodeName()
                    .equals("experience")) {
                if (card instanceof Monster) {
                    int xp = Integer.parseInt(XmlUtils.getValue(cardNode));
                    ((Monster) card).setXp(xp);
                }
            }

            if (cardNode.getNodeName()
                    .equals("gold")) {
                if (card instanceof Monster) {
                    int gold = Integer.parseInt(XmlUtils.getValue(cardNode));
                    ((Monster) card).setGold(gold);
                }
            }


            if (cardNode.getNodeName()
                    .equals("command")) {
                card.setCommand(XmlUtils.getValue(cardNode)
                        .toLowerCase());
            }

            if (cardNode.getNodeName()
                    .toLowerCase()
                    .equals("usage-card")) {
                if (card instanceof LimitedUsage) {
                    LimitedUsage lu = (LimitedUsage) card;
                    lu.setUsageCardName(XmlUtils.getValue(cardNode));
                }
            }

            if (cardNode.getNodeName()
                    .toLowerCase()
                    .equals("uses")) {
                if (card instanceof LimitedUsage) {
                    LimitedUsage lu = (LimitedUsage) card;
                    lu.setUses(new Integer(XmlUtils.getValue(cardNode)
                            .trim()
                            .toLowerCase()));
                }
            }


            if (cardNode.getNodeName()
                    .equals("starting-cards")) {
                if (card instanceof Character) {

                    String[] cards = XmlUtils.getValue(cardNode)
                            .trim()
                            .split(",", -1);
                    Character characterCard = (Character) card;
                    for (String startingCard : cards) {
                        try {
                            characterCard.getStartingCards()
                                    .add(CardUtils.copyCard(Catalog.getInstance()
                                            .get(startingCard)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            if (cardNode.getNodeName()
                    .equals("level-cards")) {
                if (card instanceof Character) {

                    String[] cards = XmlUtils.getValue(cardNode)
                            .trim()
                            .split(",", -1);
                    Character characterCard = (Character) card;
                    for (String levelCard : cards) {
                        try {
                            characterCard.addLevelCard(CardUtils.copyCard(Catalog.getInstance()
                                    .get(levelCard)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            if (cardNode.getNodeName()
                    .equals("keywords")) {
                String keyWordsArgs[] = XmlUtils.getValue(cardNode)
                        .toUpperCase()
                        .split(",", -1);
                for (String kw : keyWordsArgs) {
                    card.addKeyword(Keyword.valueOf(kw.trim()));
                }
            }

            if (cardNode.getNodeName()
                    .equals("effects")) { // net.sachau.deathcrawl.cards.effects
                NodeList effectNodes = cardNode.getChildNodes();
                if (effectNodes.getLength() > 0) {
                    for (int i = 0; i < effectNodes.getLength(); i++) {
                        Node effectNode = effectNodes.item(i);
                        if ("effect".equals(effectNode.getNodeName())) {

                            String startEvent = XmlUtils.getAttributes(effectNode)
                                    .get("start");
                            String endEvent = XmlUtils.getAttributes(effectNode)
                                    .get("end");
                            String effectName = XmlUtils.getValue(effectNode)
                                    .trim()
                                    .toLowerCase();

                            Reflections refEffect = new Reflections(effectPrefix);
                            Set<Class<? extends CardEffect>> effects = refEffect.getSubTypesOf(CardEffect.class);
                            for (Class<? extends CardEffect> effectClass : effects) {
                                if (effectClass.getSimpleName()
                                        .toLowerCase()
                                        .equals(effectName)) {
                                    EffectTiming timing = new EffectTiming(!StringUtils.isEmpty(startEvent) ?
                                            GameEventContainer.Type.valueOf(startEvent.trim().toUpperCase()) : null,
                                            !StringUtils.isEmpty(endEvent) ? GameEventContainer.Type.valueOf(endEvent.trim().toUpperCase()) : null);

                                    CardEffect effect = effectClass.newInstance();
                                    effect.setEffectTiming(timing);
                                    if (effect instanceof KeywordEffect) {
                                        String keyword = XmlUtils.getAttributes(effectNode)
                                                .get("keyword");
                                        ((KeywordEffect) effect).setKeyword(Keyword.valueOf(keyword.trim().toUpperCase()));
                                    }

                                    card.getEffects()
                                            .add(effect);
                                }
                            }

                        }
                    }
                }
            }

            if (withTexts && cardNode.getNodeName()
                    .equals("text")) {
                CardText cardText = CardText.builder();
                TextParser.parse(cardNode, cardText);
                Catalog.putText(card.getName(), cardText.write());
            }

            if (cardNode.getNodeName()
                    .equals("illumination")) {
                if (card instanceof Illumination) {
                    ((Illumination) card).setIlluminationValue(NumberUtils.toInt(cardNode.getTextContent()
                            .trim()));
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

            Catalog.getInstance()
                    .add(card);
        }

        Logger.debug(card.getName() + ": " + Math.abs(new Date().getTime() - start));
        return card;
    }
}