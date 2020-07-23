package net.sachau.deathcrawl.gui.card;

import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.CardCache;
import net.sachau.deathcrawl.commands.CommandParser;
import net.sachau.deathcrawl.dto.Creature;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.keywords.Keyword;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class CardTile extends CardView {

    public static final DataFormat cardFormat = new DataFormat("card");
    public static final DataFormat momentumFormat = new DataFormat("momentum");
    private Card card;

    public CardTile(Card card) {
        super(card);
        this.card = card;


        setOnMouseClicked(event -> {
            if (event.getButton()
                    .equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    if (card.getKeywords()
                            .contains(Keyword.ACTION)) {
                        executeCommand(card, 0);
                    }
                }
            }
            event.consume();
        });

        setOnDragDetected(event -> {

            if (!card.isPlayable()) {
                return;
            }
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);

            Map<DataFormat, Object> map = new HashMap<>();
            map.put(cardFormat, card.getId());
            db.setContent(map);
            event.consume();
        });

        setOnDragOver(event -> {
            if (event.getDragboard()
                    .hasContent(cardFormat)) {
                event.acceptTransferModes(TransferMode.ANY);
            }

            if (event.getDragboard()
                    .hasContent(momentumFormat)) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });

        setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();

            if (db.getContent(cardFormat) != null) {

                CardTile sourceTile = CardTileCache.getTile((Long) db.getContent(cardFormat));
                Card sourceCard = sourceTile.getCard();

                executeCommand(sourceCard, 0);
            } else if (db.getContent(momentumFormat) != null) {
                String[] args = ((String) db.getContent(momentumFormat)).split(",", -1);
                Card sourceCard = CardCache.get(new Long(args[0]));
                executeCommand(sourceCard, new Integer(args[1]));


            }
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
        });

    }

    public Card getCard() {
        return card;
    }

    private void executeCommand(Card sourceCard, int cost) {
        boolean commandSuccessful = CommandParser.executeCommand(sourceCard, getCard());
        if (commandSuccessful) {
            Creature owner = sourceCard.getOwner();
            if (owner != null && owner instanceof Player) {
                Player player = (Player) owner;
                if (sourceCard.getDeck() != null) {
                    sourceCard.getDeck()
                            .discard(sourceCard, player.getDiscard());
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

