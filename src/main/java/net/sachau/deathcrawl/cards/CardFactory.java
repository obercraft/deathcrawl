package net.sachau.deathcrawl.cards;

import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class CardFactory {


    public static List<Card> createCards(String path) throws Exception {

        CardFactory factory = new CardFactory();
        List<String> files = factory.getResourceFiles(path);

        List <Card> cards = new LinkedList<>();

        if (files == null || files.size() == 0) {
            return cards;
        }
        for (String file : files) {
            Card card = CardParser.parseFor(path, file);
            cards.add(card);

        }
        return cards;
    }

    private List<String> getResourceFiles(String path) throws IOException {
        List<String> filenames = new LinkedList<>();

        try (
                InputStream in = getResourceAsStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;

            while ((resource = br.readLine()) != null) {
                filenames.add(resource);
            }
        }

        return filenames;
    }

    private InputStream getResourceAsStream(String resource) {
        final InputStream in
                = getContextClassLoader().getResourceAsStream(resource);

        return in == null ? getClass().getResourceAsStream(resource) : in;
    }

    private ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static void main(String args[]) throws Exception {
        List<Card> cards = CardFactory.createCards("/cards/characters/starting");

        return;


    }
}
