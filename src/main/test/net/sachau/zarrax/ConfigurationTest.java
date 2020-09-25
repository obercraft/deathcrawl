package net.sachau.zarrax;

import org.junit.Assert;
import org.junit.Test;

public class ConfigurationTest {

    @Test
    public void getInt() {
        Assert.assertNotEquals(0, Configuration.getInstance().getInt("zarrax.score.gold-ratio"));
    }
}