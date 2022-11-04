package cn.edu.fzu.sm.wuweida.frame;

import javax.swing.*;
import java.awt.*;

public class CustomerMainFrame extends JFrame {
    public CustomerMainFrame() throws HeadlessException {
        super("餐厅主页面");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
//        this.pack();

        //头部欢迎词
        JLabel topLabel=new JLabel("本店欢迎你!",JLabel.CENTER);
        topLabel.setOpaque(true);
        topLabel.setFont(new Font("楷体",Font.BOLD,60));
        topLabel.setBackground(Color.ORANGE);
        topLabel.setForeground(Color.RED);
        this.add(topLabel,BorderLayout.NORTH);



        JTabbedPane jTabbedPaneCenter=new JTabbedPane();
        JPanel popularPanel=new JPanel();
        JPanel vegetPanel=new JPanel();
        jTabbedPaneCenter.add("火爆菜式",popularPanel);
        jTabbedPaneCenter.add("青菜类",vegetPanel);




        this.add(jTabbedPaneCenter,BorderLayout.CENTER);
        this.setVisible(true);
    }
}
