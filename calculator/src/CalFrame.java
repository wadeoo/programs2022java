import javax.swing.*;
import java.awt.*;

public class CalFrame extends JFrame {
    private final JLabel resultLabel;

    public CalFrame() throws HeadlessException {
        setSize(320,400);
        setTitle("计算器");
        setLocationRelativeTo(null);
        setLayout(null);
        ImageIcon icon = new ImageIcon("./img/calculator2.png");
        setIconImage(icon.getImage());
        resultLabel = new JLabel("0",JLabel.RIGHT);
        resultLabel.setSize(295,40);
        resultLabel.setLocation(5,5);
        Font resultFont = new Font("黑体",Font.BOLD,30);
        resultLabel.setFont(resultFont);
        resultLabel.setOpaque(true);
        resultLabel.setBackground(Color.WHITE);//设置标签背景色
        
        add(resultLabel);

    }
}
