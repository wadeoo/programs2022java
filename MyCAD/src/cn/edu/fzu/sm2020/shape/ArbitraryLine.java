package cn.edu.fzu.sm2020.shape;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArbitraryLine implements Serializable {
    public ArbitraryLine(){
        this.pointList =new ArrayList<>();
        this.color = null;
        this.width = 1;
    }

    public List<Point> pointList;
    public Color color;
    public int width;
}
