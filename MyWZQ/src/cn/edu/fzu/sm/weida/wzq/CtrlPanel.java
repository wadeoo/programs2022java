package cn.edu.fzu.sm.weida.wzq;

import javax.swing.*;
import java.awt.*;

public class CtrlPanel extends JPanel {
    private final JButton retractBtn;

    public CtrlPanel() {
        this.setBackground(Color.WHITE);
        retractBtn=new JButton("悔棋");
        retractBtn.setPreferredSize(new Dimension(100,100));
        this.add(retractBtn);

    }
}
