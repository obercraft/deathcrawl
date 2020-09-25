package net.sachau.zarrax.engine;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.UniqueCardList;
import net.sachau.zarrax.card.level.Levels;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.card.type.Monster;
import net.sachau.zarrax.card.effect.CardEffect;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.encounter.EncounterGenerator;
import net.sachau.zarrax.map.World;

import java.util.*;

public class GameEngine  implements Observer {

    private static long id = 1;
    private boolean initialized;

    public static synchronized  long createId() {
        return id ++;
    }


    Player player;
    World world;

    private List<Card> initiativeOrder;
    private SimpleIntegerProperty currentInitiative = new SimpleIntegerProperty(0);

    private static GameEngine gameEngine;

    public static GameEngine getInstance() {
        if (gameEngine == null) {
            gameEngine = new GameEngine();
        }
        return gameEngine;
    }


    private GameEngine() {
        super();
        GameEvent.getInstance().addObserver(this);

    }

    @Override
    public void update(Observable o, Object arg) {
        switch(GameEvent.getType(arg)) {
            case WELCOME:
                Logger.debug("WELCOME!");
                break;
            case PARTYDONE: {
                for (Card c : player.getParty()) {
                    Character character = (Character) c;
                    for (Card startingCard : character.getStartingCards()) {
                        startingCard.setOwner(player);
                        player.getDraw()
                                .add(startingCard);
                    }
                }

                player.initHand();
                return;
            }
            case START_TURN: {
                player.setMoves(Player.MOVES);
                triggerStartEffects(GameEventContainer.Type.START_TURN);
                GameEvent.getInstance()
                        .send(GameEventContainer.Type.START_MOVEMENT);
                return;
            }

            case END_MOVEMENT: {
                GameEvent.getInstance()
                        .send(GameEventContainer.Type.START_ENCOUNTER);
                return;
            }
            case PREPARE_ENCOUNTER: {
                {
                    // make sure every card in the party is owned by player and has PERMANENT keyword
                    for (Card card : player.getParty()) {
                        card.setOwner(player);
                        card.setVisible(true);
                        card.addKeyword(Keyword.PERMANENT);
                    }

                    // generate an encounter and assign it to player
                    List<Card> hazards = world.getHazards(player.getX(), player.getY());
                    if (hazards == null || hazards.size() == 0) {
                        GameEvent.getInstance()
                                .send(GameEventContainer.Type.END_ENCOUNTER);
                        return;
                    }
                    player.setHazards(FXCollections.observableArrayList(hazards));

                    // check if every card in hazards is visible and not owned by player
                    for (Card card : player.getHazards()) {
                        card.setVisible(true);
                        card.setOwner(null);
                    }

                    // build initiativeDeck
                    initiativeOrder = new ArrayList<>();
                    initiativeOrder.addAll(player.getParty());
                    initiativeOrder.addAll(player.getHazards());
                    Collections.shuffle(initiativeOrder);
                    setCurrentInitiative(0);

                    GameEvent.getInstance()
                            .send(GameEventContainer.Type.START_ENCOUNTER);

                }

                return;
            }

            case START_ENCOUNTER: {
                //trigger phase effects
                triggerStartEffects(GameEventContainer.Type.START_ENCOUNTER);
                GameEvent.getInstance()
                        .send(GameEventContainer.Type.START_CARDPHASE);
                return;
            }

            case START_CARDPHASE:
                startCardPhase();
                return;

            case NEXT_ACTION: {

                Card card = getCurrentCard();
                if (card.getOwner() instanceof Player) {
                    Logger.debug("waiting for " + getCurrentCard());
                    GameEvent.getInstance()
                            .send(GameEventContainer.Type.WAITING_FOR_PLAYER_ACTION);

                } else {
                    Logger.debug("AI for " + card);
                    GameAI.execute(player, card);
                    GameEvent.getInstance()
                            .send(GameEventContainer.Type.ACTION_DONE);
                }

                return;
            }

            case PLAYERACTIONDONE:
                GameEvent.getInstance()
                        .send(GameEventContainer.Type.ACTION_DONE);
                return;
            case ACTION_DONE: {
                if (getCurrentInitiative() < initiativeOrder.size() - 1) {
                    int ini = getCurrentInitiative() + 1;

                    setCurrentInitiative(ini);
                    GameEvent.getInstance()
                            .send(GameEventContainer.Type.NEXT_ACTION);
                } else {
                    GameEvent.getInstance()
                            .send(GameEventContainer.Type.END_CARDPHASE);
                }
                return;
            }

            case END_CARDPHASE:
                triggerStartEffects(GameEventContainer.Type.END_CARDPHASE);
                player.resetHand();

                for (Card card : player.getParty()) {
                    card.getKeywords().remove(Keyword.EXHAUSTED);
                }
                for (Card card : player.getHand()) {
                    card.getKeywords().remove(Keyword.EXHAUSTED);
                }
                for (Card card : player.getDraw().getDiscards()) {
                    card.getKeywords().remove(Keyword.EXHAUSTED);
                }

                if (regenerateMonstersAndCheckDefeated()) {
                    GameEvent.getInstance().send(GameEventContainer.Type.END_TURN);
                } else {
                    GameEvent.getInstance().send(GameEventContainer.Type.START_CARDPHASE);
                }

                return;
            case END_TURN: {
                Levels.getInstance()
                        .gainLevel(player);
                    GameEvent.getInstance()
                            .send(GameEventContainer.Type.START_TURN);
                return;
            }
            case CHARACTERDEATH: {
                int totalHealth = 0;
                Card deadCard = (Card) GameEvent.getInstance()
                        .getData();

                for (CardEffect cardEffect : deadCard.getEffects()) {
                    for (Card card : player.getHazards()) {
                         card.removeEffect(cardEffect);
                    }
                    for (Card card : player.getParty()) {
                        card.removeEffect(cardEffect);
                    }
                }

                for (Card card : player.getParty()) {
                    if (card instanceof Character) {
                        totalHealth += Math.max(0, card.getHits());
                    }
                }
                Card hazardCard = null;
                for (Card card : player.getHazards()) {
                    if (card.getId() == deadCard.getId()) {
                        hazardCard = deadCard;
                        break;
                    }
                }
                if (hazardCard != null) {
                    player.getHazards()
                            .remove(hazardCard);
                }


                // ALL DEAD
                if (totalHealth <= 0) {
                    Logger.debug("all characters are dead - game over");
                    GameEvent.getInstance()
                            .send(GameEventContainer.Type.GAMEOVER);
                    return;
                }

                Card turnCard = null;
                int initiativeId = -1, i =0;
                if (initiativeOrder != null) {
                    for (Card card : initiativeOrder) {
                        if (card.getId() == deadCard.getId()) {
                            turnCard = card;
                            initiativeId = i;
                        }
                        i++;

                    }
                }
                if (turnCard != null) {
                    initiativeOrder.remove(turnCard);

                }

                if (initiativeId >= initiativeOrder.size()) {
                    GameEvent.getInstance()
                            .send(GameEventContainer.Type.END_CARDPHASE);
                }

                return;
            }
            default:
                return;

        }
    }

    private boolean regenerateMonstersAndCheckDefeated() {
        if (player.getHazards() == null ||player.getHazards().size() == 0) {
            return true;
        }
        int monstersAlive = 0;
        for (Card card : player.getHazards()) {
            if (card instanceof Monster) {
                Monster monster = (Monster) card;
                if (monster.isAlive()) {
                    if (monster.getRegenerate() > 0) {
                        monster.heal(monster, monster.getRegenerate());
                    }
                    monstersAlive ++;
                }
            }
        }
        // all monsters are dead
        return monstersAlive == 0;
    }


    private void startCardPhase() {
        setCurrentInitiative(0);
        triggerStartEffects(GameEventContainer.Type.START_CARDPHASE);
        GameEvent.getInstance()
                .send(GameEventContainer.Type.NEXT_ACTION);
    }


    public void triggerStartEffects(GameEventContainer.Type eventType) {
        player.setSpawnCards(new UniqueCardList());
        for (Card card : player.getHazards()) {
            card.triggerStartEffects(eventType);
        }
        for (Card card : player.getParty()) {
            card.triggerStartEffects(eventType);
        }
        if (player.getSpawnCards().size() > 0) {
            player.getHazards().addAll(player.getSpawnCards());
        }
    }

    public void triggerEndEffects(GameEventContainer.Type eventType) {

        for (Card card : player.getHazards()) {
            card.triggerEndEffects(eventType);
        }
        for (Card card : player.getParty()) {
            card.triggerEndEffects(eventType);
        }
    }



    public Card getCurrentCard() {
        if (getCurrentInitiative() >= initiativeOrder.size() ) {
            return null;
        }
        return initiativeOrder.get(getCurrentInitiative());
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public void setInitialized(boolean b) {
        this.initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public int getCurrentInitiative() {
        return currentInitiative.get();
    }

    public SimpleIntegerProperty currentInitiativeProperty() {
        return currentInitiative;
    }

    public void setCurrentInitiative(int currentInitiative) {
        this.currentInitiative.set(currentInitiative);
    }

    public List<Card> getInitiativeOrder() {
        return initiativeOrder;
    }

    public void setInitiativeOrder(List<Card> initiativeOrder) {
        this.initiativeOrder = initiativeOrder;
    }

    public void gameOver() {
        // TODO msachau

    }

    public void createGame() {
        setPlayer(new Player());
        setWorld(new World());
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
