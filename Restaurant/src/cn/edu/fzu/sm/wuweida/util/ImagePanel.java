package cn.edu.fzu.sm.wuweida.util;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private final ImageIcon imageIcon;

    public ImagePanel() {
        imageIcon=new ImageIcon(ImagePanel.class.getResource("/loginBG.png"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(imageIcon.getImage(),0,0,null);
    }
}
