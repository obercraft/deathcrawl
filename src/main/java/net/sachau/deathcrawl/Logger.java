package net.sachau.deathcrawl;

import net.sachau.deathcrawl.gui.Console;

import java.io.PrintWriter;

public class Logger {

    private static final Level logLevel = Level.DEBUG;

    private enum Level {
        ERROR(0),
        INFO(1),
        DEBUG(2);

        private int priority;

        Level(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }
    }


    private static final Console console = new Console();

    public static void info(String text) {
        log(Level.INFO, text);
    }

    public static void debug(String text) {
        log(Level.DEBUG, text);
    }

    public static boolean isDebugEnabled() {
        return logLevel.getPriority() >= Level.DEBUG.getPriority();
    }


    public static Console getConsole() {
        return console;
    }

    private static void log(Level level, String text) {
        if (level == null) {
            return;
        }
        if (logLevel.getPriority() <= level.getPriority()) {
            console.appendText(text + "\n");
            if (logLevel == Level.DEBUG) {
                System.out.println(text);
            }
        }
    }

    public static void error(String text, Throwable e) {
        log(Level.ERROR, text);
        log(Level.ERROR, e.toString());
        if (logLevel == Level.DEBUG) {
            e.printStackTrace();
            //String s = new String();
            //PrintWriter pw = new PrintWriter(s);
            //e.printStackTrace(pw);
        }
    }

}
