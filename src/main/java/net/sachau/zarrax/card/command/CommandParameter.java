package net.sachau.zarrax.card.command;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

public class CommandParameter {


    private CommandTarget target;

    private List<String> arguments;

    public CommandParameter(List<String> arguments) {
        if (arguments != null && arguments.size() > 0) {
            target = getCommandTarget(arguments.get(0));
            arguments.remove(0);
            this.arguments = arguments;
        } else {
            target = getCommandTarget("");
        }

    }

    public CommandTarget getTarget() {
        return target;
    }

    public int getInt(int index) {
        return getInt(index, 0);
    }

    public String getString(int index) {
        if (arguments == null || arguments.size() < index) {
            return "";
        } else {
            return arguments.get(index);
        }
    }

    public int getInt(int index, int defaultValue) {
        if (arguments == null || index >= arguments.size() ) {
            return defaultValue;
        }
        return NumberUtils.toInt(arguments.get(0), defaultValue);
    }


    private CommandTarget getCommandTarget(String s) {
        if (StringUtils.isEmpty(s)) {
            return CommandTarget.TARGET;
        }
        if (s.trim().toLowerCase().contains("all")) {
            return CommandTarget.ALL;
        }

        if (s.trim().toLowerCase().contains("self")) {
            return CommandTarget.SELF;
        }

        if (s.trim().toLowerCase().contains("rand")) {
            return CommandTarget.RANDOM;
        }

        if (s.trim().toLowerCase().contains("adj")) {
            return CommandTarget.ADJACENT;
        }


        return CommandTarget.TARGET;
    }
}
