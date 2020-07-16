package net.sachau.deathcrawl.gui.images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TileSet {

    private static TileSet instance;
    private PixelReader reader;

    Map<String, WritableImage> cache = new HashMap<>();

    public static TileSet getInstance() {
        if (instance == null) {
            instance = new TileSet();
        }
        return instance;
    }

    private TileSet() {
        InputStream inputStream = TileSet.class.getResourceAsStream("/icons.png");
        Image image = new Image(inputStream);

        reader = image.getPixelReader();

    }

    public ImageView getTile(Tile tile) {
        WritableImage writableImage = readImage(tile.getX(), tile.getY());
        ImageView imageView = new ImageView(writableImage);
        if (tile.getHeight() != Tile.HEIGHT || tile.getWidth() != Tile.WIDTH) {
            imageView.setFitHeight(tile.getHeight());
            imageView.setFitWidth(tile.getWidth());
            imageView.setPreserveRatio(true);
        }
        return imageView;
    }

    private WritableImage readImage(int x, int y) {
        final String key = x +"," + y;
        WritableImage image = cache.get(key);
        if (image != null) {
            return image;
        } else {
            image = new WritableImage(reader, x * Tile.WIDTH, y * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
            cache.put(key, image);
            return  image;
        }
    }

}
