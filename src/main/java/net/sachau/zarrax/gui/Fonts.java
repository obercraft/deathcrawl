package net.sachau.zarrax.gui;

import javafx.scene.text.Font;
import net.sachau.zarrax.Logger;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Fonts {

    static Map<String, String> fontNames = new HashMap<>();
    static {
        fontNames.put("standard", "/fonts/LibreBaskerville-Regular.ttf");
        fontNames.put("symbol", "/fonts/fontawesome-solid-900.otf");
    }

    Map<String, Font> fontMap = new HashMap<>();

    private static Fonts fonts;

    public static Fonts getInstance() {
        if (fonts == null)  {
            fonts = new Fonts();
        }
        return fonts;
    }

    public Font get(String fontName, int size) {
        Font font = fontMap.get(fontName + "-" + size);
        if (font != null) {
            return font;
        }
        InputStream inputStream = Fonts.class.getResourceAsStream(fontNames.get(fontName));
        font = Font.loadFont(inputStream, size);
        if (font != null) {
            fontMap.put(fontName + "-" +size, font);
            Logger.debug("adding font " + fontName  + " " + size);
        } else {
            Logger.error("font for " + fontName + " not usable");
        }
        return font;
    }

}
