package net.sachau.zarrax.gui.card;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.*;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.card.command.Command;
import net.sachau.zarrax.engine.GuiComponent;
import net.sachau.zarrax.v2.GEngine;

import java.util.HashMap;
import java.util.Map;

@GuiComponent
public class DragEvents {

    public static final DataFormat cardFormat = new DataFormat("card");
    public static final DataFormat momentumFormat = new DataFormat("momentum");

    public EventHandler<? super MouseEvent> dragDetected(Node node, Card card) {
        return (EventHandler<MouseEvent>) event -> {
            if (card.isPlayable()) {
                Dragboard db = node.startDragAndDrop(TransferMode.ANY);
                Map<DataFormat, Object> map = new HashMap<>();
                map.put(cardFormat, card.getId());
                db.setContent(map);
            }
            event.consume();

        };

    }


    public EventHandler<? super DragEvent> dragOver() {
        return (EventHandler<DragEvent>) event -> {
            if (event.getDragboard()
                    .hasContent(cardFormat)) {
                event.acceptTransferModes(TransferMode.ANY);
            }

            if (event.getDragboard()
                    .hasContent(momentumFormat)) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        };

    }

    public EventHandler<? super DragEvent> dragDropped(Card targetCard) {
        return (EventHandler<DragEvent>) event -> {
            Dragboard db = event.getDragboard();

            if (db.getContent(cardFormat) != null) {
                long cardId = (Long) db.getContent(cardFormat);
                Catalog catalog = GEngine.getBean(Catalog.class);
                Card sourceCard = catalog.getById(cardId);
                Command.execute(sourceCard instanceof Character ? ((Character)sourceCard).getSelectedCard() : sourceCard, targetCard);
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
