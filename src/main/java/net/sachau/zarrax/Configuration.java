package net.sachau.zarrax;

import net.sachau.zarrax.engine.GameData;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.util.Properties;

@GameData
public class Configuration {

    private Properties properties;

    public Configuration() {
        properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/zarrax.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        String val = properties.getProperty(key);
        return NumberUtils.toInt(val, defaultValue);
    }

}
