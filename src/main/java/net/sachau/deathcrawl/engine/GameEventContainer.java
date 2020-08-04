package net.sachau.deathcrawl.engine;

import net.sachau.deathcrawl.card.Card;

public class GameEventContainer {
    public enum Type {
        NEWGAME,
        RANDOMPARTY,
        PARTYDONE,


        STARTTURN,
        STARTENCOUNTER,
        STARTCARDPHASE,
        ENDCARDPHASE, GUI_STARTENCOUNTER, EXPERIENCEPHASE, PARTYMOVE, CHARACTERDEATH, GAMEOVER,
        NEXTACTION,
        ACTIONDONE,
        WAITINGFORPLAYERACTION,
        PLAYERACTIONDONE,

        GUI_NEXTACTION,
        ENVIROMENTDEATH,
        ;

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
