package net.sachau.zarrax.card.command;

public class CommandStage {

    private int hits;
    private String command;

    public CommandStage(int hits, String command) {
        this.hits = hits;
        this.command = command;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
