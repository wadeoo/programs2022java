package cn.edu.fzu.sm2020.frame;

import cn.edu.fzu.sm2020.shape.Line;
import com.sun.xml.internal.ws.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

public class MyPaint extends JFrame {

    private final JPanel ctrlPanel1;
    private final DrawPanel drawPanel;
    private final JRadioButton rbLine,rbCircle,rbRect;
    private final ButtonGroup btnGroup;
    private Point prePos=null,curPos=null;
    private Line line;
    private List<Line> lineList = new ArrayList<>();
    private boolean isPrePosSet=false;

    class DrawPanel extends JPanel{
        public DrawPanel() {
            setBackground(Color.WHITE);
            addMouseListener(new MyMouseAdapter());//为绘图区域添加鼠标侦听器
            addMouseMotionListener(new MyMouseMotionAdapter());
        }


        @Override
        protected  void  paintComponent(Graphics g){

           super.paintComponent(g);
           Graphics2D g2d=(Graphics2D)g;

//           if(prePos!=null&&curPos!=null){
//               g2d.drawLine(prePos.x,prePos.y,curPos.x,curPos.y);
//           }

           if(lineList.size()>0){
                for(int i=0;i<lineList.size();i++){
                    prePos=lineList.get(i).prePos;
                    curPos=lineList.get(i).curPos;
                    g2d.drawLine(prePos.x,prePos.y,curPos.x,curPos.y);
                }
           }


        }
    }

    class MyMouseAdapter extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e){
            super.mousePressed(e);
            if(!isPrePosSet){
                line = new Line();
                line.prePos = e.getPoint();
                isPrePosSet=true;
            }else{
                line.curPos=e.getPoint();
                lineList.add(line);
                isPrePosSet=false;
                drawPanel.repaint();
            }
        }

//        @Override
//        public void mouseDragged(MouseEvent e){
//            super.mouseDragged(e);
//        }
    }


    class  MyMouseMotionAdapter extends MouseMotionAdapter{
        @Override
        public void mouseMoved(MouseEvent e){
            super.mouseMoved(e);
        }
    }

    public MyPaint(String title,Dimension frameDim) throws HeadlessException {

        super(title);
        setSize(frameDim);
        setLocationRelativeTo(null);//窗口居中

        //透明
        //setUndecorated(true);
        //setBackground(new Color(0,0,0,0.5f));

        //设置图标
        ImageIcon icon =new ImageIcon("./././img/paintIcon.png");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //属性声明
        ctrlPanel1 =new JPanel();
        drawPanel=new DrawPanel();
        btnGroup=new ButtonGroup();
        rbLine = new JRadioButton("直线");
        rbCircle=new JRadioButton("画圆");
        rbRect=new JRadioButton("矩形");
        line=new Line();



        //按钮分组
        btnGroup.add(rbLine);
        btnGroup.add(rbCircle);
        btnGroup.add(rbRect);

        //默认直线
        rbLine.setSelected(true);

        ctrlPanel1.add(rbLine);
        ctrlPanel1.add(rbCircle);
        ctrlPanel1.add(rbRect);
        add(ctrlPanel1,BorderLayout.NORTH);
        this.add(drawPanel);





    }
}
