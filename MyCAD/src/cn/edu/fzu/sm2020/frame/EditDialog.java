package cn.edu.fzu.sm2020.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditDialog extends JDialog {

    public EditDialog(MyPaint myPaint) {
        super(myPaint);
        this.setSize(300,200);
        this.setLocationRelativeTo(null);
        this.setTitle("图形编辑");
        this.setVisible(true);
        GridLayout gridLayout=new GridLayout(3,2);
        this.setLayout(gridLayout);


        JLabel labelForWidth=new JLabel("线条宽度");
        this.add(labelForWidth);
        JTextField widthTF=new JTextField();
        this.add(widthTF);


        JLabel labelForColor=new JLabel("颜色选择");
        this.add(labelForColor);
        JButton colorButton=new JButton();
        this.add(colorButton);


        JButton okBtn=new JButton("确定");
        this.add(okBtn);

        JButton cancelBtn=new JButton("取消");
        this.add(cancelBtn);


        if (myPaint.currentSelectLine!=null){
            widthTF.setText(""+myPaint.currentSelectLine.width);
            colorButton.setBackground(myPaint.currentSelectLine.color);
        }

        initBtnListeners(widthTF,colorButton,okBtn,cancelBtn,myPaint);

    }

    private void initBtnListeners(JTextField widthTF, JButton colorButton, JButton okBtn
            , JButton cancelBtn, MyPaint myPaint) {

        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Color color = JColorChooser.showDialog(EditDialog.this,"选择线条的颜色"
                        ,colorButton.getBackground());
                colorButton.setBackground(color);
            }
        });


        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myPaint.currentSelectLine!=null){
                    myPaint.currentSelectLine.color=colorButton.getBackground();
                    try{
                        int width=Integer.parseInt(widthTF.getText());
                        myPaint.currentSelectLine.width=width;
                    }catch (Exception e1){
                        JOptionPane.showMessageDialog(EditDialog.this
                                ,"请输入正确的数值");
                    }
                    myPaint.drawPanel.repaint();
                }
                EditDialog.this.dispose();
            }
        });


        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditDialog.this.dispose();
            }
        });
    }
}

