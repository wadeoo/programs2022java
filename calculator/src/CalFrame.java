import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalFrame extends JFrame implements ActionListener {
    private final JLabel resultLabel;
    private final JButton btn7,btn8,btn9,btnMulti,btnEqual,btnAdd,btnSubt
            ,btnDiv,btnDot,btnBack,btnClear;
    private  final  Dimension btnDim=new Dimension(70,40);
    private  String operand1="", operand2="";//运算子
    private int  operatorType = 0;//1为+, 2为-, 3为*
    private String operation="0";
    private String sresult;//
    private ScriptEngineManager mgr =new ScriptEngineManager();
    private ScriptEngine engine =mgr.getEngineByName("JavaScript");


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




        resultLabel = new JLabel("0",JLabel.RIGHT);
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
        /*if(e.getSource()==btn7) {
            if(operatorType==0) {
                operand1+= "7";
                resultLabel.setText(operand1);
            }else {
               operand2+="7" ;
               resultLabel.setText(operand2);
            }
        }else if(e.getSource()==btn8){
            if(operatorType==0) {
                operand1 += "8";
                resultLabel.setText(operand1);
            }else {
                operand2+="8";
                resultLabel.setText((operand2));
            }
        }else if(e.getSource()==btnMulti){
            operatorType=3;
        }else if(e.getSource()==btnEqual){
            float foperand1= Float.parseFloat(operand1);
            float foperand2= Float.parseFloat(operand2);
            if(operatorType==3){
                float fresult=foperand1*foperand2;
                sresult=Float.toString(fresult);
                resultLabel.setText(sresult);
                operatorType=0;
            }
        }*/


        //
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
        }else if(e.getSource()==btnDiv){
           if(!operation.matches(".*[\\+\\-*/.]$")){
                operation+="/";
                resultLabel.setText(operation.replace("/","÷"));
            }
        }else if(e.getSource()==btnMulti){
            if(!operation.matches(".*[\\+\\-*/.]$")){
                operation+="*";
                resultLabel.setText(operation.replace("/","÷"));
            }

        }else if(e.getSource()==btnAdd){
            if(!operation.matches(".*[\\+\\-*/.]$")){
                operation+="+";
                resultLabel.setText(operation.replace("/","÷"));
            }
        }else if (e.getSource()==btnSubt) {
            if(!operation.matches(".*[\\+\\-*/.]$")){
                operation+="-";
                resultLabel.setText(operation.replace("/","÷"));
            }
        }else if(e.getSource()==btnDot){
//            if(!(operation.endsWith("+")||operation.endsWith("-")
//            ||operation.endsWith("*")||operation.endsWith("/")||operation.endsWith("."))){
//                operation+=".";
//                resultLabel.setText(operation.replace("/","÷"));
//            }
            if(!operation.matches(".*[\\+\\-*/.]$")&&!operation.matches(".*[0-9]*\\.[0-9]*$")){
                operation+=".";
                resultLabel.setText(operation.replace("/","÷"));
            }

        }else if (e.getSource()==btnBack){
            if(operation!="0")
            operation=operation.substring(0,operation.length()-1);
            resultLabel.setText(operation.replace("/","÷"));
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
