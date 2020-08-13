package net.sachau.deathcrawl.gui;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class AttackLine {

    public Line connect(Pane commonAncestor, Node n1, Node n2) {


        Bounds n1InCommonAncestor = getRelativeBounds(n1, commonAncestor);
        Bounds n2InCommonAncestor = getRelativeBounds(n2, commonAncestor);
        Point2D n1Center = getCenter(n1InCommonAncestor);
        Point2D n2Center = getCenter(n2InCommonAncestor);

        Line line = new Line(n1Center.getX(), n1Center.getY(), n2Center.getX(), n2Center.getY());
        line.setScaleX(10);
        line.setStartY(10);
        commonAncestor.getChildren().add(line);
        return line;
    }

// ...

    private Bounds getRelativeBounds(Node node, Node relativeTo) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return relativeTo.sceneToLocal(nodeBoundsInScene);
    }

    private Point2D getCenter(Bounds b) {
        return new Point2D(b.getMinX() + b.getWidth() / 2, b.getMinY() + b.getHeight() / 2);
    }
}
