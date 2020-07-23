package net.sachau.deathcrawl.events;

import net.sachau.deathcrawl.Logger;

import java.util.Observable;
import java.util.Observer;

public class PlayCardObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        Logger.log("play card :" + o + "," + arg);
    }
}
