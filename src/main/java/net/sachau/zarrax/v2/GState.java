package net.sachau.zarrax.v2;

import javafx.beans.property.SimpleIntegerProperty;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.map.World;

import java.util.List;

public class GState {

    private long id = 1;

    Player player;
    World world;

    private List<Card> initiativeOrder;
    private SimpleIntegerProperty currentInitiative = new SimpleIntegerProperty(0);


    public GState() {
    }

    public synchronized  long createId() {
        return id ++;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setCurrentInitiative(int value) {
        currentInitiative.set(value);
    }

    public List<Card> getInitiativeOrder() {
        return initiativeOrder;
    }

    public void setInitiativeOrder(List<Card> initiativeOrder) {
        this.initiativeOrder = initiativeOrder;
    }

    public int getCurrentInitiative() {
        return currentInitiative.get();
    }

    public SimpleIntegerProperty currentInitiativeProperty() {
        return currentInitiative;
    }

    public Card getCurrentCard() {
        if (getCurrentInitiative() >= initiativeOrder.size() ) {
            return null;
        }
        return initiativeOrder.get(getCurrentInitiative());
    }

}
