package net.sachau.deathcrawl.gui;


import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.catalog.Catalog;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console extends ScrollPane {

    private Pattern pattern = Pattern.compile("\\{Card@(\\d+):(.*?)\\}");

    private CardInfoWindow cardInfoWindow = new CardInfoWindow();

    private TextFlow textFlow = new TextFlow();


    public Console() {
        super();
        this.setFitToHeight(true);
        this.setFitToWidth(true);
        this.setContent(textFlow);

        Console pane = this;
        textFlow.heightProperty().addListener(
                (observable, oldValue, newValue) -> {
                    textFlow.layout();

                pane.setVvalue( 1.0d );
                }
        );
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
        this.textFlow.getChildren().addAll(tokens);
    }

    private Node createToken(String arg, boolean isLast) {
        String textString = arg.trim();
        textString += isLast ? "\n" : " ";
        Matcher matcher = pattern.matcher(textString);
        if (matcher.find()) {
            long cardId = Long.parseLong(matcher.group(1));

            Card card = Catalog.getById(cardId);
            System.out.println("cardId=" + cardId + " card= "+ card);
            Text cardLink = new Text();

            if (card != null) {
                cardLink.setOnMouseClicked(event -> {
                    cardInfoWindow.setCardId(cardId);
                    cardInfoWindow.show();
                    cardInfoWindow.toFront();
                });
                cardLink.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        cardLink.setUnderline(true);
                    }
                });
                cardLink.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        cardLink.setUnderline(false);
                    }
                });
                cardLink.setText("[" + card.getName() + "@" + card.getId() + "]");

            } else {
                cardLink.setText("[" + cardId + " not found]");
            }
            return cardLink;
        } else {
            return new Text(textString);
        }

    }

    public CardInfoWindow getCardInfoWindow() {
        return cardInfoWindow;
    }
}
