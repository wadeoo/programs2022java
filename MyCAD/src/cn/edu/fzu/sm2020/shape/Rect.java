package cn.edu.fzu.sm2020.shape;

import java.awt.*;
import java.io.Serializable;

public class Rect implements Serializable {
    public Rect() {
        this.point1 = null;
        this.point2 = null;
        color=Color.BLACK;
        width=1;
    }

    public Point point1;
    public Point point2;
    public Color color;
    public int width;
}
