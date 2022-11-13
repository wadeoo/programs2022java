package cn.edu.fzu.sm.wuweida.frame;

import cn.edu.fzu.sm.wuweida.bean.NameNPrice;
import cn.edu.fzu.sm.wuweida.dao.JdbcHelper;
import cn.edu.fzu.sm.wuweida.tableModel.ChosenDishTableModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerMainFrame extends JFrame {


    private final JPanel popularPanel;
    private final JPanel cantonesePanel;
    private final JPanel xiangPanel;
    private final JPanel dessertPanel;
    private final JTable chosenDishTabel;
    private JdbcHelper jdbcHelper;

    private ArrayList<String> foodNameList;
    private int[] priceListc;
    private ArrayList<String> chosenFoodNameList = new ArrayList<>();
    private int[] chosenPriceList = new int[50];
    private ArrayList<String> quantityList = new ArrayList<>();


    private ArrayList<String> cols;
    private List<NameNPrice> rows;
    private ChosenDishTableModel<NameNPrice> chosenDishTableModel;


    public CustomerMainFrame(String username) throws HeadlessException {
        super("餐厅主页面");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(CustomerMainFrame.class.getResource("/restaurant.png")).getImage());
        this.setLayout(new BorderLayout(10, 0));

        quantityList.add("1");
        quantityList.add("1");
        quantityList.add("1");
        quantityList.add("1");
        quantityList.add("1");
        quantityList.add("1");
        quantityList.add("1");
        quantityList.add("1");
        quantityList.add("1");
        quantityList.add("1");
        quantityList.add("1");


        //头部欢迎词
        JLabel topLabel = new JLabel(username + " 粤湘之家欢迎你!", JLabel.CENTER);
        topLabel.setOpaque(true);
        topLabel.setFont(new Font("楷体", Font.BOLD, 60));
        topLabel.setBackground(Color.ORANGE);
        topLabel.setForeground(Color.RED);
        this.add(topLabel, BorderLayout.NORTH);


        //主面板

        JTabbedPane jTabbedPaneCenter = new JTabbedPane();
        popularPanel = new JPanel();
        cantonesePanel = new JPanel();
        xiangPanel = new JPanel();
        dessertPanel = new JPanel();
        popularPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        cantonesePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        xiangPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        dessertPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        dishPanelSetup();
        jTabbedPaneCenter.add("火爆菜式", popularPanel);
        jTabbedPaneCenter.add("粤菜", cantonesePanel);
        jTabbedPaneCenter.add("湘菜", xiangPanel);
        jTabbedPaneCenter.add("甜品饮料", dessertPanel);


        //东部面板

        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        detailPanel.setPreferredSize(new Dimension(200, 500));
        JLabel tableChoosingLabel = new JLabel("请选择桌号");
        JLabel headLabel = new JLabel("请输入用餐人数");
        JLabel chosenLabel = new JLabel("已点菜式列表");
        String[] tableNumber = {"桌1", "桌2", "桌3", "桌4", "桌5", "桌6"};
        JComboBox tableComboBox = new JComboBox(tableNumber);
        JTextField headTextField = new JTextField(16);

        chosenDishTabel = new JTable();
        JScrollPane scrollPaneForTable = new JScrollPane(chosenDishTabel);
        scrollPaneForTable.setPreferredSize(new Dimension(180, 300));
        cols = new ArrayList<>();
        cols.add("菜式");
        cols.add("价格");
        cols.add("数量");
        rows = getTheChosens();
        chosenDishTableModel = new ChosenDishTableModel<>(cols, rows);
        chosenDishTableModel.addTableModelListener(new MyTableModelListener());
        chosenDishTabel.setModel(chosenDishTableModel);

        detailPanel.add(tableChoosingLabel);
        detailPanel.add(tableComboBox);
        detailPanel.add(headLabel);
        detailPanel.add(headTextField);
        detailPanel.add(chosenLabel);

        detailPanel.add(scrollPaneForTable);
        this.add(detailPanel, BorderLayout.EAST);

        //南部面板
        JPanel actionPanel = new JPanel();
        JButton checkOutBtn = new JButton("下单");
        JButton cancelBtn = new JButton("退出");
        actionPanel.add(checkOutBtn);
        actionPanel.add(cancelBtn);
        this.add(actionPanel, BorderLayout.SOUTH);


        this.add(jTabbedPaneCenter, BorderLayout.CENTER);
        this.setVisible(true);


        checkOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(CustomerMainFrame.this, "确定要退出吗?", "注意", JOptionPane.YES_NO_OPTION
                );
                if (result == 0) {
                    CustomerMainFrame.this.dispose();
                }
            }
        });

        tableComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(tableComboBox.getSelectedItem());
            }
        });


    }


    public List<NameNPrice> getTheChosens() {
        List<NameNPrice> nameNPriceList = new ArrayList<>();
        for (int i = 0; i < chosenFoodNameList.size(); i++) {
            NameNPrice nameNPrice = new NameNPrice();
            nameNPrice.setFoodName(chosenFoodNameList.get(i));
            nameNPrice.setPrice(chosenPriceList[i]);
            nameNPrice.setQuantity(quantityList.get(i));
            nameNPriceList.add(nameNPrice);
        }
        return nameNPriceList;
    }


    public void updateTable() {
        rows = getTheChosens();
        chosenDishTableModel.setRows(rows);
        chosenDishTableModel.fireTableDataChanged();
    }


    public void dishPanelSetup() {
        foodNameList = new ArrayList<>();
        priceListc = new int[50];
        jdbcHelper = new JdbcHelper();
        jdbcHelper.setFoodNameList(foodNameList, "c");
        jdbcHelper.setPriceList(priceListc, "c");
        for (int i = 0; i < foodNameList.size(); i++) {
            ImageIcon img = new ImageIcon(CustomerMainFrame.class
                    .getResource("/dishImg/" + foodNameList.get(i) + ".jpg"));
            JLabel jLabel = new JLabel(img);

            JLabel jLabel1 = new JLabel(Integer.toString(priceListc[i]) + "元", JLabel.CENTER);
            JCheckBox jCheckBox = new JCheckBox(foodNameList.get(i));

            JPanel subPanel = new JPanel();

            subPanel.add(jLabel);
            subPanel.add(jLabel1);
            subPanel.add(jCheckBox);

            cantonesePanel.add(subPanel);

            jCheckBox.addActionListener(new CheckBoxListener(subPanel));

        }

        foodNameList.clear();
        priceListc = new int[50];
        jdbcHelper.setFoodNameList(foodNameList, "x");
        jdbcHelper.setPriceList(priceListc, "x");
        for (int i = 0; i < foodNameList.size(); i++) {
            ImageIcon img = new ImageIcon(CustomerMainFrame.class
                    .getResource("/dishImg/" + foodNameList.get(i) + ".jpg"));
            JLabel jLabel = new JLabel(img);

            JLabel jLabel1 = new JLabel(Integer.toString(priceListc[i]) + "元", JLabel.CENTER);
            JCheckBox jCheckBox = new JCheckBox(foodNameList.get(i));


            JPanel subPanel = new JPanel();

            subPanel.add(jLabel);
            subPanel.add(jLabel1);
            subPanel.add(jCheckBox);

            xiangPanel.add(subPanel);

            jCheckBox.addActionListener(new CheckBoxListener(subPanel));
        }


        foodNameList.clear();
        priceListc = new int[50];
        jdbcHelper.setFoodNameList(foodNameList, "d");
        jdbcHelper.setPriceList(priceListc, "d");
        for (int i = 0; i < foodNameList.size(); i++) {
            ImageIcon img = new ImageIcon(CustomerMainFrame.class
                    .getResource("/dishImg/" + foodNameList.get(i) + ".jpg"));
            JLabel jLabel = new JLabel(img);

            JLabel jLabel1 = new JLabel(Integer.toString(priceListc[i]) + "元", JLabel.CENTER);
            JCheckBox jCheckBox = new JCheckBox(foodNameList.get(i));


            JPanel subPanel = new JPanel();

            subPanel.add(jLabel);
            subPanel.add(jLabel1);
            subPanel.add(jCheckBox);

            dessertPanel.add(subPanel);

            jCheckBox.addActionListener(new CheckBoxListener(subPanel));
        }

        //for popularPanel
        foodNameList.clear();
        foodNameList.add("烧味拼盘");
        foodNameList.add("姜汁撞奶");
        foodNameList.add("小炒黄牛");
        priceListc = new int[]{30, 5, 35};
        for (int i = 0; i < foodNameList.size(); i++) {
            ImageIcon img = new ImageIcon(CustomerMainFrame.class
                    .getResource("/dishImg/" + foodNameList.get(i) + ".jpg"));
            JLabel jLabel = new JLabel(img);

            JLabel jLabel1 = new JLabel(Integer.toString(priceListc[i]) + "元", JLabel.CENTER);
            JCheckBox jCheckBox = new JCheckBox(foodNameList.get(i));


            JPanel subPanel = new JPanel();

            subPanel.add(jLabel);
            subPanel.add(jLabel1);
            subPanel.add(jCheckBox);

            popularPanel.add(subPanel);

            jCheckBox.addActionListener(new CheckBoxListener(subPanel));
        }


    }


    public class CheckBoxListener implements ActionListener {

        private JPanel panel;

        public CheckBoxListener(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Component component = panel.getComponent(2);
            JCheckBox jcb = (JCheckBox) component;
            if (jcb.isSelected()) {
                if (chosenFoodNameList.indexOf(jcb.getText()) < 0) {
                    chosenFoodNameList.add(jcb.getText());
                }
            } else {
                chosenFoodNameList.removeIf(val -> val == jcb.getText());
            }
            jdbcHelper.setChosenPriceList(chosenFoodNameList, chosenPriceList);
            updateTable();
        }
    }


    private class MyTableModelListener implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent e) {
            int row = chosenDishTabel.getSelectedRow();
            int col = chosenDishTabel.getSelectedColumn();
//
            String value = (String) chosenDishTabel.getValueAt(row, col);
//            chosenDishTabel.setValueAt(value, row, col);
            quantityList.add(row,value);
        }
    }
}
