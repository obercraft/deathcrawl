package net.sachau.deathcrawl;

import java.util.Observable;

public class Game extends Observable {

    private static long id = 1;

    public static synchronized  long createId() {
        return id ++;
    }


    private static Game game;

    public static Game events() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    public static void addObserver(Observable o) {
        events().addObserver(o);
    }

    public static Event get(Object arg) {
        if (arg instanceof Event) {
            return (Event) arg;
        }
        return null;
    }

    private Event stage;

    private Game() {
    }

    public Event getStage() {
        return stage;
    }

    public void send(Event stage) {
        this.stage = stage;
        setChanged();
        notifyObservers(stage);
    }

}
