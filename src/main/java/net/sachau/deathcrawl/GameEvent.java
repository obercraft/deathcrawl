package net.sachau.deathcrawl;

import java.util.Observable;

public class GameEvent extends Observable {


    public enum Type {
        NEWGAME,
        RANDOMPARTY,
        PARTYDONE,
        STARTTURN,
        STARTENCOUNTER,
        STARTCARDPHASE,
        ENDCARDPHASE, STARTENCOUNTERVIEW, EXPERIENCEPHASE, PARTYMOVE, CHARACTERDEATH, GAMEOVER,

    }


    private static long id = 1;

    public static synchronized  long createId() {
        return id ++;
    }


    private static GameEvent gameEvent;

    public static GameEvent events() {
        if (gameEvent == null) {
            gameEvent = new GameEvent();
        }
        return gameEvent;
    }

    public static Type get(Object arg) {
        if (arg instanceof GameEvent.Type) {
            return (Type) arg;
        }
        return null;
    }

    private Type stage;

    private GameEvent() {
    }

    public Type getStage() {
        return stage;
    }

    public void send(Type stage) {
        this.stage = stage;
        setChanged();
        notifyObservers(stage);
    }

}
