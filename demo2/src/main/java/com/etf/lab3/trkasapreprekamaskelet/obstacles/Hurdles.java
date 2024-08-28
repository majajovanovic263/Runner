package com.etf.lab3.trkasapreprekamaskelet.obstacles;

import com.etf.lab3.trkasapreprekamaskelet.objects.Player;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;

public class Hurdles extends ObstacleBody{
    private Group hurdle;
    private static final Color DEFAULT_OBSTACLE_COLOR = Color.WHITESMOKE;
    private static final Color DEFAULT_OBSTACLE_COLOR2 = Color.RED;
    private static final PhongMaterial OBSTACLE_MATERIAL = new PhongMaterial(DEFAULT_OBSTACLE_COLOR);

    private static final PhongMaterial OBSTACLE_MATERIAL2 = new PhongMaterial(DEFAULT_OBSTACLE_COLOR2);

    public Hurdles(Position position) {
        super(position);
    }

    @Override
    protected void createObstacleBody() {
        Box b1 = new Box(4,24,4);
        Box b2 = new Box(4,4,4);
        Box b3 = new Box(4,4,4);
        Box b4 = new Box(4,4,4);
        Box b5 = new Box(4,4,4);
        Box b6 = new Box(4,4,4);
        Box b7 = new Box(4,4,4);
        Box b8 = new Box(4,4,4);
        Box b9 = new Box(4,24,4);
        b1.setMaterial(OBSTACLE_MATERIAL);
        b2.setMaterial(OBSTACLE_MATERIAL);
        b3.setMaterial(OBSTACLE_MATERIAL2);
        b4.setMaterial(OBSTACLE_MATERIAL);
        b5.setMaterial(OBSTACLE_MATERIAL2);
        b6.setMaterial(OBSTACLE_MATERIAL);
        b7.setMaterial(OBSTACLE_MATERIAL2);
        b8.setMaterial(OBSTACLE_MATERIAL);
        b9.setMaterial(OBSTACLE_MATERIAL);

        b1.getTransforms().addAll(
                new Translate(-12,-12,0)
        );
        b2.getTransforms().addAll(
                new Translate(-12,-24,0)
        );
        b3.getTransforms().addAll(
                new Translate(-8,-24,0)
        );
        b4.getTransforms().addAll(
                new Translate(-4,-24,0)
        );
        b5.getTransforms().addAll(
                new Translate(0,-24,0)
        );
        b6.getTransforms().addAll(
                new Translate(4,-24,0)
        );
        b7.getTransforms().addAll(
                new Translate(8,-24,0)
        );
        b8.getTransforms().addAll(
                new Translate(12,-24,0)
        );
        b9.getTransforms().addAll(
                new Translate(12,-12,0)
        );


        this.getChildren().addAll(b1,b2,b3,b4,b5,b6,b7,b8,b9);
    }

    @Override
    public double getObstacleHeight() {
        return 0;
    }


}
