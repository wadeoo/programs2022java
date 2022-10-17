package cn.edu.fzu.sm2020.feiying.myps;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MainFrame extends JFrame implements WindowStateListener {


    private ImageIcon ii;
    private JLabel originImgLabel, modImgLabel;
    private JSplitPane splitPane;



    @Override
    public void windowStateChanged(WindowEvent e) {

    }

    //inner class
    class  HandleOpen implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser=
                    new JFileChooser("D:/BASIC/Desktop/JAVA/programs2022java/MYPS/img");
            fileChooser.showOpenDialog(MainFrame.this);

            File file =fileChooser.getSelectedFile();
            if(file.exists()){
                ii =new ImageIcon(file.getPath());

                originImgLabel.setIcon(ii);
            }else {
                JOptionPane.showMessageDialog(fileChooser,"文件不存在");
            }


        }
    }

    //inner class
    public class HandleEdgeDetect implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(ii==null||ii.equals("")){
                JOptionPane.showMessageDialog(MainFrame.this,"请先打开图像");
            }
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

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()== KeyEvent.VK_Q){
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
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
        openBtn.addActionListener(new HandleOpen());
        edgeDetectBtn.setIcon(imageIcon2);
        edgeDetectBtn.addActionListener(new HandleEdgeDetect());

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

        fileMenu.add(iOpenItem);
        imgProcessMenu.add(edgeDetectItem);

        menuBar.add(fileMenu);
        menuBar.add(imgProcessMenu);

        iOpenItem.addActionListener(new HandleOpen());
        edgeDetectItem.addActionListener(new HandleEdgeDetect());

        this.setJMenuBar(menuBar);
    }


}
