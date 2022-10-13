package cn.edu.fzu.sm2020.shape;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//为了方便保存, 将所有图形类放在一个类里
public class AllShape implements Serializable {

    private List<Line> lineList = new ArrayList<>();
    private List<Circle2> circle2List=new ArrayList<>();
    private List<Circle> circleList= new ArrayList<>();
    private List<Rect> rectList= new ArrayList<>();
    private List<Star>  starList=new ArrayList<>();
    private List<ArbitraryLine> ArbitraryLineList=new ArrayList<>();


    public List<Line> getLineList() {
        return lineList;
    }

    public List<Circle2> getCircle2List() {
        return circle2List;
    }

    public List<Circle> getCircleList() {
        return circleList;
    }

    public List<Rect> getRectList() {
        return rectList;
    }

    public List<Star> getStarList() {
        return starList;
    }


    public List<ArbitraryLine> getArbitraryLineList() {
        return ArbitraryLineList;
    }
}
