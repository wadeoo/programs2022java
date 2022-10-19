package cn.edu.fzu.sm2020.feiying.myps.dialog;


import javax.swing.*;
import java.awt.*;

public class SaveImageDialog extends JDialog {
    private Image outImage;

    public SaveImageDialog(Image outImg) {
        this.outImage=outImg;

        this.setTitle("保存图像");
        this.setSize(512,600);
        this.setLocationRelativeTo(null);

        ImageIcon imageIcon=new ImageIcon("./././img/saveIcon.png");
        this.setIconImage(imageIcon.getImage());

        DrawImagePanel imagePanel = new DrawImagePanel();

        this.add(imagePanel);
    }

    private class DrawImagePanel extends JPanel{
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(outImage,(this.getWidth()-outImage.getWidth(this))/2,0,this);//在面板上绘制图像
        }
    }
}
