package net.sachau.zarrax.gui.text;


import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

public class TextParser {

    public static void parse(Node node, CardText textBuilder) {
        if (node == null) {
            return;
        }

        if (node.getChildNodes().getLength() == 0) {
            String txt = node.getTextContent().trim();
            if (StringUtils.isNotEmpty(txt)) {
                textBuilder.add(txt);
            }
            return;
        } else {
            for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                Node child = node.getChildNodes().item(i);
                String nodeName = child.getNodeName();
                if ("h1".equals(nodeName)) {
                    textBuilder.toggleStyle("font:h1").add(child.getTextContent().trim()).writeln();
                } else if ("p".equals(nodeName)) {
                    textBuilder.nl().add(child.getTextContent().trim()).nl().write();
                } else {
                    textBuilder.reset();
                    parse(child, textBuilder);
                }

            }
        }
    }
}