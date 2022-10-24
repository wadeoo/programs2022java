package cn.edu.fzu.sm.weida.wzq;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CtrlPanel extends JPanel implements ActionListener {
    private final JButton retractBtn;
    private MainPanel mainPanel;

    public CtrlPanel(MainPanel mainPanel) {
        this.mainPanel=mainPanel;
        this.setBackground(Color.WHITE);
        retractBtn=new JButton("悔棋");
        retractBtn.setPreferredSize(new Dimension(100,100));
        retractBtn.addActionListener(this);

        this.add(retractBtn);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == retractBtn) {
            mainPanel.doRetraction();
        }
    }
}
