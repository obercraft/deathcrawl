package net.sachau.zarrax.engine;

import net.sachau.zarrax.card.Card;

public class GameEventContainer {
    public enum Type {
        NEWGAME,
        RANDOMPARTY,
        PARTYDONE,


        START_TURN,
        END_TURN,
        START_ENCOUNTER,
        END_ENCOUNTER,
        START_CARDPHASE,
        END_CARDPHASE, GUI_STARTENCOUNTER, EXPERIENCEPHASE, PARTYMOVE, CHARACTERDEATH, GAMEOVER,
        NEXT_ACTION,
        ACTION_DONE,
        WAITING_FOR_AI_ACTION,
        WAITING_FOR_PLAYER_ACTION,
        PLAYERACTIONDONE,

        GUI_NEXTACTION,
        ENVIROMENTDEATH,
        WELCOME, START_MOVEMENT, END_MOVEMENT, PREPARE_ENCOUNTER;

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
