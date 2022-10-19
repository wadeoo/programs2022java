package cn.edu.fzu.sm2020.feiying.myps.dialog;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

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
            JFileChooser fileChooser=new JFileChooser("D:/basic/desktop");
            fileChooser.setFileFilter(new FileNameExtensionFilter("*.jpg","jpg"));
            fileChooser.showSaveDialog(SaveImageDialog.this);

            File file=fileChooser.getSelectedFile();
            if(!file.getPath().endsWith(".jpg")){
               file=new File(file.getPath()+".jpg") ;
            }
            if(file.exists()){
                JOptionPane.showMessageDialog(SaveImageDialog.this,"文件已存在,请使用其他文件名");
                return;
            }
            try{
                file.createNewFile();

                BufferedImage outImgBuffered=new BufferedImage(outImage.getWidth(null),outImage.getHeight(null)
                ,BufferedImage.TYPE_INT_RGB);
                outImgBuffered.getGraphics().drawImage(outImage,0,0,null);

                ImageIO.write(outImgBuffered,"jpg",file);
            }catch (Exception e1){

            }
        }
    }
}
