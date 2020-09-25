package net.sachau.zarrax.gui.image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileSet {

    private static final String[] filenames = new String[] {
            "/images/icons.png",
            "/images/icons1.png",
    };

    private static TileSet instance;
    private List<PixelReader> readers = new ArrayList<>();

    Map<String, WritableImage> cache = new HashMap<>();

    public static TileSet getInstance() {
        if (instance == null) {
            instance = new TileSet();
        }
        return instance;
    }

    private TileSet() {
        for (String filename : filenames) {
            InputStream inputStream = TileSet.class.getResourceAsStream(filename);
            Image image = new Image(inputStream);

            PixelReader reader = image.getPixelReader();
            readers.add(reader);
        }

    }

    public ImageView getTile(Tile tile) {
        if (tile == null) {
            return null;
        }
        WritableImage writableImage = readImage(tile.getIndex(), tile.getX(), tile.getY());
        ImageView imageView = new ImageView(writableImage);
        if (tile.getHeight() != Tile.HEIGHT || tile.getWidth() != Tile.WIDTH) {
            imageView.setFitHeight(tile.getHeight());
            imageView.setFitWidth(tile.getWidth());
            imageView.setPreserveRatio(true);
        }
        return imageView;
    }


    private WritableImage readImage(int index, int x, int y) {
        final String key = x +"," + y;
        WritableImage image = cache.get(key);
        if (image != null) {
            return image;
        } else {
            image = new WritableImage(readers.get(index), x * Tile.WIDTH, y * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
            cache.put(key, image);
            return  image;
        }
    }

}
