package net.sachau.deathcrawl.cards;

import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import net.sachau.deathcrawl.cards.catalog.Catalog;
import net.sachau.deathcrawl.effects.Exhausted;
import net.sachau.deathcrawl.events.Event;
import net.sachau.deathcrawl.GameEngine;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.commands.Command;
import net.sachau.deathcrawl.commands.CommandType;
import net.sachau.deathcrawl.dto.Creature;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.effects.Armor;
import net.sachau.deathcrawl.effects.CardEffect;
import net.sachau.deathcrawl.effects.Prone;
import net.sachau.deathcrawl.keywords.Keyword;
import net.sachau.deathcrawl.keywords.Keywords;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public abstract class Card {

    public enum Source {
        NONE,
        HAND,
        PARTY,
    };
    private long id;
    private String name;
    private String uniqueId;
    private Map<Event.Type, List<CardEffect>> effects;
    private Keywords keywords = new Keywords();
    private String command;


    private SimpleIntegerProperty damage = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty hits = new SimpleIntegerProperty(1);
    private SimpleIntegerProperty skill = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty maxHits = new SimpleIntegerProperty(1);
    private SimpleBooleanProperty visible = new SimpleBooleanProperty();
    private SimpleBooleanProperty active = new SimpleBooleanProperty(false);

    private Creature owner;
    private Source source;
    private SetProperty<CardEffect> conditions;

    public Card() {
        super();
        initHits(1);
        this.id = GameEngine.createId();
        this.effects = new HashMap<>();
        ObservableSet<CardEffect> observableSet = FXCollections.observableSet(new HashSet<>());
        this.conditions = new SimpleSetProperty<>(observableSet);

        this.hitsProperty().addListener(new HitsListener(this));
        Catalog.putById(this);
//        Card card = this;
//        this.hitsProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                if (newValue != null && newValue.intValue() <= 0) {
//
//                    Logger.debug(card + " killed");
//
//
//                    if (card instanceof Monster && owner != null) {
//                        Monster mc = (Monster) card;
//                        int gold = mc.getGold() + owner.getGold();
//                        owner.setGold(gold);
//                        int xp = mc.getXp() + owner.getXp();
//                        owner.setXp(xp);
//
//                        Logger.debug(owner + " gains " + gold + " gold & " + xp + " XP");
//                    }
//                    GameEvent.getInstance().send(new Event(Event.Type.CHARACTERDEATH, card));
//                }
//
//            }
//        });
    }

    public Card(Card card) {
        super();
        this.id = GameEngine.createId();
        this.effects = new HashMap<>();
        this.name = card.name;
        this.uniqueId = card.uniqueId;
        if (card.effects != null) {
            this.effects = new HashMap<>();
            for (Map.Entry<Event.Type, List<CardEffect>> entry : card.effects.entrySet()) {
                this.effects.put(entry.getKey(), entry.getValue());
            }
        }
        this.keywords = new Keywords();
        if (card.keywords != null) {
            for (Keyword kw : card.getKeywords()) {
                this.keywords.add(kw);
            }
        }

        this.command = card.command;

        this.setDamage(card.getDamage());
        this.setHits(card.getHits());
        this.setMaxHits(card.getMaxHits());
        this.setVisible(card.isVisible());

        this.owner = card.owner;
        this.source = card.source;

        ObservableSet<CardEffect> observableSet = FXCollections.observableSet(new HashSet<>());
        this.conditions = new SimpleSetProperty<>(observableSet);
        if (card.conditions != null) {
            for (CardEffect condition : card.conditions) {
                this.conditions.add(condition);
            }
        }

        this.setActive(card.isActive());

        this.hitsProperty().addListener(new HitsListener(this));

        Catalog.putById(this);
    }

    public Card(int initialHits, int initialDamage) {
        super();
        initHits(initialHits);
        initDamage(initialDamage);
        this.id = GameEngine.createId();
        this.effects = new HashMap<>();
        ObservableSet<CardEffect> observableSet = FXCollections.observableSet(new HashSet<>());
        this.conditions = new SimpleSetProperty<>(observableSet);

    }

    private List<CardEffect> getPhaseEffects(Event.Type event) {
        return effects.get(event) != null ? effects.get(event) : new LinkedList<>();
    }

    public void triggerPhaseEffects(Event.Type event) {
        List<CardEffect> phaseCardEffects = getPhaseEffects(event);
        if (phaseCardEffects != null) {
            for (CardEffect e : phaseCardEffects) {
                e.trigger(null, this);
            }
        }
    }


    public void addEffect(Event.Type event, CardEffect cardEffect) {
        if (effects.get(event) == null) {
            effects.put(event, new LinkedList<>());
        }
        effects.get(event)
                .add(cardEffect);

    }

    public void setEffects(Map<Event.Type, List<CardEffect>> effects) {
        this.effects = effects;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isVisible() {
        return visible.get();
    }

    public SimpleBooleanProperty visibleProperty() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible.set(visible);
    }

    public Creature getOwner() {
        return owner;
    }

    public void setOwner(Creature owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id &&
                name.equals(card.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "{Card@" + id + "}";
    }

    public int getHits() {
        return hits.get();
    }

    public SimpleIntegerProperty hitsProperty() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits.set(hits);
    }

    public boolean attack(Card target, int attack) {

        if (target == null) {
            Logger.debug("target is null");
            return false;
        }

        if (hasCondition(Prone.class)) {
            Logger.debug(this + " is prone");;
            return false;
        }

        if (attack <=0) {
            Logger.debug("damage is less than 1");
            return true;
        }

        // TODO msachau armor
//        Deck targetDeck = target.getDeck();
//        for (Card card : targetDeck.getCards()) {
//            if (card.isAlive() && card.getId() != target.getId() && card.hasCondition(Armor.class)) {
//                Logger.debug(target + " is guarded by " + card);
//                return false;
//            }
//        }

        if (target.hasCondition(Armor.class)) {
            target.removeCondition(Armor.class);
            Logger.debug(target + " saved by armor");
            return true;
        }

        int hits = target.getHits();

        if (attack > 0) {
            hits -= attack;
        }

        target.setHits(hits);
        Logger.debug(target + " hit for " + attack + " damage");
        return true;

    }


    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Keywords getKeywords() {
        return keywords;
    }

    public void addKeywords(Keyword... keywords) {
        for (Keyword k : keywords) {
            this.keywords.add(k);
        }
    }

    public boolean isPlayable() {

        if (this.hasCondition(Exhausted.class)) {
            Logger.debug(this + " is exhausted");
            return false;
        }

        if (this.isActive()) {
            return true;
        }
        Card currentCard = GameEngine.getInstance().getCurrentCard();
        if (currentCard != null) {

            if (this.getKeywords().contains(Keyword.SIMPLE)) {
                return true;
            } else {
                return currentCard.hasOneKeyword(this.getKeywords());
            }
        }
        return false;
    }

    public boolean hasOneKeyword(Keywords keywords) {
        for (Keyword kw : keywords) {
            if (getKeywords().contains(kw)) {
                return true;
            }
        }
        return false;
    }

    public boolean heal(int amount) {
        int h = amount + getHits();
        if (getHits() + h <= getMaxHits()) {
            setHits(h);
            return true;
        } else {
            setHits(getMaxHits());
            return false;
        }
    }

    public int getMaxHits() {
        return maxHits.get();
    }

    public SimpleIntegerProperty maxHitsProperty() {
        return maxHits;
    }

    public void setMaxHits(int maxHits) {
        this.maxHits.set(maxHits);
    }

    public int getDamage() {
        return damage.get();
    }

    public SimpleIntegerProperty damageProperty() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage.set(damage);
    }

    public void setKeywords(Keywords keywords) {
        this.keywords = keywords;
    }

    public void initHits(int hits) {
        setHits(hits);
        setMaxHits(hits);
    }

    public void initDamage(int damage) {
        setDamage(damage);
    }

    public boolean hasAllKeywords(Set<Keyword> wantedKeywords) {
        if (wantedKeywords == null || wantedKeywords.size() == 0) {
            return true;
        }
        Set<Keyword> foundKeywords = new HashSet<>();
        for (Keyword k : wantedKeywords) {
            if (getKeywords().contains(k)) {
                foundKeywords.add(k);
                if (wantedKeywords.size() == foundKeywords.size()) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getCardKeyWords(boolean all) {
        if (this.getKeywords() == null) {
            return "";
        }
        Set<String> words = new TreeSet<>();
        for (Keyword k : this.getKeywords()) {
            if (all || k.isOnCard()) {
                words.add(k.name());
            }
        }

        return StringUtils.join(words, " \u00b7 ");
    }

    public boolean isRanged() {
        return  keywords.contains(Keyword.RANGED);
    }

    public boolean hasCondition(Class<? extends CardEffect> clazz) {
        for (CardEffect cardEffect : getConditions()) {
            if (cardEffect.getClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    public CardEffect removeCondition(CardEffect effect) {
        CardEffect removeCondition = null;
        for (CardEffect cardEffect : getConditions()) {
            if (cardEffect == effect) {
                removeCondition = cardEffect;
            }
        }
        if (removeCondition != null) {
            removeCondition.remove(this);
            getConditions().remove(removeCondition);
        }
        return removeCondition;
    }


    public CardEffect removeCondition(Class<? extends CardEffect> clazz) {
        CardEffect removeCondition = null;
        for (CardEffect cardEffect : getConditions()) {
            if (cardEffect.getClass().equals(clazz)) {
                removeCondition = cardEffect;
            }
        }
        if (removeCondition != null) {
            Logger.debug("remove " + clazz + " from " + this);
            removeCondition.remove(this);
            getConditions().remove(removeCondition);
        }
        return removeCondition;
    }


    public boolean isAlive() {
        return getHits() > 0;
    }

    public Player getPlayer() {
        if (getOwner() != null && getOwner() instanceof Player) {
            return  (Player) getOwner();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public ObservableSet<CardEffect> getConditions() {
        return conditions.get();
    }

    public SetProperty<CardEffect> conditionsProperty() {
        return conditions;
    }

    public void setConditions(ObservableSet<CardEffect> conditions) {
        this.conditions.set(conditions);
    }

    public  boolean hasKeyword(Keyword keyword) {
        return this.getKeywords().contains(keyword);
    }

    public boolean isActive() {
        return active.get();
    }

    public SimpleBooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }


    public int getSkill() {
        return skill.get();
    }

    public SimpleIntegerProperty skillProperty() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill.set(skill);
    }

    public void initSkill(int skill) {
        this.setSkill(skill);
    }


}

