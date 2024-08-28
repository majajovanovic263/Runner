package com.etf.lab3.trkasapreprekamaskelet.objects;

import com.etf.lab3.trkasapreprekamaskelet.Game;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;


public class Nozzle extends GameObject{
    private static double OBSTACLE_SPEED = 4.0;
    private static double step = 0.00000000002;
    public Nozzle(Position position) {
        super(position);
        Cylinder c = new Cylinder(7.5,24);
        PhongMaterial c1 = new PhongMaterial(Color.BLUE);
        c.setMaterial(c1);
        super.setTranslateX(position.getX());
        super.setTranslateY(position.getY() - 6);
        super.setTranslateZ(position.getZ());
        super.getChildren().addAll(c);
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
