package cn.edu.fzu.sm2020.feiying.myps;

import cn.edu.fzu.sm2020.feiying.myps.dialog.CutImageDialog;
import cn.edu.fzu.sm2020.feiying.myps.dialog.SaveImageDialog;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;

public class MainFrame extends JFrame implements KeyListener ,WindowStateListener  {


    private ImageIcon openedImg;
    private JLabel originImgLabel, modImgLabel;
    private JSplitPane splitPane;
    private Image outImg;


    @Override
    public void windowStateChanged(WindowEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_Q){
            System.out.println("sdhfgid");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //inner class
    class  OpenHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser=
                    new JFileChooser("D:/BASIC/Desktop/JAVA/programs2022java/MYPS/img");
            fileChooser.showOpenDialog(MainFrame.this);

            File file =fileChooser.getSelectedFile();
            if(file.exists()){
                openedImg =new ImageIcon(file.getPath());

                originImgLabel.setIcon(openedImg);
            }else {
                JOptionPane.showMessageDialog(fileChooser,"文件不存在");
            }


        }
    }

    //inner class
    public class EdgeDetectHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(openedImg==null||openedImg.equals("")){
                JOptionPane.showMessageDialog(MainFrame.this,"请先打开图像");
                return;
            }
            int width=openedImg.getIconWidth();
            int height=openedImg.getIconHeight();

            BufferedImage bufferInImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics graphics=bufferInImg.getGraphics();
            graphics.drawImage(openedImg.getImage(),0,0,null);

            BufferedImage bufferOutImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            float []kernelMatrix={0.0f,-1.0f,0.0f,
                               -1.f,4.0f,-1.0f,
                                0.0f,-1.0f,0.0f};

            Kernel kernel=new Kernel(3,3,kernelMatrix);
            ConvolveOp cop=new ConvolveOp(kernel,ConvolveOp.EDGE_ZERO_FILL,null);
            cop.filter(bufferInImg,bufferOutImg);
            outImg=bufferOutImg;

            ImageIcon imageIcon=new ImageIcon(outImg);
            modImgLabel.setIcon(imageIcon);


        }
    }


    public MainFrame() throws HeadlessException {
        this.setSize(1024,720);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("我的图像处理");
        this.setLayout(new BorderLayout());
        this.addWindowStateListener(this);


        ImageIcon imageIcon=new ImageIcon("././img/ps.png");
        this.setIconImage(imageIcon.getImage());

        //菜单
        menuInit();

        //工具栏
        toolInit();

        //状态
        statusInit();

        //
        imagePanelInit();

        this.setVisible(true);

        this.addKeyListener(this);

    }

    private void imagePanelInit() {
        originImgLabel= new JLabel() ;
        modImgLabel=new JLabel();

        JScrollPane leftPane=new JScrollPane(originImgLabel);
        JScrollPane rightPane=new JScrollPane(modImgLabel);

        splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPane,rightPane);
        splitPane.setResizeWeight(0.5d);
        this.add(splitPane);
    }

    private void statusInit() {
        JPanel statusPanel=new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(this.getWidth(),30));
        statusPanel.setLayout(new BoxLayout(statusPanel,BoxLayout.X_AXIS));


        JLabel statusLabel=new JLabel("状态栏");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);

        statusPanel.add(statusLabel);

        this.add(statusPanel,BorderLayout.SOUTH);




    }

    private void toolInit() {
        JToolBar toolBar=new JToolBar();

        JButton openBtn=new JButton("打开图像");
        JButton edgeDetectBtn=new JButton("边缘检测");

        ImageIcon imageIcon1=new ImageIcon("././img/folder.png");
        imageIcon1 =new ImageIcon(imageIcon1.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH));
        ImageIcon imageIcon2=new ImageIcon("././img/edge_detect.png");
        imageIcon2 =new ImageIcon(imageIcon2.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH));

        openBtn.setIcon(imageIcon1);
        openBtn.addActionListener(new OpenHandler());
        edgeDetectBtn.setIcon(imageIcon2);
        edgeDetectBtn.addActionListener(new EdgeDetectHandler());

        toolBar.add(openBtn);
        toolBar.add(edgeDetectBtn);

        this.add(toolBar,BorderLayout.NORTH);

    }


    private void menuInit() {
        JMenuBar menuBar=new JMenuBar();

        JMenu fileMenu=new JMenu("文件");
        JMenu imgProcessMenu= new JMenu("图像处理");

        JMenuItem iOpenItem=new JMenuItem("打开图像");
        JMenuItem edgeDetectItem=new JMenuItem("边缘检测");
        JMenuItem saveItem=new JMenuItem("保存图像");
        JMenuItem bleachItem=new JMenuItem("去色");
        JMenuItem cutItem=new JMenuItem("裁剪");

        fileMenu.add(iOpenItem);
        fileMenu.add(saveItem);
        imgProcessMenu.add(edgeDetectItem);
        imgProcessMenu.add(bleachItem);
        imgProcessMenu.add(cutItem);

        menuBar.add(fileMenu);
        menuBar.add(imgProcessMenu);

        iOpenItem.addActionListener(new OpenHandler());
        saveItem.addActionListener(new SaveHandler());
        edgeDetectItem.addActionListener(new EdgeDetectHandler());
        bleachItem.addActionListener(new BleachHandler());
        cutItem.addActionListener(new CutHandler());

        this.setJMenuBar(menuBar);
    }


    private class SaveHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(outImg==null||outImg.equals("")){
                JOptionPane.showMessageDialog(MainFrame.this,"当前无处理结果");
                return;
            }
            SaveImageDialog saveImageDialog=new SaveImageDialog(outImg);
            saveImageDialog.setVisible(true);
        }
    }

    private class BleachHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(openedImg==null||openedImg.equals("")){
                JOptionPane.showMessageDialog(MainFrame.this,"请先打开图像");
                return;
            }
            int width=openedImg.getIconWidth();
            int height=openedImg.getIconHeight();

            BufferedImage bufferInImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferInImg.getGraphics().drawImage(openedImg.getImage(),0,0,null);

//            BufferedImage bufferOutImg=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
//            for (int y=0;y<height;y++){
//                for (int x=0;x<width;x++){
//                    bufferOutImg.setRGB(x,y,bufferInImg.getRGB(x,y));
//                }
//            }

            BufferedImage bufferOutImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            for (int y=0;y<height;y++){
                for (int x=0;x<width;x++){
                    bufferOutImg.setRGB(x,y,Color.RED.getRGB()+x);
                }
            }
            outImg=bufferOutImg;
            ImageIcon imageIcon=new ImageIcon(outImg);
            modImgLabel.setIcon(imageIcon);
        }
    }

    private class CutHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(openedImg==null||openedImg.equals("")){
                JOptionPane.showMessageDialog(MainFrame.this,"请先打开图像");
                return;
            }
            CutImageDialog cutImageDialog=new CutImageDialog(openedImg.getImage());
            cutImageDialog.setVisible(true);
        }
    }
}
