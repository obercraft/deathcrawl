package net.sachau.deathcrawl.gui.card;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.*;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.Creature;
import net.sachau.deathcrawl.card.catalog.Catalog;
import net.sachau.deathcrawl.card.keyword.Keyword;
import net.sachau.deathcrawl.command.Command;
import net.sachau.deathcrawl.command.CommandParser;
import net.sachau.deathcrawl.engine.Player;

import java.util.HashMap;
import java.util.Map;

public class DragEvents {

    public static final DataFormat cardFormat = new DataFormat("card");
    public static final DataFormat momentumFormat = new DataFormat("momentum");

    public EventHandler<? super MouseEvent> dragDetected(Node node, Card card) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (card.isPlayable()) {
                    Dragboard db = node.startDragAndDrop(TransferMode.ANY);
                    Map<DataFormat, Object> map = new HashMap<>();
                    map.put(cardFormat, card.getId());
                    db.setContent(map);
                }
                event.consume();

            }
        };

    }


    public EventHandler<? super DragEvent> dragOver() {
        return new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getDragboard()
                        .hasContent(cardFormat)) {
                    event.acceptTransferModes(TransferMode.ANY);
                }

                if (event.getDragboard()
                        .hasContent(momentumFormat)) {
                    event.acceptTransferModes(TransferMode.ANY);
                }
                event.consume();
            }
        };

    }

    public EventHandler<? super DragEvent> dragDropped(Card targetCard) {
        return new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();

                if (db.getContent(cardFormat) != null) {
                    CardTile sourceTile = CardTileCache.getTile((Long) db.getContent(cardFormat));
                    Card sourceCard = sourceTile.getCard();
                    Command.execute(sourceCard, targetCard);
                }
// TODO msachau momentum
//                else if (db.getContent(momentumFormat) != null) {
//                    String[] args = ((String) db.getContent(momentumFormat)).split(",", -1);
//                    Card sourceCard = Catalog.getById(new Long(args[0]));
//                    executeCommand(sourceCard, sourceCard, new Integer(args[1]), true);
//                }
//
//            boolean commandSuccessful = CommandParser.executeCommand(sourceCard, getCard());
//            if (commandSuccessful) {
//                Creature owner = sourceCard.getOwner();
//                if (owner != null && owner instanceof Player) {
//                    Player player = (Player) owner;
//                    sourceCard.getDeck()
//                            .discard(sourceCard, player.getDiscard());
//                }
//            }

                event.consume();
            }
        };

    }

//    private void executeCommand(Card sourceCard, Card targetCard, int cost, boolean discard) {
//        boolean commandSuccessful = CommandParser.executeCommands(sourceCard, targetCard);
//        if (commandSuccessful) {
//            Creature owner = sourceCard.getOwner();
//            if (owner != null && owner instanceof Player) {
//                Player player = (Player) owner;
//                if (discard) {
//                    if (Card.Source.HAND.equals(sourceCard.getSource())) {
//                        player.getDraw().addToDiscard(sourceCard);
//                    }
//                    player.getHand().remove(sourceCard);
//                }
//
//                int m = player.getMomentum() - cost;
//                player.setMomentum(m);
//            }
//
//        }
//
//    }



}
