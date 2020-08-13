package net.sachau.deathcrawl.gui;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.sachau.deathcrawl.Main;
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

    public CardText bold(String string) {
        return add(string, "font:bold");
    }

    public CardText add(String string, String ...styleClasses) {
        Text text = new Text(string);
        boolean hasFont = false;
        if (styleClasses != null && styleClasses.length > 0) {
            for (String styleClass : styleClasses) {
                if (styleClass.startsWith("font:")) {
                    text.setFont(Fonts.getInstance().get(styleClass.replaceFirst("font:", "")));
                    hasFont = true;
                } else {
                    if (StringUtils.isNotEmpty(styleClass)) {
                        text.getStyleClass()
                                .add(styleClass);
                    }
                }
            }
        }
        if (!hasFont) {
            text.setFont(Fonts.getInstance()
                    .get("standard"));
        }

        parts.add(text);
        if (withWhitespaces) {
            parts.add(new Text(" "));
        }
        return this;

    }

    public CardText symbol(String symbol) {
        return add(symbol, "font:symbol-12");
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
