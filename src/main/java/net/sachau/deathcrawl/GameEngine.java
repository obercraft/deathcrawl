package net.sachau.deathcrawl;

import javafx.collections.FXCollections;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.types.Monster;
import net.sachau.deathcrawl.cards.types.StartingCharacter;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.effects.CardEffect;
import net.sachau.deathcrawl.effects.Exhausted;
import net.sachau.deathcrawl.events.Event;
import net.sachau.deathcrawl.events.GameEvent;
import net.sachau.deathcrawl.keywords.Keyword;

import java.util.*;

public class GameEngine  implements Observer {

    private static long id = 1;

    public static synchronized  long createId() {
        return id ++;
    }


    Player player;

    private List<Card> initiativeOrder;
    private int currentInitiative;
    private Card currentCard;
    private Card lastCard;

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
            case PARTYDONE: {
                for (Card c : player.getParty()) {
                    StartingCharacter startingCharacter = (StartingCharacter) c;
                    for (Card startingCard : startingCharacter.getStartingCards()) {
                        startingCard.setOwner(player);
                        player.getDraw()
                                .add(startingCard);
                    }
                }

                player.initHand();
                return;
            }
            case STARTTURN: {
                player.setMoves(1);

                // trigger events
                for (Card card : player.getParty()) {
                    card.triggerPhaseEffects(Event.Type.STARTTURN);
                }

                return;
            }
            case STARTENCOUNTER: {
                startEncounter();
                return;
            }

            case STARTCARDPHASE:
                startCardPhase();
                return;

            case NEXTACTION: {
                if (lastCard != null) {
                    lastCard.setActive(false);
                }
                lastCard = currentCard;
                currentCard = initiativeOrder
                        .get(currentInitiative);
                currentCard.setActive(true);
                Card card = getCurrentCard();
                if (card.getOwner() instanceof Player) {
                    Logger.debug("waiting for " + getCurrentCard());
                    GameEvent.getInstance()
                            .send(Event.Type.WAITINGFORPLAYERACTION);

                } else {
                    Logger.debug("AI for " + card);
                    GameAI.execute(player, card);
                    GameEvent.getInstance()
                            .send(Event.Type.ACTIONDONE);
                }

                return;
            }

            case PLAYERACTIONDONE:
                GameEvent.getInstance()
                        .send(Event.Type.ACTIONDONE);
                return;
            case ACTIONDONE: {
                currentCard.setActive(false);
                if (currentInitiative < initiativeOrder.size() - 1) {
                    currentInitiative++;
                    GameEvent.getInstance()
                            .send(Event.Type.NEXTACTION);
                } else {
                    GameEvent.getInstance()
                            .send(Event.Type.ENDCARDPHASE);
                }
                return;
            }

            case ENDCARDPHASE:
                player.getDraw().draw(player.getHand(), player.getHandSize() - player.getHand().size());

                for (Card card : player.getParty()) {
                    card.removeCondition(Exhausted.class);
                }

                if (hazardsDefeated()) {
                    GameEvent.getInstance().send(Event.Type.STARTTURN);
                } else {
                    GameEvent.getInstance().send(Event.Type.STARTCARDPHASE);
                }

                return;
            case CHARACTERDEATH: {
                int totalHealth = 0;
                Card deadCard = GameEvent.getInstance()
                        .getStage()
                        .getCard();

                for (CardEffect condition : deadCard.getConditions()) {
                    for (Card card : player.getHazards()) {
                        card.removeCondition(condition);
                    }
                    for (Card card : player.getParty()) {
                        card.removeCondition(condition);
                    }
                }

                for (Card card : player.getParty()) {
                    if (card instanceof StartingCharacter) {
                        totalHealth += Math.max(0, card.getHits());
                    }
                }
                Card hazardCard = null;
                for (Card card : player.getHazards()) {
                    if (card.getId() == deadCard.getId()) {
                        hazardCard = deadCard;
                    }
                }
                if (hazardCard != null) {
                    player.getHazards()
                            .remove(hazardCard);
                }
                Card turnCard = null;
                for (Card card : initiativeOrder) {
                    if (card.getId() == deadCard.getId()) {
                        turnCard = card;
                    }
                }
                if (turnCard != null) {
                    initiativeOrder.remove(turnCard);
                }


                // ALL DEAD
                if (totalHealth <= 0) {
                    Logger.debug("all characters are dead - game over");
                    GameEvent.getInstance()
                            .send(Event.Type.GAMEOVER);
                    return;
                }
                return;
            }
            default:
                return;

        }
    }

    private boolean hazardsDefeated() {
        if (player.getHazards() == null ||player.getHazards().size() == 0) {
            return true;
        }
        for (Card card : player.getHazards()) {
            if (card instanceof Monster) {
                return false;
            }
        }
        return true;
    }

    private void startEncounter() {

        // make sure every card in the party is owned by player and has PERMANENT keyword
        for (Card card : player.getParty()) {
            card.setOwner(player);
            card.setVisible(true);
            card.addKeywords(Keyword.PERMANENT);
        }

        // generate an encounter and assign it to player
        player.setHazards(FXCollections.observableArrayList(EncounterGenerator.build()));

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
        currentInitiative = 0;

        //trigger phase effects
        triggerEffects(Event.Type.STARTENCOUNTER);


        GameEvent.getInstance()
                .send(Event.Type.GUI_STARTENCOUNTER);
        GameEvent.getInstance()
                .send(Event.Type.STARTCARDPHASE);

    }

    private void startCardPhase() {
        currentInitiative = 0;

        triggerEffects(Event.Type.STARTCARDPHASE);

        GameEvent.getInstance()
                .send(Event.Type.NEXTACTION);
    }


    private void triggerEffects(Event.Type eventType) {
        for (Card card : player.getHazards()) {
            card.triggerPhaseEffects(eventType);
        }
        for (Card card : player.getParty()) {
            card.triggerPhaseEffects(eventType);
        }
    }


    public Card getCurrentCard() {
        return currentCard;
    }

    public Card getLastCard() {
        return lastCard;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
