package net.sachau.zarrax.engine;

import net.sachau.zarrax.card.Card;

public class GameEventContainer {
    public enum Type {
        CREATE_GAME,
        RANDOMPARTY,
        PARTYDONE,
        LANDINFO,


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
    private Object data;

    public GameEventContainer(Type type) {
        this.type = type;
    }

    public GameEventContainer(Type type, Object data) {
        this.type = type;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Event{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }
}
