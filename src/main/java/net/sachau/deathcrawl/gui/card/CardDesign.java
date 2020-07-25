package net.sachau.deathcrawl.gui.card;

import javafx.scene.text.Text;

import javax.swing.text.html.ImageView;
import java.util.LinkedList;
import java.util.List;

public class CardDesign {

    private String name;
    private ImageView image;

    private List<Text> cardText = new LinkedList<>();
    private List<Text> flavorText = new LinkedList<>();

    public CardDesign(String name) {
        this.name = name;
    }

    public CardDesign() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public List<Text> getCardText() {
        return cardText;
    }

    public void setCardText(List<Text> cardText) {
        this.cardText = cardText;
    }

    public List<Text> getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(List<Text> flavorText) {
        this.flavorText = flavorText;
    }
}
