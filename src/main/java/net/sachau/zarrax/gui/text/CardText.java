package net.sachau.zarrax.gui.text;

import javafx.scene.text.TextFlow;
import net.sachau.zarrax.gui.Symbol;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CardText {

    List<StyledText> parts = new LinkedList<>();
    boolean withWhitespaces = true;
    Set<String> styles = new HashSet<>();

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
                parts.add(new StyledText(arg + (withWhitespaces ? " " : ""), new HashSet<>(styles)));
            }
        }
        return this;

    }

    public CardText symbol(String symbol) {
        styleOffAllFonts();
        styleOn("font:symbol-12");
        parts.add(new StyledText(symbol, new HashSet<>(styles)));
        styleOff("font:symbol-12");
        return this;
    }

    private CardText styleOffAllFonts() {
        Set<String> fontStyles = new HashSet<>();
        for (String style : styles) {
            if (style.startsWith("font:")) {
                fontStyles.add(style);
            }
        }
        styles.removeAll(fontStyles);
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

    public CardText styleOn(String style) {
        styles.add(style);
        return this;
    }

    public CardText styleOff(String style) {
        styles.remove(style);
        return this;
    }

    public void reset() {
        styles.removeAll(styles);
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
