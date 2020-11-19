package net.sachau.zarrax;

import org.junit.Assert;
import org.junit.Test;

public class ConfigurationTest {

    Configuration configuration = new Configuration();

    @Test
    public void getInt() {
        Assert.assertNotEquals(0, configuration.getInt("zarrax.score.gold-ratio"));
    }
}