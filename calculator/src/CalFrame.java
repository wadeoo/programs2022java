import com.sun.xml.internal.ws.util.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalFrame extends JFrame implements ActionListener {
    private final JLabel resultLabel;
    private final JButton btn7,btn8,btn9,btn4,btn5,btn6,btn1,btn2,btn3,btn0,btnMulti,btnEqual,btnAdd,btnSubt
            ,btnDiv,btnDot,btnBack,btnClear;
    private  final  Dimension btnDim=new Dimension(70,40);
    private  String operand1="", operand2="";//运算子
    private int  operatorType = 0;//1为+, 2为-, 3为*
    private String operation="0";
    private String sresult;//
    private ScriptEngineManager mgr =new ScriptEngineManager();
    private ScriptEngine engine =mgr.getEngineByName("JavaScript");


    //regex
    String OperatorAndDotMatch =".*[\\+\\-*/.]$";
    String IllegalDotUsePreventMatch=".*[0-9]*\\.[0-9]*$";

    public CalFrame() throws HeadlessException {

        //窗口的基础初始化
        setSize(320,600);
        setTitle("计算器");
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //设置图标
        ImageIcon icon = new ImageIcon("./img/calculator2.png");
        setIconImage(icon.getImage());

        //设置透明度
        //setUndecorated(true);
        //setBackground(new Color(0.f,0.f,0.f,0.5f));
        //getContentPane().setBackground(new Color(1.0f,1.0f,1.0f,0.0f));


        resultLabel = new JLabel(operation,JLabel.RIGHT);
        resultLabel.setSize(295,40);
        resultLabel.setLocation(5,5);
        Font resultFont = new Font("黑体",Font.BOLD,30);
        resultLabel.setFont(resultFont);
        resultLabel.setOpaque(true);
        resultLabel.setBackground(Color.WHITE);//设置标签背景色
        add(resultLabel);

        //Clear
        btnClear=new JButton("C");
        btnClear.setLocation(5,50);
        btnClear.setSize(btnDim);
        btnClear.setFont(resultFont);
        btnClear.addActionListener(this);
        add(btnClear);


        //back
        btnBack=new JButton("B");
        btnBack.setLocation(155,50);
        btnBack.setSize(btnDim);
        btnBack.setFont(resultFont);
        btnBack.addActionListener(this);
        add(btnBack);


        //7
        btn7=new JButton("7");
        btn7.setLocation(5,95);
        btn7.setSize(btnDim);
        btn7.setFont(resultFont);
        btn7.addActionListener(this);
        add(btn7);

        //8
        btn8 = new JButton("8");
        btn8.setLocation(80,95);
        btn8.setSize(btnDim);
        btn8.setFont(resultFont);
        btn8.addActionListener(this);
        add(btn8);

        //9
        btn9 = new JButton("9");
        btn9.setLocation(155,95);
        btn9.setSize(btnDim);
        btn9.setFont(resultFont);
        btn9.addActionListener(this);
        add(btn9);

        //4
        btn4 = new JButton("4");
        btn4.setLocation(5,140);
        btn4.setSize(btnDim);
        btn4.setFont(resultFont);
        btn4.addActionListener(this);
        add(btn4);

        //5
        btn5 = new JButton("5");
        btn5.setLocation(80,140);
        btn5.setSize(btnDim);
        btn5.setFont(resultFont);
        btn5.addActionListener(this);
        add(btn5);

        //6
        btn6 = new JButton("6");
        btn6.setLocation(155,140);
        btn6.setSize(btnDim);
        btn6.setFont(resultFont);
        btn6.addActionListener(this);
        add(btn6);

        //1
        btn1 = new JButton("1");
        btn1.setLocation(5,185);
        btn1.setSize(btnDim);
        btn1.setFont(resultFont);
        btn1.addActionListener(this);
        add(btn1);

        //2
        btn2 = new JButton("2");
        btn2.setLocation(80,185);
        btn2.setSize(btnDim);
        btn2.setFont(resultFont);
        btn2.addActionListener(this);
        add(btn2);

        //3
        btn3 = new JButton("3");
        btn3.setLocation(155,185);
        btn3.setSize(btnDim);
        btn3.setFont(resultFont);
        btn3.addActionListener(this);
        add(btn3);

        //0
        btn0 = new JButton("0");
        btn0.setLocation(80,230);
        btn0.setSize(btnDim);
        btn0.setFont(resultFont);
        btn0.addActionListener(this);
        add(btn0);

        //除法
        btnDiv= new JButton("÷");
        btnDiv.setLocation(230,50);
        btnDiv.setSize(btnDim);
        btnDiv.setFont(resultFont);
        btnDiv.addActionListener(this);
        add(btnDiv);

        //*
        btnMulti= new JButton("X");
        btnMulti.setLocation(230,95);
        btnMulti.setSize(btnDim);
        btnMulti.setFont(resultFont);
        btnMulti.addActionListener(this);
        add(btnMulti);

        //-
        btnSubt= new JButton("-");
        btnSubt.setLocation(230,140);
        btnSubt.setSize(btnDim);
        btnSubt.setFont(resultFont);
        btnSubt.addActionListener(this);
        add(btnSubt);

        //+
        btnAdd= new JButton("+");
        btnAdd.setLocation(230,185);
        btnAdd.setSize(btnDim);
        btnAdd.setFont(resultFont);
        btnAdd.addActionListener(this);
        add(btnAdd);

        //.
        btnDot= new JButton(".");
        btnDot.setLocation(155,230);
        btnDot.setSize(btnDim);
        btnDot.setFont(resultFont);
        btnDot.addActionListener(this);
        add(btnDot);

        //=
        btnEqual= new JButton("=");
        btnEqual.setLocation(230,230);
        btnEqual.setSize(btnDim);
        btnEqual.setFont(resultFont);
        btnEqual.addActionListener(this);
        add(btnEqual);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==btn7){
            if(operation=="0"){
                operation="";
            }
            operation+="7";
            resultLabel.setText(operation.replace("/","÷"));
        }else if(e.getSource()==btn8){
            if(operation=="0"){
                operation="";
            }
            operation+="8";
            resultLabel.setText(operation.replace("/","÷"));
        }else if(e.getSource()==btn9){
            if(operation=="0"){
                operation="";
            }
            operation+="9";
            resultLabel.setText(operation.replace("/","÷"));
        }else if(e.getSource()==btn4){
            if(operation=="0"){
                operation="";
            }
            operation+="4";
            resultLabel.setText(operation.replace("/","÷"));
        }else if(e.getSource()==btn5){
            if(operation=="0"){
                operation="";
            }
            operation+="5";
            resultLabel.setText(operation.replace("/","÷"));
        }else if(e.getSource()==btn6){
            if(operation=="0"){
                operation="";
            }
            operation+="6";
            resultLabel.setText(operation.replace("/","÷"));
        }else if(e.getSource()==btn1){
            if(operation=="0"){
                operation="";
            }
            operation+="1";
            resultLabel.setText(operation.replace("/","÷"));
        }else if(e.getSource()==btn2){
            if(operation=="0"){
                operation="";
            }
            operation+="2";
            resultLabel.setText(operation.replace("/","÷"));
        }else if(e.getSource()==btn3){
            if(operation=="0"){
                operation="";
            }
            operation+="3";
            resultLabel.setText(operation.replace("/","÷"));
        }else if(e.getSource()==btn0){
            if(operation!="0"){
                operation+="0";
                resultLabel.setText(operation.replace("/","÷"));
            }
        }else if(e.getSource()==btnDiv){
           if(!operation.matches(OperatorAndDotMatch)){
                operation+="/";
                resultLabel.setText(operation.replace("/","÷"));
            }
        }else if(e.getSource()==btnMulti){
            if(!operation.matches(OperatorAndDotMatch)){
                operation+="*";
                resultLabel.setText(operation.replace("/","÷"));
            }

        }else if(e.getSource()==btnAdd){
            if(!operation.matches(OperatorAndDotMatch)){
                operation+="+";
                resultLabel.setText(operation.replace("/","÷"));
            }
        }else if (e.getSource()==btnSubt) {
            if(!operation.matches(OperatorAndDotMatch)){
                operation+="-";
                resultLabel.setText(operation.replace("/","÷"));
            }
        }else if(e.getSource()==btnDot){
            if(!operation.matches(OperatorAndDotMatch)&&!operation.matches(IllegalDotUsePreventMatch)){
                operation+=".";
                resultLabel.setText(operation.replace("/","÷"));
            }
        }else if (e.getSource()==btnBack){
            if(operation!="0"){
                operation= operation.replaceAll(".$","");
                if(operation.equals("")){
                    operation="0";
                }
                resultLabel.setText(operation.replace("/","÷"));
            }
        }else if(e.getSource()==btnClear){
            operation="0";
            resultLabel.setText(operation.replace("/","÷"));
        }else if(e.getSource()==btnEqual){
            try {
                sresult=engine.eval(operation).toString();
            } catch (ScriptException e1) {
                e1.printStackTrace();
            }
            resultLabel.setText(sresult);
            operation=sresult;
        }

    }
}
