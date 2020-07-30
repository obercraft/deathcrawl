package net.sachau.deathcrawl;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.catalog.Catalog;
import net.sachau.deathcrawl.cards.types.EventDeck;
import net.sachau.deathcrawl.cards.types.StartingCharacter;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.effects.CardEffect;
import net.sachau.deathcrawl.effects.Exhausted;
import net.sachau.deathcrawl.events.Event;
import net.sachau.deathcrawl.events.GameEvent;
import net.sachau.deathcrawl.keywords.Keyword;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GameEngine  implements Observer {

    private static long id = 1;

    public static synchronized  long createId() {
        return id ++;
    }


    Player player;

    private Deck turnDeck;
    private int currentTurnDeck;
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
                for (Card c : player.getParty()
                        .getCards()) {
                    StartingCharacter startingCharacter = (StartingCharacter) c;
                    for (Card startingCard : startingCharacter.getStartingCards()
                            .getCards()) {
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
                for (Card card : player.getParty()
                        .getCards()) {
                    card.triggerPhaseEffects(Event.Type.STARTTURN);
                }

                return;
            }
            case STARTENCOUNTER: {

                List<Card> eventCards = Catalog.getInstance()
                        .get(EventDeck.class);

                Deck hazards = new Deck();
                hazards.setVisible(true);
                for (Card card : eventCards.get(0)
                        .getDeck()
                        .getCards()) {
                    try {
                        hazards.add(Utils.copyCard(card));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (Card card : player.getParty()
                        .getCards()) {
                    card.setOwner(player);
                    card.addKeywords(Keyword.PERMANENT);
                }
                player.setHazard(hazards);

                turnDeck = new Deck();
                turnDeck.setVisible(true);

                turnDeck.addAll(player.getParty());
                turnDeck.addAll(player.getHazard());
                turnDeck.shuffe();
                currentTurnDeck = 0;
                for (Card card : turnDeck.getCards()) {
                    card.triggerPhaseEffects(Event.Type.STARTENCOUNTER);
                }

                GameEvent.getInstance()
                        .send(Event.Type.GUI_STARTENCOUNTER);
                GameEvent.getInstance()
                        .send(Event.Type.STARTCARDPHASE);
                return;
            }

            case STARTCARDPHASE: {
                currentTurnDeck = 0;
                GameEvent.getInstance()
                        .send(Event.Type.NEXTACTION);
                return;
            }
            case NEXTACTION: {
                if (lastCard != null) {
                    lastCard.setActive(false);
                }
                lastCard = currentCard;
                currentCard = turnDeck.getCards()
                        .get(currentTurnDeck);
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
                if (currentTurnDeck < turnDeck.size() - 1) {
                    currentTurnDeck++;
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

                // the hand still has open slots
                if (player.getHandSize() - player.getHand().size() > 0) {

                    // put discard to draw pile and shuffle
                    player.getDiscard().moveAll(player.getDraw());
                    player.getDraw().shuffe();
                    player.getDraw().draw(player.getHand(), player.getHandSize() - player.getHand().size());

                    // draw again
                    player.getDraw().draw(player.getHand(), player.getHandSize() - player.getHand().size());
                }

                for (Card card : player.getParty().getCards()) {
                    card.removeCondition(Exhausted.class);
                }

                if (player.getHazard().size() == 0) {
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
                    for (Card card : player.getHazard().getCards()) {
                        card.removeCondition(condition);
                    }
                    for (Card card : player.getParty().getCards()) {
                        card.removeCondition(condition);
                    }
                }

                for (Card card : player.getParty()
                        .getCards()) {
                    if (card instanceof StartingCharacter) {
                        totalHealth += Math.max(0, card.getHits());
                    }
                }
                Card hazardCard = null;
                for (Card card : player.getHazard()
                        .getCards()) {
                    if (card.getId() == deadCard.getId()) {
                        hazardCard = deadCard;
                    }
                }
                if (hazardCard != null) {
                    player.getHazard()
                            .remove(hazardCard);
                }
                Card turnCard = null;
                for (Card card : turnDeck.getCards()) {
                    if (card.getId() == deadCard.getId()) {
                        turnCard = card;
                    }
                }
                if (turnCard != null) {
                    turnDeck.remove(turnCard);
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
