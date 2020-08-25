package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.engine.GameEventContainer;

import java.util.Objects;

public class EffectTiming {


    GameEventContainer.Type startPhase;

    GameEventContainer.Type endPhase;

    public EffectTiming(GameEventContainer.Type startPhase) {
        this.startPhase = startPhase;
        this.endPhase = startPhase;
    }

    public EffectTiming(GameEventContainer.Type startPhase, GameEventContainer.Type endPhase) {
        this.startPhase = startPhase;
        this.endPhase = endPhase;
    }

    public GameEventContainer.Type getStartPhase() {
        return startPhase;
    }

    public void setStartPhase(GameEventContainer.Type startPhase) {
        this.startPhase = startPhase;
    }

    public GameEventContainer.Type getEndPhase() {
        return endPhase;
    }

    public void setEndPhase(GameEventContainer.Type endPhase) {
        this.endPhase = endPhase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EffectTiming that = (EffectTiming) o;
        return startPhase == that.startPhase &&
                endPhase == that.endPhase;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPhase, endPhase);
    }
}
