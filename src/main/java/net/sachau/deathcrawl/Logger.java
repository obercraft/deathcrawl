package net.sachau.deathcrawl;

import net.sachau.deathcrawl.gui.Console;

public class Logger {

    private static final Console console = new Console();

    public static void log(String text) {
        console.appendText(text+"\n");
    }

    public static Console getConsole() {
        return console;
    }
}
