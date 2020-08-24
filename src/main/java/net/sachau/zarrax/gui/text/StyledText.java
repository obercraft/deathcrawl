package net.sachau.zarrax.gui.text;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.sachau.zarrax.gui.Fonts;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StyledText {

    private String text;
    private Map<String, String> styles = new HashMap<>();

    public StyledText(String text) {
        this.text = text;
    }

    public StyledText(String text, String style, String value) {
        this.text = text;
        this.styles.put(style, value);
    }


    public StyledText(String text, Map<String, String> styles) {
        this.text = text;
        this.styles = styles;
    }

    public Text get() {
        Text styleText = new Text(text);
        styleText.setFill(Color.BLACK);

        styleText.setFont(Fonts.getInstance().get("standard", 12));
        if (styles != null) {

            if (styles.get("font") != null) {
                styleText.setFont(Fonts.getInstance().get(styles.get("font"), NumberUtils.toInt(styles.get("size"), 12)));
            }
            if (styles.get("color") != null) {
                String colorName = styles.get("color").toUpperCase().trim();
                Color color = Color.valueOf(colorName);
                styleText.setFill(color);
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

