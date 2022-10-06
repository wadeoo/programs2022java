package cn.edu.fzu.sm2020;

import cn.edu.fzu.sm2020.frame.MyPaint;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        MyPaint myPaint = new MyPaint("画图"
                ,new Dimension(800,600));//定义并初始化窗口
        myPaint.setVisible(true);
    }
}
