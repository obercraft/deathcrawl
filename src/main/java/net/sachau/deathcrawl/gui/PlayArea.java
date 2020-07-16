package net.sachau.deathcrawl.gui;

import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.gui.card.CardTile;
import net.sachau.deathcrawl.gui.card.CardTileCache;

public class PlayArea extends ScrollPane {

    private HBox container = new HBox();
    private Deck hazards;
    private Player player;

    public PlayArea(Player player, Deck hazards, int length) {
    	super();
    	this.player = player;
    	this.hazards = hazards;

    	this.setMinHeight(CardTile.HEIGHT);

        hazards.getCards().addListener(new ListChangeListener<Card>() {
            @Override
            public void onChanged(Change<? extends Card> change) {
                while (change.next()) {
                    int added = change.getAddedSize();
                    if (added > 0) {
                        for (Card c : change.getAddedSubList()) {
                            container.getChildren()
                                    .add(CardTileCache.getTile(c));
                        }
                    }

                    int removed = change.getRemovedSize();
                    if (removed > 0) {
                        for (Card c : change.getRemoved()) {
                            container.getChildren().remove(CardTileCache.getTile(c));

                        }
                    }
                }
            }
        });

        setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setVbarPolicy(ScrollBarPolicy.NEVER);
        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setHeight(CardTile.HEIGHT +20);
        setMaxHeight(CardTile.HEIGHT +20);
        setMaxWidth(CardTile.WIDTH * length);
        setFitToHeight(true);
        setFitToWidth(true);
        container.setAlignment(Pos.TOP_LEFT);
        setContent(container);

    }

}
