package net.sachau.deathcrawl.gui;


import javafx.beans.property.SimpleLongProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.catalog.Catalog;
import net.sachau.deathcrawl.gui.card.CardInfoView;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console extends TextFlow {

    private Pattern pattern = Pattern.compile("\\{Card@(\\d+)\\}");

    private CardInfoWindow cardInfoWindow = new CardInfoWindow();



    public Console() {
        super();
    }

    public void appendText(String text) {
        if (StringUtils.isEmpty(text)) {
            return;
        }
        String args[] = text.trim().split(" ", -1);
        List<Node> tokens = new LinkedList<>();
        for (int i = 0; i< args.length; i ++) {
            tokens.add(createToken(args[i], i == args.length -1));
        }
        this.getChildren().addAll(tokens);
    }

    private Node createToken(String arg, boolean isLast) {
        String textString = arg.trim();
        textString += isLast ? "\n" : " ";
        Matcher matcher = pattern.matcher(textString);
        if (matcher.find()) {
            long cardId = Long.parseLong(matcher.group(1));

            Card card = Catalog.getById(cardId);
            System.out.println("cardId=" + cardId + " card= "+ card);
            Text hbox = new Text();

            if (card != null) {
                hbox.setOnMouseClicked(event -> {
                    cardInfoWindow.setCardId(cardId);
                    cardInfoWindow.show();
                    cardInfoWindow.toFront();
                });
                hbox.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        hbox.setUnderline(true);
                    }
                });
                hbox.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        hbox.setUnderline(false);
                    }
                });
                hbox.setText("[" + card.getName() + "@" + card.getId() + "]");

            } else {
                hbox.setText("[" + cardId + " not found]");
            }
            return hbox;
        } else {
            return new Text(textString);
        }

    }
}
