package net.sachau.zarrax.gui.card;

import javafx.scene.control.Tooltip;
import javafx.scene.input.*;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.command.CommandParser;
import net.sachau.zarrax.card.Creature;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.card.keyword.Keyword;

import java.util.Observable;

public class CardTile extends CardView {

    private Card card;

    public CardTile(Card card, String cssClass) {
        super(card, cssClass);
        this.card = card;

        String tp = card.getCardKeyWords(true);
        Tooltip keywords = new Tooltip(tp);
        Tooltip.install(this, keywords);


        setOnMouseClicked(event -> {
            if (event.getButton()
                    .equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    if (card.getKeywords()
                            .contains(Keyword.ACTION) || card.getKeywords().contains(Keyword.PERMANENT)) {
                        executeCommand(card, 0, !card.getKeywords().contains(Keyword.PERMANENT));
                    }
                }
            }
            event.consume();
        });

//        setOnDragDetected(event -> {
//
//            if (card.isPlayable()) {
//                Dragboard db = this.startDragAndDrop(TransferMode.ANY);
//                Map<DataFormat, Object> map = new HashMap<>();
//                map.put(cardFormat, card.getId());
//                db.setContent(map);
//            }
//            event.consume();
//        });


//        setOnDragOver(event -> {
//            if (event.getDragboard()
//                    .hasContent(cardFormat)) {
//                event.acceptTransferModes(TransferMode.ANY);
//            }
//
//            if (event.getDragboard()
//                    .hasContent(momentumFormat)) {
//                event.acceptTransferModes(TransferMode.ANY);
//            }
//            event.consume();
//        });
        setOnDragDetected(new DragEvents().dragDetected(this, card));
        setOnDragOver(new DragEvents().dragOver());
        setOnDragDropped(new DragEvents().dragDropped(card));
//        setOnDragDropped(event -> {
//            Dragboard db = event.getDragboard();
//
//            if (db.getContent(cardFormat) != null) {
//                CardTile sourceTile = CardTileCache.getTile((Long) db.getContent(cardFormat));
//                Card sourceCard = sourceTile.getCard();
//                executeCommand(sourceCard, 0, !sourceCard.hasKeyword(Keyword.PERMANENT));
//            } else if (db.getContent(momentumFormat) != null) {
//                String[] args = ((String) db.getContent(momentumFormat)).split(",", -1);
//                Card sourceCard = Catalog.getById(new Long(args[0]));
//                executeCommand(sourceCard, new Integer(args[1]), true);
//            }
////
////            boolean commandSuccessful = CommandParser.executeCommand(sourceCard, getCard());
////            if (commandSuccessful) {
////                Creature owner = sourceCard.getOwner();
////                if (owner != null && owner instanceof Player) {
////                    Player player = (Player) owner;
////                    sourceCard.getDeck()
////                            .discard(sourceCard, player.getDiscard());
////                }
////            }
//
//            event.consume();
//        });

        CardCover cardCover = new CardCover(cssClass);
        if (!card.isVisible()) {
            this.getChildren()
                    .add(cardCover);
        }


        card.visibleProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (Boolean.TRUE.equals(newValue)) {
                        getChildren().remove(cardCover);
                    } else {
                        getChildren().add(cardCover);
                    }
                });




    }

    public Card getCard() {
        return card;
    }

    private void executeCommand(Card sourceCard, int cost, boolean discard) {
        boolean commandSuccessful = CommandParser.executeCommands(sourceCard, getCard());
        if (commandSuccessful) {
            Creature owner = sourceCard.getOwner();
            if (owner != null && owner instanceof Player) {
                Player player = (Player) owner;
                if (discard) {
                    if (Card.Source.HAND.equals(sourceCard.getSource())) {
                        player.getDraw().addToDiscard(card);
                    }
                    player.getHand().remove(sourceCard);
                }

                int m = player.getMomentum() - cost;
                player.setMomentum(m);
            }

        }

    }


    @Override
    public void update(Observable o, Object arg) {

    }
}

