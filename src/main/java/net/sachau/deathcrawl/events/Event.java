package net.sachau.deathcrawl.events;

import net.sachau.deathcrawl.cards.Card;

public class Event {
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

    public Event(Type type) {
        this.type = type;
    }

    public Event(Type type, Card card) {
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
