package cn.edu.fzu.sm2020.shape;

import java.awt.*;
import java.io.Serializable;

public class Circle2 implements Serializable {

    public Circle2() {
        point1 = null;
        point2 = null;
        point3 = null;
        color=Color.BLACK;
        width=1;
    }

    public Point point1, point2, point3;
    public Color color;
    public int width;

}
