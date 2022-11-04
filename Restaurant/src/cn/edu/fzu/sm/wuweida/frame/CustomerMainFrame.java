package cn.edu.fzu.sm.wuweida.frame;

import javax.swing.*;
import java.awt.*;

public class CustomerMainFrame extends JFrame {
    public CustomerMainFrame() throws HeadlessException {
        super("餐厅主页面");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800,600);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }
}
