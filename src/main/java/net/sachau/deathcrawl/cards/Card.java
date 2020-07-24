package net.sachau.deathcrawl.cards;

import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import net.sachau.deathcrawl.Game;
import net.sachau.deathcrawl.conditions.Armor;
import net.sachau.deathcrawl.conditions.Condition;
import net.sachau.deathcrawl.conditions.Guard;
import net.sachau.deathcrawl.conditions.Poisonous;
import net.sachau.deathcrawl.dto.Creature;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.keywords.Keyword;
import net.sachau.deathcrawl.keywords.Keywords;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public abstract class Card {

    private long id;
    private String name;
    private Map<CardEffect.Phase, List<CardEffect>> effects;
    private Keywords keywords = new Keywords();
    private String command;
    private String text;

    private SimpleIntegerProperty damage = new SimpleIntegerProperty(1);
    private SimpleIntegerProperty maxDamage = new SimpleIntegerProperty(1);
    private SimpleIntegerProperty hits = new SimpleIntegerProperty(1);
    private SimpleIntegerProperty maxHits = new SimpleIntegerProperty(1);
    private SimpleBooleanProperty visible = new SimpleBooleanProperty();

    private Creature owner;
    private Deck deck;
    private SetProperty<Condition> conditions;

    public Card(String name, int initialHits, int initialDamage) {
        super();



        this.name = name;
        initHits(initialHits);
        initDamage(initialDamage);
        this.id = Game.createId();
        this.effects = new HashMap<>();

        ObservableSet<Condition> observableSet = FXCollections.observableSet(new HashSet<>());
        this.conditions = new SimpleSetProperty<>(observableSet);

    }

    public Card(String name, Creature owner) {
        super();
        this.name = name;
        this.id = Game.createId();
        this.effects = new HashMap<>();
        this.owner = owner;

    }


    private Map<CardEffect.Phase, List<CardEffect>> getEffects() {
        return effects;
    }

    private List<CardEffect> getPhaseEffects(CardEffect.Phase phase) {
        return effects.get(phase) != null ? effects.get(phase) : new LinkedList<>();
    }

    public void triggerPhaseEffects(CardEffect.Phase phase) {
        List<CardEffect> phaseCardEffects = getPhaseEffects(phase);
        if (phaseCardEffects != null) {
            for (CardEffect e : phaseCardEffects) {
                e.trigger(this);
            }
        }
    }


    public void addEffect(CardEffect.Phase phase, CardEffect cardEffect) {
        if (effects.get(phase) == null) {
            effects.put(phase, new LinkedList<>());
        }
        effects.get(phase)
                .add(cardEffect);

    }

    public void setEffects(Map<CardEffect.Phase, List<CardEffect>> effects) {
        this.effects = effects;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return id == card.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
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
            return false;
        }

        Deck targetDeck = target.getDeck();
        for (Card card : targetDeck.getCards()) {
            if (card.isAlive()) {
                for (Condition condition : card.getConditions()) {
                    if (condition instanceof Guard && card.getId() != target.getId()) {
                        return false;
                    }
                }
            }
        }

        if (target.attackArmor()) {
            return true;
        }

        if (owner != null) {
            attack += owner.getAttackBonus();
        }

        int hits = target.getHits();

        for (Condition c : getConditions()) {
            if (c instanceof Poisonous) {
                attack += c.getAmount();
                conditions.remove(c);
            }
        }

        if (attack > 0) {
            hits -= attack;
        }

        target.setHits(hits);
        if (hits <= 0) {

            if (target instanceof MonsterCard && owner != null) {
                MonsterCard mc = (MonsterCard) target;
                int gold = mc.getGold() + owner.getGold();
                owner.setGold(gold);
                int xp = mc.getXp() + owner.getXp();
                owner.setXp(xp);

            }
        }
        return true;

    }


    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public ObservableSet<Condition> getConditions() {
        return conditions.get();
    }

    public SetProperty<Condition> conditionsProperty() {
        return conditions;
    }

    public void setConditions(ObservableSet<Condition> conditions) {
        this.conditions.set(conditions);
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
        if (keywords.contains(Keyword.SIMPLE)) {
            return true;
        }

        if (owner == null) {
            return false;
        }

        if (owner instanceof Player) {
            Set<Keyword> kwords = ((Player) owner).getParty()
                    .getKeywords();
            for (Keyword cardKeyword : this.keywords) {
                if (kwords.contains(cardKeyword)) {
                    return true;
                }
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

    public int getMaxDamage() {
        return maxDamage.get();
    }

    public SimpleIntegerProperty maxDamageProperty() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage.set(maxDamage);
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
        setMaxDamage(damage);
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

    public boolean attackArmor() {
        Condition armorCondition = null;
        for (Condition c : conditions) {
            if (c instanceof Armor) {
                armorCondition = c;
                break;
            }
        }
        if (armorCondition != null) {
            conditions.remove(armorCondition);
            return true;
        }
        return false;
    }

    public String getCardKeyWords() {
        if (this.getKeywords() == null) {
            return "";
        }
        Set<String> words = new TreeSet<>();
        for (Keyword k : this.getKeywords()) {
            if (k.isOnCard()) {
                words.add(k.name());
            }
        }

        return StringUtils.join(words, " \u00b7 ");
    }

    public boolean isRanged() {
        return  keywords.contains(Keyword.RANGED);
    }

    public boolean hasCondition(Class<? extends Condition> clazz) {
        for (Condition c : this.conditions) {
            if (c.getClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAlive() {
        return getHits() > 0;
    }
}
