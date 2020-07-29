package net.sachau.deathcrawl.commands;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class Command {

    public static CommandType getType(String command) {
        if (StringUtils.isNotEmpty(command)) {
            List<String> args = getArguments(command);
            if (args.size() > 0) {
                Commands c = Commands.valueOf(args.get(0));
                if (c != null) {
                    return c.getType();
                }
            }
        }
        return CommandType.ACTION;
    }

    public static List<String> getArguments(String command) {
        List<String> args = new LinkedList<>();
        if (StringUtils.isNotEmpty(command)) {
            for (String cmd : command.split(",", -1)) {
                args.add(cmd.trim());
            }
        }
        return args;
    }
}
