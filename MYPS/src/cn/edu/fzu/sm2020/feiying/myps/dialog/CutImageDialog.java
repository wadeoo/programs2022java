package cn.edu.fzu.sm2020.feiying.myps.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CutImageDialog extends JDialog {
    private final DrawImagePanel imagePanel;
    private Image img;
    private int pressPanelX,pressPanelY,pressScreenX,pressScreenY,dragPanelX,dragPanelY;
    private Rectangle rect;

    public CutImageDialog(Image img) {
        this.img=img;
        this.setTitle("裁剪");
        this.setSize(1024,1200);
        this.setLocationRelativeTo(null);


        imagePanel = new DrawImagePanel();
        imagePanel.addMouseListener(new MyMouseAdaptor());
        imagePanel.addMouseMotionListener(new MyMouseMotionAdaptor());

        JSplitPane splitPane=new JSplitPane();
        splitPane.setResizeWeight(.5d);
        splitPane.setLeftComponent(imagePanel);
        splitPane.setDividerSize(2);

        this.add(splitPane,BorderLayout.CENTER);


        this.add(imagePanel);
        this.setModal(true);
    }

    private class DrawImagePanel extends JPanel{
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(img,(this.getWidth()-img.getWidth(this))/2,0,this);//在面板上绘制图像
            g.setColor(Color.RED);
            g.drawRect(pressPanelX,pressPanelY,dragPanelX-pressPanelX,dragPanelY-pressPanelY);
        }
    }

    private class MyMouseAdaptor extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e){
            super.mousePressed(e);

            pressPanelX=e.getX();
            pressPanelY=e.getY();
            pressScreenX=e.getXOnScreen();
            pressScreenY=e.getYOnScreen();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);

            rect=new Rectangle();
        }
    }

    private class MyMouseMotionAdaptor extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);

            dragPanelX=e.getX();
            dragPanelY=e.getY();

            imagePanel.repaint();
        }
    }
}
