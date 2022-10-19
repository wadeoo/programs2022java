package cn.edu.fzu.sm2020.feiying.myps.dialog;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        JButton saveBtn=new JButton("保存");
        JButton cancelBtn=new JButton("关闭");

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveImageDialog.this.dispose();
            }
        });
        saveBtn.addActionListener(new ActionListenerForSave());

        JPanel lowPanel=new JPanel();//放置两个按钮

        lowPanel.add(saveBtn);
        lowPanel.add(cancelBtn);

        this.add(imagePanel);
        this.add(lowPanel,BorderLayout.SOUTH);

        this.setModal(true);


    }

    private class DrawImagePanel extends JPanel{
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(outImage,(this.getWidth()-outImage.getWidth(this))/2,0,this);//在面板上绘制图像
        }
    }

    //for savebtn
    private class ActionListenerForSave implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
