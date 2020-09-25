package net.sachau.zarrax.engine;

import net.sachau.zarrax.Logger;

import java.util.Observable;

public class GameEvent extends Observable {


    private static GameEvent gameEvent;

    public static GameEvent getInstance() {
        if (gameEvent == null) {
            gameEvent = new GameEvent();
        }
        return gameEvent;
    }

    public static void addObserver(Observable o) {
        getInstance().addObserver(o);
    }

    public static GameEventContainer.Type getType(Object arg) {
        if (arg instanceof GameEventContainer) {
            return ((GameEventContainer) arg).getType();
        }
        return null;
    }

    private GameEventContainer stage;
    private GameEventContainer lastStage;

    private GameEvent() {
        super();
    }

    public GameEventContainer getStage() {
        return stage;
    }

    public void send(GameEventContainer.Type type) {
        send(new GameEventContainer(type));
    }
    public void send(GameEventContainer stage) {
        lastStage = this.stage;
        this.stage = stage;
        Logger.debug(lastStage + "->" +  this.stage);
        setChanged();
        notifyObservers(stage);
    }

    public GameEventContainer getLastStage() {
        return lastStage;
    }

    public GameEventContainer.Type getType() {
        return this.stage.getType();
    }


    public Object getData() {
        return this.stage.getData();
    }
}
