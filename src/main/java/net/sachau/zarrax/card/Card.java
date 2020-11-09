package net.sachau.zarrax.card;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.command.CommandResult;
import net.sachau.zarrax.card.effect.CardEffect;
import net.sachau.zarrax.card.effect.Guard;
import net.sachau.zarrax.card.effect.KeywordEffect;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.card.type.Illumination;
import net.sachau.zarrax.engine.ApplicationContext;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.util.DiceUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public abstract class Card {



    public enum Source {
        NONE,
        HAND,
        PARTY,
    }

    ;
    private long id;
    private String name;
    private String uniqueId;
    private Set<CardEffect> effects;
    private String command;


    private SimpleIntegerProperty damage = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty hits = new SimpleIntegerProperty(1);
    private SimpleIntegerProperty skill = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty maxHits = new SimpleIntegerProperty(1);
    private SimpleBooleanProperty visible = new SimpleBooleanProperty();
    private SimpleBooleanProperty active = new SimpleBooleanProperty(false);

    private Creature owner;
    private Source source;

    public Card() {
        super();
        initHits(1);
        this.id = GameEngine.createId();
        this.effects = new HashSet<>();

        this.hitsProperty()
                .addListener(new HitsListener(this));

//        ApplicationContext.getCatalog().putById(this);
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

        this.name = card.name;
        this.uniqueId = card.uniqueId;
        if (card.effects != null) {
            this.effects = new HashSet<>(card.getEffects());
        } else {
            this.effects = new HashSet<>();
        }

        this.command = card.command;

        this.setDamage(card.getDamage());
        this.setHits(card.getHits());
        this.setMaxHits(card.getMaxHits());
        this.setVisible(card.isVisible());

        this.owner = card.owner;
        this.source = card.source;

        this.setActive(card.isActive());

        this.hitsProperty()
                .addListener(new HitsListener(this));

//        ApplicationContext.getCatalog().putById(this);
    }

    public Card(int initialHits, int initialDamage) {
        super();
        initHits(initialHits);
        initDamage(initialDamage);
        this.id = GameEngine.createId();
        this.effects = new HashSet<>();


    }

    public void triggerStartEffects(GameEventContainer.Type event) {
        if (effects ==  null || effects.size() == 0) {
            return;
        }
        for (CardEffect effect : effects) {
            if (effect.getEffectTiming() != null && event.equals(effect.getEffectTiming().getStartPhase())) {
                effect.start(this);
            }
        }

    }

    public void triggerEndEffects(GameEventContainer.Type event) {
        if (effects ==  null || effects.size() == 0) {
            return;
        }
        for (CardEffect effect : effects) {
            if (effect.getEffectTiming() != null && event.equals(effect.getEffectTiming().getEndPhase())) {
                effect.tick(this);

            }
        }

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
        return "{Card@" + id + ":" + name + "}";
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

    public CommandResult attack(Card target, int attack) {
        return attack(target, attack, false);
    }
    private  CommandResult attack(Card target, int attack, boolean isRetaliate) {

        if (target == null) {
            return CommandResult.notAllowed("target is null");
        }

        if (hasKeyword(Keyword.PRONE)) {
            return CommandResult.notAllowed(this + " is prone");
        }

        if (attack <= 0) {
            return CommandResult.notAllowed("damage is less than 1");
        }


        boolean ranged = this.hasKeyword(Keyword.RANGED);

        if (ranged) {
            int rangeHitChance = 100; // percent
            if (target.hasKeyword(Keyword.SMALL)) {
                rangeHitChance -= 25;
            }
            boolean hasBadLight = false;
            if (this.hasKeyword(Keyword.BLIND)) {
                rangeHitChance -= 60;
                hasBadLight = true;
            } else if (this.hasKeyword(Keyword.DARKNESS)) {
                rangeHitChance -= 30;
                hasBadLight = true;
            }
            if (hasBadLight) {
                Player player = ApplicationContext.getPlayer();
                if (this.owner instanceof Player) {

                    for (Card c : player.getParty()) {
                        if (c instanceof Illumination) {
                            rangeHitChance += ((Illumination) c).getIlluminationValue();
                        }
                    }
                } else {
                    for (Card c : player.getHazards()) {
                        if (c instanceof Illumination) {
                            rangeHitChance += ((Illumination) c).getIlluminationValue();
                        }
                    }

                }
            }

            int hitChance = Math.min(100, rangeHitChance);
            if (!DiceUtils.percentage(hitChance)) {
                return CommandResult.notSuccessful("ranged " + this + "attack on "+ target + " failed", hitChance);
            }
        }

        if (target.hasKeyword(Keyword.FLYING) && !ranged) {
            return CommandResult.notAllowed("cannot attack flying target with ranged");
        }

        // not ranged and target is not a guard and target and target is guarded
        if (!ranged && !target.hasEffect(Guard.class) && target.hasKeyword(Keyword.GUARDED)) {
            return CommandResult.notAllowed(target + " is guarded");
        }


//
//            List<Card> possibleGuards;
//            if (target.getOwner() instanceof Player) {
//                possibleGuards = ((Player) target.getOwner()).getParty();
//            } else {
//                possibleGuards = GameEngine.getInstance()
//                        .getPlayer()
//                        .getHazards();
//            }
//
//            if (possibleGuards != null) {
//                for (Card guard : possibleGuards) {
//                    if (guard.isAlive() && guard.getId() != target.getId() && guard.hasKeyword(Keyword.GUARDED)) {
//                        Logger.debug(target + " is guarded by " + guard);
//                        return false;
//                    }
//                }
//            }
//        }

        if (target.hasKeyword(Keyword.ARMOR)) {
            target.removeKeyword(Keyword.ARMOR);
            return CommandResult.successful(target + " saved by armor");
        }

        int hits = target.getHits();

        if (attack > 0) {
            hits -= attack;
        }

        target.setHits(Math.max(0,hits));
        Logger.debug(this + (isRetaliate ? " retaliates " : " attacks " ) + target + " for " + attack + " damage");
        if (target.hasKeyword(Keyword.RETALIATE)) {

            Card retaliateTarget = ApplicationContext.getGameEngine().getCurrentCard();
            if (retaliateTarget != null && retaliateTarget.hasKeyword(Keyword.CREATURE)) {
                target.attack(retaliateTarget, target.getDamage(), true);
            } else {
                Logger.debug("cannot retaliate " + retaliateTarget);
            }
        }
        return CommandResult.successful();

    }

    public void removeKeyword(Keyword keyword) {
        Set<CardEffect> removeEffect = new HashSet<>();
        for (CardEffect effect : getEffects()) {
            if (effect instanceof KeywordEffect) {
                if (((KeywordEffect) effect).getKeyword().equals(keyword)) {
                    removeEffect.add(effect);
                }
            }
        }
        if (removeEffect.size() > 0) {
            getEffects().removeAll(removeEffect);
        }
    }


    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Set<Keyword> getKeywords() {
        Set<Keyword> keywords = new HashSet<>();
        for (CardEffect cardEffect : effects) {
            if (cardEffect instanceof KeywordEffect) {
                keywords.add(((KeywordEffect) cardEffect).getKeyword());
            }
        }
        return keywords;
    }

    public void addKeyword(Keyword keyword) {
        this.getEffects().add(new KeywordEffect(keyword));
    }

    public boolean isPlayable() {

        if (this.hasKeyword(Keyword.EXHAUSTED)) {
            Logger.debug(this + " is exhausted");
            return false;
        }

        if (this.isActive()) {
            return true;
        }
        Card currentCard = ApplicationContext.getGameEngine()
                .getCurrentCard();
        if (currentCard != null) {

            if (this.getKeywords()
                    .contains(Keyword.SIMPLE)) {
                return true;
            } else {
                return currentCard.hasOneKeyword(this.getKeywords());
            }
        }
        return false;
    }

    public boolean hasOneKeyword(Set<Keyword> keywords) {
        if (keywords == null || keywords.size() == 0) {
            return true;
        }
        for (Keyword kw : keywords) {
            if (Keyword.SIMPLE.equals(kw)) {
                return true;
            }
            if (getKeywords().contains(kw)){
                return true;
            }
        }
        return false;
    }

    public CommandResult heal(Card target, int amount) {
        if (amount <= 0) {
            return CommandResult.successful();
        }
        int h = amount + target.getHits();
        if (h <= target.getMaxHits()) {
            target.setHits(h);
        } else {
            target. setHits(getMaxHits());
        }
        return CommandResult.successful(this + " heals " + target + " for " + amount);
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

    public String getCardKeywords(boolean all) {

        Set<String> words = new TreeSet<>();
        for (Keyword k : this.getKeywords()) {
            if (all || k.isOnCard()) {
                words.add(k.name());
            }
        }

        return StringUtils.join(words, " \u00b7 ");
    }

    public boolean isRanged() {
        return getKeywords().contains(Keyword.RANGED);
    }


    public boolean isAlive() {
        return getHits() > 0;
    }

    public Player getPlayer() {
        if (getOwner() != null && getOwner() instanceof Player) {
            return (Player) getOwner();
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

    public boolean hasKeyword(Keyword keyword) {
        return this.getKeywords()
                .contains(keyword);
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

    public Set<CardEffect> getEffects() {
        return effects;
    }

    public void setEffects(Set<CardEffect> effects) {
        this.effects = effects;
    }

    public boolean hasAllKeywords(List<Keyword> allowedKeywords) {
        if (allowedKeywords == null || allowedKeywords.size() == 0) {
            return true;
        }
        for (Keyword allowedKeyword : allowedKeywords) {
            if (!this.hasKeyword(allowedKeyword)) {
                return false;
            }
        }
        return true;
    }

    public void removeEffect(CardEffect cardEffect) {
        if (this.effects.contains(cardEffect)) {
            cardEffect.end(this);
        }
    }

    public void removeEffectWithSourceId(long id) {
        CardEffect removeEffect = null;
        for (CardEffect cardEffect : getEffects()) {
            if (cardEffect.getSourceEffect() != null && id == cardEffect.getSourceEffect().getId()) {
                removeEffect = cardEffect;
            }
        }
        if (removeEffect != null) {
            getEffects().remove(removeEffect);
        }
    }


    public boolean hasEffect(Class<? extends CardEffect> cardEffect) {
        for (CardEffect c : getEffects()) {
            if (c.getClass() == cardEffect) {
                return true;
            }
        }
        return false;
    }

    public void reset() {
        this.removeKeyword(Keyword.EXHAUSTED);
        if (this instanceof Character) {
            Character character = (Character) this;
            for (Card card : character.getLevelCards()) {
                card.reset();
            }
        }
    }

    public void longRest() {
        this.removeKeyword(Keyword.EXHAUSTED);
        this.removeKeyword(Keyword.LONG_EXHAUSTED);
        if (this instanceof Character) {
            Character character = (Character) this;
            if (character.getHits() > 0) {
                character.setHits(character.getMaxHits());
            }
            for (Card card : character.getLevelCards()) {
                card.longRest();
            }
        }
    }



}

