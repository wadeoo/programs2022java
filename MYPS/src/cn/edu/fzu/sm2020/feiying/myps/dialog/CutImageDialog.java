package cn.edu.fzu.sm2020.feiying.myps.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class CutImageDialog extends JDialog {
    private final DrawImagePanel imagePanel;
    private final CutImagePanel cutImagePanel;
    private final JSplitPane splitPane;
    private Image openedImg;
    private int pressPanelX,pressPanelY,pressScreenX,pressScreenY,dragPanelX,dragPanelY;
    private Rectangle rect;
    private BufferedImage resultImage;

    public CutImageDialog(Image openedImg) {
        this.openedImg=openedImg;
        this.setTitle("裁剪");
        this.setSize(1024,1200);
        this.setLocationRelativeTo(null);
        ImageIcon imageIcon=new ImageIcon("./././img/ps.png");
        this.setIconImage(imageIcon.getImage());


        imagePanel = new DrawImagePanel();
        cutImagePanel=new CutImagePanel();


        JButton saveBtn=new JButton("保存截图");
        saveBtn.addActionListener(new SaveHandler());

        imagePanel.addMouseListener(new MyMouseAdaptor());
        imagePanel.addMouseMotionListener(new MyMouseMotionAdaptor());
        cutImagePanel.setLayout(new BoxLayout(cutImagePanel,BoxLayout.Y_AXIS));
        cutImagePanel.add(saveBtn,BorderLayout.SOUTH);

        splitPane=new JSplitPane();
        splitPane.setLeftComponent(imagePanel);
        splitPane.setRightComponent(cutImagePanel);
        splitPane.setResizeWeight(.5d);

        this.add(splitPane,BorderLayout.CENTER);

        this.setVisible(true);

        this.setModal(true);
    }


    private class SaveHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(resultImage==null||resultImage.equals("")){
                JOptionPane.showMessageDialog(CutImageDialog.this,"当前无处理结果");
                return;
            }
            SaveImageDialog saveImageDialog=new SaveImageDialog(resultImage);
            saveImageDialog.setVisible(true);
        }
    }

    private class DrawImagePanel extends JPanel{
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(openedImg,(this.getWidth()-openedImg.getWidth(this))/2,0,this);//在面板上绘制图像
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


            rect=new Rectangle
                    (pressScreenX+1,pressScreenY+1
                            ,e.getXOnScreen()-pressScreenX-2,e.getYOnScreen()-pressScreenY-2);
            try {
                Robot robot=new Robot();
                resultImage = robot.createScreenCapture(rect);
                cutImagePanel.repaint();
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
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

    private class CutImagePanel extends JPanel{
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if(resultImage!=null){
                g.drawImage(resultImage,(this.getWidth()-resultImage.getWidth())/2,0,null);
            }
        }
    }
}
