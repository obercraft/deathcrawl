package net.sachau.deathcrawl.gui.card;

import javafx.scene.text.Text;
import net.sachau.deathcrawl.cards.Card;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class CardDesignParser {


    public static CardDesign parseFor(Card card) throws Exception {

        CardDesign cardDesign = new CardDesign();

        cardDesign.setName(card.getClass().getSimpleName());

        InputStream resource = card.getClass().getResourceAsStream("/card-design/" + card.getClass().getSimpleName().toLowerCase() + ".xml");

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

        NodeList name = root.getElementsByTagName("name");
        if (name != null && name.getLength() > 0) {
            String cardName =  name.item(0).getTextContent().trim();
            if (StringUtils.isNotEmpty(cardName)) {
                cardDesign.setName(cardName);
            }
        }

        NodeList text = root.getElementsByTagName("text");
        for (int i = 0; i < text.getLength(); i++) {
            Node t = text.item(i);
            NodeList nodeList = t.getChildNodes();
            for (int j=0; j < nodeList.getLength(); j++) {
                Node content = nodeList.item(j);
                String nodeName = content.getNodeName().toLowerCase();
                if ("#text".equals(nodeName)) {
                    String value = content.getNodeValue().trim();
                    cardDesign.getCardText().add(new Text(cardDesign.getCardText().size() > 0 ? " " + value : value));
                } else if ("b".equals(nodeName)) {
                    String value = content.getTextContent().trim();
                    Text txt = new Text(cardDesign.getCardText().size() > 0 ? " " + value : value);
                    txt.getStyleClass().add("card-text-bold");
                    cardDesign.getCardText().add(txt);
                }
            }
        }

        return cardDesign;
    }

}
