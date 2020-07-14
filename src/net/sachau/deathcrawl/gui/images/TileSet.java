package net.sachau.deathcrawl.gui.images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TileSet {

    public enum Tile {

        DEATHCRAWL (0,0, 100,100);

        private int x,y,w,h;

        Tile(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
        Tile(int x, int y) {
            this.x = x;
            this.y = y;
            this.w = iconsWidth;
            this.h = iconsHeight;
        }
    }
    private static final int iconsHeight = 32;
    private static final int iconsWidth = 32;

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
        InputStream inputStream = TileSet.class.getResourceAsStream("icons.png");
        Image image = new Image(inputStream);

        reader = image.getPixelReader();

    }

    public ImageView getTile(Tile tile) {
        WritableImage writableImage = readImage(tile.x, tile.y);
        ImageView imageView = new ImageView(writableImage);
        if (tile.h != iconsHeight || tile.w != iconsWidth) {
            imageView.setFitHeight(tile.h);
            imageView.setFitWidth(tile.w);
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
            image = new WritableImage(reader, x * iconsWidth, y * iconsHeight, iconsWidth, iconsHeight);
            cache.put(key, image);
            return  image;
        }
    }

}
