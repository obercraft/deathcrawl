package net.sachau.zarrax.gui.text;

import javafx.scene.text.Text;
import net.sachau.zarrax.gui.Fonts;

import java.util.HashSet;
import java.util.Set;

public class StyledText {

    private String text;
    private Set<String> styles = new HashSet<>();

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
            boolean hasFont = false;
            for (String style : styles) {
                if (style.startsWith("font:")) {
                    styleText.setFont(Fonts.getInstance().get(style.replaceFirst("font:", "")));
                    hasFont = true;
                } else {
                    styleText.getStyleClass()
                            .add(style);

                }
            }
            if (! hasFont) {
                styleText.setFont(Fonts.getInstance().get("standard"));
            }
        }

        return styleText;
    }

    @Override
    public String toString() {
        return "StyledText{" +
                "text='" + text + '\'' +
                ", styles=" + styles +
                '}';
    }

    public String getText() {
        return text;
    }
}

