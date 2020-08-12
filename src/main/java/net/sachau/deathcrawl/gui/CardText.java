package net.sachau.deathcrawl.gui;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class CardText {

    List<Text> parts;
    boolean withWhitespaces = true;

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
        parts.add(new Text(" "));
        return this;
    }

    public CardText add(String string, String ...styleClasses) {
        Text text = new Text(string);
        if (styleClasses != null) {
            for (String styleClass : styleClasses) {
                if (StringUtils.isNotEmpty(styleClass)) {
                    text.getStyleClass().add(styleClass);
                }
            }
        }
        parts.add(text);
        if (withWhitespaces) {
            add(" ");
        }
        return this;

    }

    public CardText icon(String symbol) {
        return add(symbol, "icons");
    }

    public TextFlow write() {
        TextFlow textFlow = new TextFlow();
        textFlow.getChildren().addAll(parts);
        return textFlow;
    }

    public TextFlow writeln() {
        parts.add(new Text("\n"));
        return write();
    }


}
