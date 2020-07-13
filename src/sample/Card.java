package sample;

import java.io.Serializable;

public class Card implements Serializable {

    private String name;
    private CardPosition position;
    private String command;

    public Card() {
    }

    public Card(String name, CardPosition position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardPosition getPosition() {
        return position;
    }

    public void setPosition(CardPosition position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", position=" + position +
                '}';
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
