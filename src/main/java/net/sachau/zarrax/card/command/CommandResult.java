package net.sachau.zarrax.card.command;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CommandResult {

    private boolean successful;
    private boolean allowed;
    private String message;
    private int hitChance;

    private CommandResult(boolean successful, boolean allowed, String message) {
        this.successful = successful;
        this.allowed = allowed;
        this.message = message;
    }

    public static CommandResult notAllowed(String message) {
        return new CommandResult(false, false, message);
    }

    public static CommandResult notSuccessful(String message, int hitChance) {
        CommandResult commandResult = new CommandResult(false, true, message);
        commandResult.setHitChance(hitChance);
        return commandResult;
    }

    public static CommandResult successful(String message) {
        return new CommandResult(true, true, message);
    }

    public static CommandResult successful() {
        return successful(null);
    }

    public static CommandResult allowed() {
        return new CommandResult(false, true, null);
    }

    public static CommandResult build(List<CommandResult> result) {
        if (result == null || result.size() == 0) {
            return notAllowed("build array is empty");
        }
        boolean success = true;
        boolean allowed = true;
        StringBuilder sb = new StringBuilder();
        for (CommandResult cr : result) {
            success &= cr.isSuccessful();
            allowed &= cr.isAllowed();
            if (!StringUtils.isEmpty(cr.getMessage())) {
                sb.append(cr.getMessage()).append("; ");
            }
        }
        return new CommandResult(success, allowed, sb.toString());
    }


    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHitChance() {
        return hitChance;
    }

    public void setHitChance(int hitChance) {
        this.hitChance = hitChance;
    }

    public boolean failed() {
        return !(isAllowed() && isSuccessful());
    }
}
