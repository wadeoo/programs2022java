package cn.edu.fzu.sm.wuweida.frame;

import cn.edu.fzu.sm.wuweida.bean.NamePriceQuantity;
import cn.edu.fzu.sm.wuweida.dao.JdbcHelper;
import cn.edu.fzu.sm.wuweida.tableModel.ChosenDishTableModel;

import javax.naming.Name;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerMainFrame extends JFrame {


    private final JPanel popularPanel;
    private final JPanel cantonesePanel;
    private final JPanel xiangPanel;
    private final JPanel dessertPanel;
    private JdbcHelper jdbcHelper;

    private ArrayList<String> foodNameList;
    private int[] priceListc;
    private ArrayList<String> chosenFoodNameList = new ArrayList<>();
    private int[] chosenPriceList = new int[50];
    private ArrayList<String> chosenFoodQuantityList = new ArrayList<>();


    private ArrayList<String> cols;
    private List<NamePriceQuantity> rows;
    private ChosenDishTableModel<NamePriceQuantity> chosenDishTableModel;


    public CustomerMainFrame(String username) throws HeadlessException {
        super("餐厅主页面");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 0));
        this.setIconImage(new ImageIcon(CustomerMainFrame.class.getResource("/restaurant.png")).getImage());


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
        popularPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 100, 10));
        cantonesePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 100, 10));
        xiangPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 100, 10));
        dessertPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 100, 10));
        dishPanelSetup();
        jTabbedPaneCenter.add("火爆菜式", popularPanel);
        jTabbedPaneCenter.add("粤菜", cantonesePanel);
        jTabbedPaneCenter.add("湘菜", xiangPanel);
        jTabbedPaneCenter.add("甜品饮料", dessertPanel);


        //东部面板
        JPanel detailPanel = new JPanel();
        detailPanel.setPreferredSize(new Dimension(200, 400));
        detailPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
//        detailPanel.setLayout(new GridLayout(6,1));
        JLabel tableChoosingLabel = new JLabel("请选择桌号");
        JLabel headLabel = new JLabel("请输入用餐人数");
        JLabel chosenLabel = new JLabel("已点菜式列表");
        String[] tableNumber = {"桌1", "桌2", "桌3", "桌4", "桌5", "桌6"};
        JComboBox tableComboBox = new JComboBox(tableNumber);
        JTextField headTextField = new JTextField(16);
        JTable chosenDishTabel = new JTable();
        JScrollPane scrollPaneForTable = new JScrollPane(chosenDishTabel);
        scrollPaneForTable.setPreferredSize(new Dimension(180, 350));
        detailPanel.add(scrollPaneForTable);

        chosenFoodQuantityList.add("1");
        chosenFoodQuantityList.add("1");
        chosenFoodQuantityList.add("1");
        chosenFoodQuantityList.add("1");
        chosenFoodQuantityList.add("1");
        chosenFoodQuantityList.add("1");
        chosenFoodQuantityList.add("1");
        chosenFoodQuantityList.add("1");
        chosenFoodQuantityList.add("1");
        chosenFoodQuantityList.add("1");

        cols = new ArrayList<>();
        cols.add("菜式");
        cols.add("价格");
        cols.add("份数");
        rows = getTheChosens();
        chosenDishTableModel = new ChosenDishTableModel<NamePriceQuantity>(cols, rows);
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

    public List<NamePriceQuantity> getTheChosens() {
        List<NamePriceQuantity> namePriceQuantityList = new ArrayList<>();
        for (int i = 0; i < chosenFoodNameList.size(); i++) {
            NamePriceQuantity namePriceQuantity = new NamePriceQuantity();
            namePriceQuantity.setFoodName(chosenFoodNameList.get(i));
            namePriceQuantity.setPrice(chosenPriceList[i]);

            if (!chosenFoodQuantityList.get(i).equals(null)) {
                namePriceQuantity.setQuantity(chosenFoodQuantityList.get(i));
            } else {
                namePriceQuantity.setQuantity("1");
            }
            namePriceQuantityList.add(namePriceQuantity);
        }
        return namePriceQuantityList;
    }

    public List<NamePriceQuantity> getTheChosens(JPanel panel) {
        List<NamePriceQuantity> namePriceQuantityList = new ArrayList<>();
        for (int i = 0; i < chosenFoodNameList.size(); i++) {
            NamePriceQuantity namePriceQuantity = new NamePriceQuantity();
            namePriceQuantity.setFoodName(chosenFoodNameList.get(i));
            namePriceQuantity.setPrice(chosenPriceList[i]);

            Component component = panel.getComponent(4);
            JTextField jTextField = (JTextField) component;

            chosenFoodQuantityList.add(i, jTextField.getText());

            if (!chosenFoodQuantityList.get(i).equals(null)) {
                namePriceQuantity.setQuantity(chosenFoodQuantityList.get(i));
            } else {
                namePriceQuantity.setQuantity("1");
            }
            namePriceQuantityList.add(namePriceQuantity);
        }
        return namePriceQuantityList;
    }


    public void updateTable() {
        rows = getTheChosens();
        chosenDishTableModel.setRows(rows);
        chosenDishTableModel.fireTableDataChanged();
    }

    public void updateTable(JPanel panel) {
        rows = getTheChosens(panel);
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
            JLabel jLabel2 = new JLabel("份数:");
            JTextField jTextField = new JTextField(4);

            JPanel subPanel = new JPanel();

            subPanel.add(jLabel);
            subPanel.add(jLabel1);
            subPanel.add(jCheckBox);
            subPanel.add(jLabel2);
            subPanel.add(jTextField);

            jTextField.getDocument().addDocumentListener(new QuantityTextFieldListner(subPanel));

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
                if (chosenFoodNameList.indexOf(jcb.getText()) >= 0) {
                    int index = chosenFoodNameList.indexOf(jcb.getText());
                    chosenFoodQuantityList.remove(index);
                }
                chosenFoodNameList.removeIf(val -> val == jcb.getText());
            }

            jdbcHelper.setChosenPriceList(chosenFoodNameList, chosenPriceList);
            updateTable(panel);
        }


    }


    private class QuantityTextFieldListner implements DocumentListener {
        private JPanel panel;

        public QuantityTextFieldListner(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            Component component = panel.getComponent(4);
            JTextField jTextField = (JTextField) component;
            Component component1 = jTextField.getParent().getComponent(2);
            JCheckBox jCheckBox = (JCheckBox) component1;
            String foodName = jCheckBox.getText();
            int index = chosenFoodNameList.indexOf(foodName);
            if (index >= 0) {
                chosenFoodQuantityList.add(index, jTextField.getText());
                System.out.println("6");
                updateTable();
            }

            System.out.println("fucjkhsdf");
        }

        @Override
        public void removeUpdate(DocumentEvent e) {

        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }
    }
}
