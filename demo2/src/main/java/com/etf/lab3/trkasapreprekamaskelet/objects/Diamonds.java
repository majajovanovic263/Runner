package com.etf.lab3.trkasapreprekamaskelet.objects;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.scene.Group;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

 public class Diamonds extends Group {
    public MeshView p;
    public Diamonds(){
        TriangleMesh pyramid1 = new TriangleMesh();
        float h = 15;
        float w = 15;
        float d = 15;
        pyramid1.getPoints().addAll(
                0, -h / 2,   0,      // 0
                w / 2,  h / 2,    d / 2,    // 1
                w / 2,  h / 2,    -d / 2,   // 2
                -w / 2, h / 2,    -d / 2,   // 3
                -w / 2, h / 2,    d / 2     // 4
        );
        pyramid1.getTexCoords().addAll(
                0.504f, 0.524f,     // 0
                0.701f, 0,          // 1
                0.126f, 0,          // 2
                0,      0.364f,     // 3
                0,      0.608f,     // 4
                0.165f, 1,          // 5
                0.606f, 1,          // 6
                0.575f, 0.420f,     // 7
                0.575f, 0.643f,     // 8
                0.740f, 0.643f,     // 9
                0.740f, 0.420f      // 10
        );

        pyramid1.getFaces().addAll(
                0, 0, 3, 5, 2, 6, // Front face
                0, 0, 2, 2, 1, 3, // Right face
                0, 0, 1, 1, 4, 2, // Back face
                0, 0, 4, 4, 3, 5, // Left right face
                2, 9, 3, 8, 4, 7, // Bottom face
                2, 9, 4, 7, 1, 10 // Bottom face
        );
        p = new MeshView(pyramid1);

        super.getChildren().addAll(p);

    }

}
