package cn.edu.fzu.sm.weida.wzq;

import sun.text.normalizer.Utility;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainFrame extends JFrame {//同时作为程序入口类

    public static void main(String[] args) {
        MainFrame mainFrame=new MainFrame();
        //mainFrame.setResizable(false);
        mainFrame.setTitle("五子棋");
        mainFrame.setType(Type.UTILITY);
        mainFrame.setSize(800,700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon imageIcon=new ImageIcon("./././img/gomoku.png");
        mainFrame.setIconImage(imageIcon.getImage());

        MainPanel mainPanel=new MainPanel();//左边游戏面板
        CtrlPanel ctrlPanel=new CtrlPanel(mainPanel);//操作面板

        //mainPanel.setBackground(new Color(135, 86, 0));

        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.add(ctrlPanel,BorderLayout.EAST);

        mainFrame.setVisible(true);

    }

}
