package net.sachau.zarrax.encounter;

import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.map.*;
import net.sachau.zarrax.util.XmlUtils;
import net.sachau.zarrax.v2.GEngine;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
import java.util.HashMap;
import java.util.Map;

public class EncounterMatrixGenerator {

    private Catalog catalog = GEngine.getBean(Catalog.class);

    private static final String typePrefix = Encounters.class.getCanonicalName()
            .replace("Encounters", "");


    Map<TerrainWarning, Encounters> matrix = new HashMap<>();


    private static EncounterMatrixGenerator encounterMatrix;

    private EncounterMatrixGenerator() {

        try {
            parse(EncounterParser.class
                    .getResourceAsStream("/world/world.xml"), false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static EncounterMatrixGenerator getInstance() {
        if (encounterMatrix == null) {
            encounterMatrix = new EncounterMatrixGenerator();
        }
        return encounterMatrix;
    }


    private void parse(InputStream inputStream, boolean withTexts) throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        builder.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                if (systemId != null && systemId.contains("world.dtd")) {
                    InputSource source = new InputSource(this.getClass()
                            .getResourceAsStream("/world/world.dtd"));
                    return source;
                }
                return null;
            }
        });

        Document document = builder.parse(inputStream);

        Element root = document.getDocumentElement();

        EncounterMatrixGenerator encounterMatrix = this;
//        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
//            String nodeName = root.getChildNodes().item(i).getNodeName();
//            //parse(encounterMatrix, root.getChildNodes().item(i), withTexts);
//        }

        NodeList rootNodes = root.getChildNodes();
        for (int a = 0; a < rootNodes.getLength(); a++) {


            if ("terrains".equals(rootNodes.item(a).getNodeName())) {
                parseTerrain(rootNodes.item(a).getChildNodes());
            } else if ("sites".equals(rootNodes.item(a).getNodeName())) {
                parseSites(rootNodes.item(a).getChildNodes());
            } else if ("levels".equals(rootNodes.item(a).getNodeName())) {
                // parseLevels(rootNodes.item(a).getChildNodes());
            }

        }

    }

    private void parseSites(NodeList siteNodes) {

        for (int i = 0; i < siteNodes.getLength(); i++) {

            Node node = siteNodes.item(i);

            if (node.getNodeType() == Node.TEXT_NODE) {
                continue;
            }
            Map<String, String> attributes = XmlUtils.getAttributes(node);
            Site site = Site.valueOf(attributes.get("name").trim().toUpperCase());
            TerrainList terrainList = TerrainList.build().addFromString(attributes.get("terrain"));
            site.setPossibleTerrains(terrainList);
        }

    }
    private void parseTerrain(NodeList landNodes) {
        for (int i = 0; i < landNodes.getLength(); i++) {

            Node node = landNodes.item(i);

            if (node.getNodeType() == Node.TEXT_NODE) {
                continue;
            }
            Map<String, String> attributes = XmlUtils.getAttributes(node);

            Terrain terrain = Terrain.valueOf(attributes.get("name").trim().toUpperCase());
            float left = NumberUtils.toFloat(attributes.get("left"));
            float right = NumberUtils.toFloat(attributes.get("right"));
            int moveCost = NumberUtils.toInt(attributes.get("move"), 1);
            String tile = attributes.get("tile");
            terrain.setLeft(left);
            terrain.setRight(right);
            terrain.setMoveCost(moveCost);
            terrain.setTile(StringUtils.isEmpty(tile) ? "WHITE" : tile);
            for (int j = 0; j < node.getChildNodes().getLength(); j++) {
                if (node.getChildNodes().item(j).getNodeType() == Node.TEXT_NODE) {
                    continue;
                }
                addEncountersToLand(terrain, node.getChildNodes().item(j));


            }

        }
    }

    private void addEncountersToLand(Terrain terrain, Node item) {

        String[] warningArgs = XmlUtils.getAttributes(item).get("warning").split(",", -1);
        for (String warningArg : warningArgs) {
            TerrainWarning terrainWarning = new TerrainWarning(terrain, Warning.valueOf(warningArg.trim().toUpperCase()));

            Encounters encounters = new Encounters(catalog);
            encounters.addEncounters(item.getTextContent().trim());
            matrix.put(terrainWarning, encounters);
        }


    }

//    private void parse(EncounterMatrixGenerator encounterMatrix, Node node, boolean withTexts) throws IllegalAccessException, InstantiationException {
//
//        if (node.getNodeType() == Node.TEXT_NODE) {
//            return;
//        }
//
//
//        Map<String, String> attributes = XmlUtils.getAttributes(node);
//        String name = attributes.get("name");
//
//
//        for (int a = 0; a < node.getChildNodes()
//                .getLength(); a++) {
//            Node encounterNode = node.getChildNodes()
//                    .item(a);
//
//            if (encounterNode.getNodeType() == Node.TEXT_NODE) {
//                continue;
//            }
//
//
//            String warning = XmlUtils.getAttributes(encounterNode).get("warning");
//            if (node.getNodeName().equalsIgnoreCase("land")) {
//                float left = NumberUtils.toFloat(attributes.get("left"));
//                float right = NumberUtils.toFloat(attributes.get("right"));
//                int moveCost = NumberUtils.toInt(attributes.get("move"), 1);
//                encounterMatrix.addLand(te, warning, encounterNode.getTextContent());
//            } else if (node.getNodeName().equalsIgnoreCase("site")) {
//                encounterMatrix.addSiteEncounter(name, encounterNode.getTextContent());
//            }
//
//        }
//    }

//    public void addLand(Terrain terrain, String warningString, String encounterString) {
//        String[] warningArgs = warningString.split(",", -1);
//        for (String warningArg : warningArgs) {
//            Warning warning = warningMapping.get(warningArg.trim().toLowerCase());
//            TerrainWarning terrainWarning = new TerrainWarning(terrain, warning);
//            Encounters encounters = new Encounters();
//            encounters.addEncounters(encounterString);
//            matrix.put(terrainWarning, encounters);
//        }
//
//
//    }


}
