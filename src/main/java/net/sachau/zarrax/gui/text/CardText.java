package net.sachau.zarrax.gui.text;

import javafx.scene.text.TextFlow;
import net.sachau.zarrax.gui.Symbol;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class CardText {

    List<StyledText> parts = new LinkedList<>();
    boolean withWhitespaces = true;
    Map<String, String> styles = new HashMap<>();

    public CardText() {
        this.parts = new LinkedList<>();
    }

    public CardText(boolean withWhitespaces) {
        this.withWhitespaces = withWhitespaces;
    }

    public static CardText builder() {
           return new CardText();
    }

    public static CardText builder(boolean withWhitespaces) {
        return new CardText(withWhitespaces);
    }

    public CardText ws() {
        parts.add(new StyledText(" "));
        return this;
    }

    public CardText add(String string) {
        if (StringUtils.isEmpty(string)) {
            return this;
        }
        String[] args = string.split("\\s+", -1);
        for (String arg : args) {
            if ("$EXHAUSTED$".equals(arg)) {
                    symbol(Symbol.FA_HAND_HOLDING.getText());
            } else {
                parts.add(new StyledText(arg + (withWhitespaces ? " " : ""), new HashMap<>(styles)));
            }
        }
        return this;

    }

    public CardText symbol(String symbol) {
        styleOffAllFonts();
        styleOn("font", "symbol");
        if (!styles.containsKey("size")) {
            styleOn("size", "12");
        }
        parts.add(new StyledText(symbol, new HashMap<>(styles)));
        styleOff("font");
        styleOff("size");
        return this;
    }

    private CardText styleOffAllFonts() {
        for (Map.Entry<String, String> style : styles.entrySet()) {
            if (style.getKey().equalsIgnoreCase("font")) {
                styles.remove(style.getKey());
            }
        }

        return this;
    }

    public TextFlow write() {
        TextFlow textFlow = new TextFlow();
        for (StyledText styledText : parts) {
            textFlow.getChildren().add(styledText.get());
        }
        return textFlow;
    }

    public TextFlow writeln() {
        nl();
        return write();
    }

    public CardText styleOn(String style, String value) {
        styles.put(style, value);
        return this;
    }

    public CardText styleOff(String style) {
        styles.remove(style);
        return this;
    }

    public void reset() {
        for (String key : styles.keySet()) {
            styles.remove(key);
        }
    }

    public CardText nl() {
        return nl(false);
    }

    public CardText nl(boolean force) {
        try {
            if (force || parts.size() == 0 || !parts.get(parts.size() - 1).getText().endsWith("\n")) {
                parts.add(new StyledText("\n"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}
