package net.sachau.zarrax.gui.text;


import net.sachau.zarrax.util.XmlUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.w3c.dom.Node;

public class TextParser {

    public static void parse(Node node, CardText textBuilder) {
        if (node == null) {
            return;
        }
        if (node.getChildNodes() == null ||node.getChildNodes().getLength() == 0) {
          // textBuilder.add(node.getTextContent().trim());
          return;
        }

        for (int i = 0; i < node.getChildNodes()
                .getLength(); i++) {
            Node child = node.getChildNodes()
                    .item(i);
            String nodeName = child.getNodeName();
            if (nodeName.matches("h\\d+")) {
                textBuilder.styleOn("font", "standard");
                int size = 12 * (4 - NumberUtils.toInt(nodeName.replace("h", ""), 1));
                textBuilder.styleOn("size", "" + size);
                parse(child, textBuilder);
                textBuilder.nl().styleOff("font").styleOff("size");


            } else if ("p".equals(nodeName)) {
                textBuilder.nl();
                parse(child, textBuilder);
                textBuilder.nl();
            } else if ("color".equalsIgnoreCase(nodeName)) {
                String colorName = XmlUtils.getAttributes(child).get("name");
                textBuilder.styleOn("color" , colorName);
                parse(child, textBuilder);
                textBuilder.styleOff("color");
            } else {
                textBuilder.add(child.getTextContent().trim());
                parse(child, textBuilder);
            }

        }
    }

}