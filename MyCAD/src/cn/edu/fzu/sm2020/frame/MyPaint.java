package cn.edu.fzu.sm2020.frame;

import javax.swing.*;
import java.awt.*;

public class MyPaint extends JFrame {
    public MyPaint(String title,Dimension frameDim) throws HeadlessException {
        super(title);
        setSize(frameDim);

        //设置图标
        ImageIcon icon =new ImageIcon("./././img/paintIcon.png");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
