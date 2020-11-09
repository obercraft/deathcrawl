package net.sachau.zarrax.v2;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.engine.GameEventContainer;

import java.util.Observable;

public class GEvents extends Observable {


    public static GameEventContainer.Type getType(Object arg) {
        if (arg instanceof GameEventContainer) {
            return ((GameEventContainer) arg).getType();
        }
        return null;
    }

    private GameEventContainer stage;
    private GameEventContainer lastStage;

    public GEvents() {
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
