package net.sachau.zarrax.util;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

public class XmlUtils {


    public static String getValue(Node node) {
        return node != null ? node.getTextContent().trim() : "";
    }
    public static Map<String, String> getAttributes(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        Map<String, String> attributeMap = new HashMap<>();
        for (int j = 0; j < attributes.getLength(); j++) {
            Node attribute = attributes.item(j);
                attributeMap.put(attribute.getNodeName(), attribute.getNodeValue().trim());

        }
        return attributeMap;
    }
}
