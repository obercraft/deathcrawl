package net.sachau.zarrax.gui.map;

import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.engine.GameEvent;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.gui.images.TileSet;
import net.sachau.zarrax.map.World;

import java.util.HashMap;
import java.util.Map;

public class MapTile extends AnchorPane {



    private MapCoord mapCoord;

    Player player;

    public MapTile(Player player, MapCoord mapCoord) {
        super();
        this.player = player;
        // creates the polygon using the corner coordinates

        this.mapCoord = mapCoord;

        this.setWidth(32);
        this.setHeight(32);


        if (mapCoord.getType().getTile() != null) {
            this.getChildren().add(TileSet.getInstance().getTile(mapCoord.getType().getTile()));
        } else {
            // set up the visuals and a click listener for the tile
            Rectangle rectangle = new Rectangle(32,32);
            rectangle.setFill(mapCoord.getType()
                    .getColor());
            rectangle.setStrokeWidth(1);
            rectangle.setStroke(Color.BLACK);
            this.getChildren().add(rectangle);
        }

        this.relocate(mapCoord.getX(),mapCoord.getY());


        /*
        setOnDragOver(event -> {
            if (event.getDragboard()
                    .hasContent(mapFormat)) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();

        });



        setOnDragDetected(event -> {
            if (player.getMapCoord()
                    .equals(mapCoord)) {
                Dragboard db = this.startDragAndDrop(TransferMode.ANY);
                Map<DataFormat, Object> map = new HashMap<>();
                map.put(World.mapFormat, getMapCoord());
                db.setContent(map);
            }
            event.consume();

        });


        setOnDragDropped(event -> {

            MapCoord sourceCoord = (MapCoord) event.getDragboard()
                    .getContent(World.mapFormat);


            if (player.getMoves() > 0 && Math.abs(sourceCoord.getColumn() - mapCoord.getColumn()) <= 1 && Math.abs(sourceCoord.getRow() - mapCoord.getRow()) <= 1) {
                player.setMapCoord(mapCoord);
                int moves = player.getMoves() -1;
                player.setMoves(moves);
                GameEvent.getInstance()
                        .send(GameEventContainer.Type.PARTYMOVE);
            }

            event.consume();


        });
        
         */
    }

    public MapCoord getMapCoord() {
        return mapCoord;
    }

    public void setMapCoord(MapCoord mapCoord) {
        this.mapCoord = mapCoord;
    }

    @Override
    public String toString() {
        return mapCoord.getColumn() + "," + mapCoord.getRow() + "," + mapCoord.getX() + "," + mapCoord.getY();

    }
}
