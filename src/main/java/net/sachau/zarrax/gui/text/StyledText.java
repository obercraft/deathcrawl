package net.sachau.zarrax.gui.text;

import javafx.scene.text.Text;

import java.util.Set;

public class StyledText {

    private String text;
    private Set<String> styles;

    public StyledText(String text) {
        this.text = text;
    }

    public StyledText(String text, String style) {
        this.text = text;
        this.styles.add(style);
    }


    public StyledText(String text, Set<String> styles) {
        this.text = text;
        this.styles = styles;
    }

    public Text get() {
        Text styleText = new Text(text);
        if (styles != null) {
            styleText.getStyleClass()
                    .addAll(styles);
        }
        return styleText;
    }

}

