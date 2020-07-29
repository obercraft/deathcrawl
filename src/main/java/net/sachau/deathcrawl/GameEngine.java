package net.sachau.deathcrawl;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.catalog.Catalog;
import net.sachau.deathcrawl.cards.types.EventDeck;
import net.sachau.deathcrawl.cards.types.StartingCharacter;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.keywords.Keyword;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GameEngine  implements Observer {

    Player player;

    private Deck turnDeck;
    private int currentTurnDeck;


    public GameEngine(Player player) {
        super();
        this.player = player;
        Game.events().addObserver(this);

    }

    @Override
    public void update(Observable o, Object arg) {
        switch(Game.get(arg)) {
            case STARTTURN:
                player.setMoves(1);

                // trigger events
                for (Card card : player.getParty().getCards()) {
                    card.triggerPhaseEffects(Event.STARTTURN);
                }

                return;
            case STARTENCOUNTER:

                List<Card> eventCards = Catalog.getInstance().get(EventDeck.class);

                Deck hazards = new Deck();
                hazards.setVisible(true);
                for (Card card : eventCards.get(0).getDeck().getCards()) {
                    try {
                        hazards.add(Utils.copyCard(card));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (Card card : player.getParty().getCards()) {
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
                    card.triggerPhaseEffects(Event.STARTENCOUNTER);
                }

                Game.events().send(Event.STARTENCOUNTERVIEW);
                Game.events().send(Event.STARTCARDPHASE);
                return;

            case STARTCARDPHASE:
                currentTurnDeck = 0;
                Game.events().send(Event.NEXTACTION);
                return;

            case NEXTACTION: {
                Card card = getCurrentCard();
                if (card.getOwner() instanceof Player) {
                    Logger.debug("waiting for " + getCurrentCard());
                } else {
                    Logger.debug("AI for " + card);
                    GameAI.execute(player, card);
                    Game.events()
                            .send(Event.ACTIONDONE);
                }
            }
            return;

            case ACTIONDONE:
                if (currentTurnDeck < turnDeck.size() -1) {
                    currentTurnDeck ++;
                    Game.events().send(Event.NEXTACTION);
                } else {
                    Game.events().send(Event.ENDCARDPHASE);
                }
                return;

            case PARTYDONE:
                for (Card c : player.getParty().getCards()) {
                    StartingCharacter startingCharacter = (StartingCharacter) c;
                    for (Card startingCard : startingCharacter.getStartingCards().getCards()) {
                        startingCard.setOwner(player);
                        player.getDraw().add(startingCard);
                    }
                }

                player.initHand();
                return;
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
                Game.events().send(Event.STARTTURN);
                return;
            case CHARACTERDEATH:
                int totalHealth = 0;
                for (Card card : player.getParty().getCards()) {
                    if (card instanceof StartingCharacter) {
                        totalHealth += Math.max(0,card.getHits());
                    }
                }
                // ALL DEAD
                if (totalHealth <= 0) {
                    Logger.debug("all characters are dead - game over");
                    Game.events().send(Event.GAMEOVER);
                    return;
                }
            default:
                return;

        }
    }

    private Card getCurrentCard() {
        return turnDeck.getCards().get(currentTurnDeck);
    }
}
