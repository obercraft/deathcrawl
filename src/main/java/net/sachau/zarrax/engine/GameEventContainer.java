package net.sachau.zarrax.engine;

import net.sachau.zarrax.card.Card;

public class GameEventContainer {
    public enum Type {
        NEWGAME,
        RANDOMPARTY,
        PARTYDONE,


        STARTTURN,
        ENDTURN,
        STARTENCOUNTER,
        STARTCARDPHASE,
        ENDCARDPHASE, GUI_STARTENCOUNTER, EXPERIENCEPHASE, PARTYMOVE, CHARACTERDEATH, GAMEOVER,
        NEXTACTION,
        ACTIONDONE,
        WAITINGFORPLAYERACTION,
        PLAYERACTIONDONE,

        GUI_NEXTACTION,
        ENVIROMENTDEATH,
        WELCOME;

    }

    private Type type;
    private Card card;

    public GameEventContainer(Type type) {
        this.type = type;
    }

    public GameEventContainer(Type type, Card card) {
        this.type = type;
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Event{" +
                "type=" + type +
                ", card=" + card +
                '}';
    }
}