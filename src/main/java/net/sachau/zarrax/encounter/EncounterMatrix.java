package net.sachau.zarrax.encounter;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.type.Encounter;
import net.sachau.zarrax.map.*;
import net.sachau.zarrax.util.XmlUtils;
import org.reflections.Reflections;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EncounterMatrix {

    private static final String typePrefix = Encounters.class.getCanonicalName()
            .replace("Encounters", "");

    Map<String, Land> landMapping = new HashMap<>();

    Map<String, Warning> warningMapping = new HashMap<>();

    private Map<String, Site> siteMapping = new HashMap<>();


    private static EncounterMatrix encounterMatrix;

    private Set<Site> sites = new HashSet<>();

    private Set<Land> lands = new HashSet<>();

    private EncounterMatrix() {

        {
            String s =typePrefix + ".lands";
            Reflections reflections = new Reflections(typePrefix + "lands");
            Set<Class<? extends Land>> landClasses = reflections.getSubTypesOf(Land.class);

            for (Class<? extends Land> landClass : landClasses) {
                try {
                    landMapping.put(landClass.getSimpleName().toLowerCase(), landClass.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        {
            Reflections reflections = new Reflections(typePrefix + "warnings");
            Set<Class<? extends Warning>> warningClasses = reflections.getSubTypesOf(Warning.class);
            for (Class<? extends Warning> warningClass : warningClasses) {
                try {
                    warningMapping.put(warningClass.getSimpleName().toLowerCase(), warningClass.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        {
            Reflections reflections = new Reflections(typePrefix + "sites");
            Set<Class<? extends Site>> siteClasses = reflections.getSubTypesOf(Site.class);
            for (Class<? extends Site> siteClass : siteClasses) {
                try {
                    siteMapping.put(siteClass.getSimpleName().toLowerCase(), siteClass.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            parse();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static EncounterMatrix getInstance() {
        if (encounterMatrix == null) {
            encounterMatrix = new EncounterMatrix();
        }
        return encounterMatrix;
    }

    public void addLand(String landName, String warningString, String encounterString) {
        Land land = landMapping.get(landName.trim().toLowerCase());
        String [] warningArgs = warningString.split(",", -1);
        for (String warningArg : warningArgs) {
            Warning warning = warningMapping.get(warningArg.trim().toLowerCase());
            if (warning != null) {
                Encounters encounters = new Encounters();
                encounters.addEncounters(encounterString);
                land.getEncounters().put(warning, encounters);
            } else {
                Logger.debug("warning " + warningArg + " not found");
            }
        }
        lands.add(land);




    }

    public void addSiteEncounter(String siteName, String encounterString) {



    }

    public Set<Site> getSites() {
        return sites;
    }

    public Set<Land> getLands() {
        return lands;
    }



    private void parse() throws Exception {
        parse(EncounterParser.class
                .getResourceAsStream("/encounters/encounter-matrix.xml"), false);

    }

    private void parse(InputStream inputStream, boolean withTexts) throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        builder.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                if (systemId != null && systemId.contains("encounter-matrix.dtd")) {
                    InputSource source = new InputSource(this.getClass()
                            .getResourceAsStream("/encounters/encounter-matrix.dtd"));
                    return source;
                }
                return null;
            }
        });

        Document document = builder.parse(inputStream);

        Element root = document.getDocumentElement();

        EncounterMatrix encounterMatrix = this;
//        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
//            String nodeName = root.getChildNodes().item(i).getNodeName();
//            //parse(encounterMatrix, root.getChildNodes().item(i), withTexts);
//        }

        NodeList nodeList = root.getElementsByTagName("encounters");
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList encounterList = nodeList.item(i).getChildNodes();
            for (int j = 0; j < encounterList.getLength(); j++) {
                parse(encounterMatrix, encounterList.item(j), withTexts);
            }
        }

    }

    private void parse(EncounterMatrix encounterMatrix, Node node, boolean withTexts) throws IllegalAccessException, InstantiationException {

        if (node.getNodeType() == Node.TEXT_NODE) {
            return;
        }

        String name = XmlUtils.getAttributes(node).get("name");


        for (int a = 0; a < node.getChildNodes()
                .getLength(); a++) {
            Node encounterNode = node.getChildNodes()
                    .item(a);

            if (encounterNode.getNodeType() == Node.TEXT_NODE) {
                continue;
            }


            String warning = XmlUtils.getAttributes(encounterNode).get("warning");
            if (node.getNodeName().equalsIgnoreCase("land")) {
                encounterMatrix.addLand(name, warning, encounterNode.getTextContent());
            } else if (node.getNodeName().equalsIgnoreCase("site")) {
                // encounterMatrix.addSiteEncounter(name, warning, encounterNode.getTextContent());
            }

        }
    }

}
