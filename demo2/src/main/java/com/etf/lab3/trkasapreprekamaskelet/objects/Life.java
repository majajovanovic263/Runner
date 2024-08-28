package com.etf.lab3.trkasapreprekamaskelet.objects;

import com.etf.lab3.trkasapreprekamaskelet.Game;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Life extends GameObject{
    private static double OBSTACLE_SPEED = 4.0;
    private static double step = 0.00000000002;
    public Life(Position position) {
        super(position);
        Box b1 = new Box(20,5,5);
        Box b2 = new Box(5,20,5);
        PhongMaterial b = new PhongMaterial(Color.RED);
        b1.setMaterial(b);
        b2.setMaterial(b);
        b1.setTranslateY(-2);

        super.setTranslateX(position.getX());
        super.setTranslateY(position.getY() - 10/ 2);
        super.setTranslateZ(position.getZ());
        super.getChildren().addAll(b1,b2);

        PointLight pointLight = new PointLight(Color.RED);
        Timeline timeLine;
        Duration d1 = Duration.seconds(0.5);
        Duration d2 = Duration.seconds(1);


        KeyFrame startFrame = new KeyFrame(d1, event -> {super.getChildren().addAll(pointLight);});
        KeyFrame endFrame = new KeyFrame(d2, event -> {super.getChildren().remove(pointLight);});
        timeLine = new Timeline(startFrame,endFrame);
        timeLine.setCycleCount(Timeline.INDEFINITE);
       // timeLine.play();


    }
    public void rotate(){
        super.getTransforms().addAll(new Rotate(1,Rotate.Y_AXIS));
    }
    public boolean move()
    {
        if(Game.isGameActive() == true) {
            OBSTACLE_SPEED += step;
            this.setTranslateZ(this.getTranslateZ() - OBSTACLE_SPEED);
        }
        return this.getTranslateZ() > 0;
    }
}
