package cn.edu.fzu.sm.wuweida.frame;

import cn.edu.fzu.sm.wuweida.dao.JdbcHelper;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CustomerMainFrame extends JFrame {


    private final JPanel popularPanel;
    private final JPanel cantonesePanel;
    private final JPanel xiangPanel;
    private final JPanel dessertPanel;
    private JdbcHelper jdbcHelper;

    private ArrayList<String> foodNameListc;
    private int[] priceListc;



    public CustomerMainFrame(String username) throws HeadlessException {
        super("餐厅主页面");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10,0));
//        this.pack();

        //头部欢迎词
        JLabel topLabel=new JLabel(username+" 粤湘之家欢迎你!",JLabel.CENTER);
        topLabel.setOpaque(true);
        topLabel.setFont(new Font("楷体",Font.BOLD,60));
        topLabel.setBackground(Color.ORANGE);
        topLabel.setForeground(Color.RED);
        this.add(topLabel,BorderLayout.NORTH);


        //主面板
        JTabbedPane jTabbedPaneCenter=new JTabbedPane();
        popularPanel= new JPanel();
        cantonesePanel= new JPanel();
        xiangPanel= new JPanel();
        dessertPanel= new JPanel();
        popularPanel.setLayout(new GridLayout(50,1));
        cantonesePanel.setLayout(new GridLayout(20,1));
        xiangPanel.setLayout(new GridLayout(9,1));
        dessertPanel.setLayout(new GridLayout(9,1));
        dishPanelSetup();
        jTabbedPaneCenter.add("火爆菜式",popularPanel);
        jTabbedPaneCenter.add("粤菜",cantonesePanel);
        jTabbedPaneCenter.add("湘菜",xiangPanel);
        jTabbedPaneCenter.add("甜品饮料",dessertPanel);


        //东部面板
        JPanel detailPanel=new JPanel();
        detailPanel.setLayout(new GridLayout(6,1));
        JLabel tableChoosingLabel=new JLabel("请选择桌号");
        JLabel headLabel=new JLabel("请输入用餐人数");
        JLabel chosenLabel=new JLabel("已点菜式列表");
        String[] tableNumber={"桌1", "桌2", "桌3", "桌4", "桌5", "桌6"};
        JComboBox tableComboBox=new JComboBox(tableNumber);
        JTextField headTextField=new JTextField(16);
        JTable chosenDishTabel=new JTable();
        detailPanel.add(tableChoosingLabel);
        detailPanel.add(tableComboBox);
        detailPanel.add(headLabel);
        detailPanel.add(headTextField);
        detailPanel.add(chosenLabel);
        detailPanel.add(chosenDishTabel);
        this.add(detailPanel,BorderLayout.EAST);

        //南部面板
        JPanel actionPanel=new JPanel();
        JButton checkOutBtn=new JButton("下单");
        JButton cancelBtn=new JButton("退出");
        actionPanel.add(checkOutBtn);
        actionPanel.add(cancelBtn);
        this.add(actionPanel,BorderLayout.SOUTH);



        this.add(jTabbedPaneCenter,BorderLayout.CENTER);
        this.setVisible(true);



        checkOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result=JOptionPane.showConfirmDialog(CustomerMainFrame.this,"确定要退出吗?","注意",JOptionPane.YES_NO_OPTION
                );
                if(result==0){
                    CustomerMainFrame.this.dispose();
                }
            }
        });
    }

    public  void dishPanelSetup(){
        foodNameListc=new ArrayList<>();
        priceListc=new int[50];
        jdbcHelper=new JdbcHelper();
        jdbcHelper.setFoodNameList(foodNameListc,"c");
        jdbcHelper.setPriceList(priceListc,"c");
        for(int i=0;i<foodNameListc.size();i++){
            ImageIcon img=new ImageIcon(CustomerMainFrame.class
                    .getResource("/dishImg/"+foodNameListc.get(i)+".jpg"));
            JLabel jLabel=new JLabel(img);
            JLabel jLabel1=new JLabel(Integer.toString(priceListc[i]),JLabel.CENTER);
            JCheckBox jCheckBox=new JCheckBox(foodNameListc.get(i));
            cantonesePanel.add(jLabel);
            cantonesePanel.add(jLabel1);
            cantonesePanel.add(jCheckBox);
        }
    }
}
