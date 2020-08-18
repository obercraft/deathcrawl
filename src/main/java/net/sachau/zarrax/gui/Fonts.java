package net.sachau.zarrax.gui;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Font;
import net.sachau.zarrax.InitTask;
import net.sachau.zarrax.Logger;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Fonts {

    Map<String, Font> fontMap = new HashMap<>();

    private static Fonts fonts;

    public static Fonts getInstance() {
        if (fonts == null)  {
            fonts = new Fonts();
        }
        return fonts;
    }

    public Fonts load(String mapName, InputStream inputStream, int size) {
        Font font = Font.loadFont(inputStream, size);
        if (font != null) {
            fontMap.put(mapName, font);
            Logger.debug("adding font " + mapName);
        } else {
            Logger.error("font for " + mapName + " not usable");
        }
        return this;
    }

    public Font get(String mapName) {
        Font font = fontMap.get(mapName);
        return font != null ? font : fontMap.get("standard");
    }



    public Fonts progress(InitTask initTask, int i) {
        initTask.update(i);
        return this;
    }
}
