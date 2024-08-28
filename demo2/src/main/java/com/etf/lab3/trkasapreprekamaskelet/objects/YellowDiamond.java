package com.etf.lab3.trkasapreprekamaskelet.objects;

import com.etf.lab3.trkasapreprekamaskelet.Game;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;

public class YellowDiamond extends GameObject{

    private static double OBSTACLE_SPEED = 4.0;
    private static double step = 0.00000000002;
    public YellowDiamond(Position position) {
        super(position);
        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY() - 15/ 2);
        this.setTranslateZ(position.getZ());
        Diamonds top = new Diamonds();
        Diamonds bottom = new Diamonds();
        top.setTranslateY(-15);
        bottom.getTransforms().addAll(new Rotate(180, Rotate.X_AXIS));

        Image gradientimg = new Image("file:///Users/majajovanovic/IdeaProjects/demo2/src/main/java/com/etf/lab3/trkasapreprekamaskelet/gradient2.jpg");
        PhongMaterial gradient = new PhongMaterial();
        gradient.setDiffuseMap(gradientimg);
        top.p.setMaterial(gradient);
        bottom.p.setMaterial(gradient);




        super.getChildren().addAll(top, bottom);

    }
    public boolean move()
    {
        if(Game.isGameActive() == true) {
            OBSTACLE_SPEED += step;
            this.setTranslateZ(this.getTranslateZ() - OBSTACLE_SPEED);
        }
        return this.getTranslateZ() > 0;
    }

    public void rotate(){
        super.getTransforms().addAll(new Rotate(1,Rotate.Y_AXIS));
    }
}
