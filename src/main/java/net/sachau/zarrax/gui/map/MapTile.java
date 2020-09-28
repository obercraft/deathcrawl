package net.sachau.zarrax.gui.map;

import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import net.sachau.zarrax.engine.*;
import net.sachau.zarrax.gui.image.Tile;
import net.sachau.zarrax.gui.image.TileSet;
import net.sachau.zarrax.map.Land;
import net.sachau.zarrax.map.World;

public class MapTile extends AnchorPane {

    private int x, y;
    private Land land;
    public MapTile(Land land, int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.land = land;
        this.setWidth(Tile.WIDTH);
        this.setHeight(Tile.HEIGHT);



        try {
            Tile tile = Tile.valueOf(land.getTerrain()
                    .getTile()
                    .toUpperCase());

            this.getChildren()
                    .add(TileSet.getInstance()
                            .getTile(tile));
        } catch (Exception e) {
            Rectangle rect = new Rectangle(Tile.WIDTH, Tile.HEIGHT);
            rect.setFill(Color.valueOf(land.getTerrain()
                    .getTile()
                    .toUpperCase()));
            rect.setStrokeWidth(1);
            rect.setStroke(Color.BLACK);
            this.getChildren()
                    .add(rect);
        }

        if (land.getCity() != null) {
            this.getChildren()
                    .add(TileSet.getInstance()
                            .getTile(Tile.ARMOR));
        }


        setOnMouseEntered(event -> {
            GameEventContainer container = new GameEventContainer(GameEventContainer.Type.LANDINFO, land);
            GameEvent.getInstance().send(container);
        });

        setOnDragOver(event -> {
            if (event.getDragboard()
                    .hasContent(WorldMap.mapFormat)) {
                String data = (String) event.getDragboard().getContent(WorldMap.mapFormat);
                if (data.equals("party")) {
                    Player player = ApplicationContext.getPlayer();
                    if (player.getMoves() >= land.getMoveCost() && Math.abs(x - player.getX()) <= 1 && Math.abs(y - player.getY()) <= 1) {
                        event.acceptTransferModes(TransferMode.ANY);
                    }
                    // GameEngine.getInstance().getPlayer().relocate(x, y);
                    // GameEvent.getInstance().send(GameEventContainer.Type.PARTYMOVE);
                }

            }
            event.consume();

        });

        setOnDragDropped(event -> {
            ApplicationContext.getPlayer().move(land, x, y);
            GameEvent.getInstance().send(GameEventContainer.Type.PARTYMOVE);
            event.consume();

        });








//    setOnDragDropped(event -> {
//
//            MapCoord sourceCoord = (MapCoord) event.getDragboard()
//                    .getContent(World.mapFormat);
//
//
//            if (player.getMoves() > 0 && Math.abs(sourceCoord.getColumn() - mapCoord.getColumn()) <= 1 && Math.abs(sourceCoord.getRow() - mapCoord.getRow()) <= 1) {
//                player.setMapCoord(mapCoord);
//                int moves = player.getMoves() -1;
//                player.setMoves(moves);
//                GameEvent.getInstance()
//                        .send(GameEventContainer.Type.PARTYMOVE);
//            }
//
//            event.consume();
//
//
//        });



        /*



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



         */
    }

}
