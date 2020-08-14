package net.sachau.zarrax.gui.text;

import javafx.scene.text.TextFlow;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CardText {

    List<StyledText> parts;
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

    public CardText b() {
        return toggleStyle("bold");
    }

    public CardText add(String string) {
        parts.add(new StyledText(string + (withWhitespaces ? " " :""), new HashSet<>(styles)));
        return this;

    }

    public CardText symbol(String symbol) {
        parts.add(new StyledText(symbol, "font:symbol-12"));
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
        parts.add(new StyledText("\n"));
        return write();
    }

    public CardText toggleStyle(String style) {
        if (styles.contains(style)) {
            styles.remove(style);
        } else {
            styles.add(style);
        }
        return this;
    }


}
