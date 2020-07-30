package net.sachau.deathcrawl.events;

import net.sachau.deathcrawl.Logger;

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

    public static Event.Type getType(Object arg) {
        if (arg instanceof Event) {
            return ((Event) arg).getType();
        }
        return null;
    }

    private Event stage;
    private Event lastStage;

    private GameEvent() {
        super();
    }

    public Event getStage() {
        return stage;
    }

    public void send(Event.Type type) {
        send(new Event(type));
    }
    public void send(Event stage) {
        lastStage = this.stage;
        this.stage = stage;
        Logger.debug(lastStage + "->" +  this.stage);
        setChanged();
        notifyObservers(stage);
    }

    public Event getLastStage() {
        return lastStage;
    }

    public Event.Type getType() {
        return this.stage.getType();
    }


}
