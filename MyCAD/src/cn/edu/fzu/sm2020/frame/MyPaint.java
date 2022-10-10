package cn.edu.fzu.sm2020.frame;

import cn.edu.fzu.sm2020.shape.Circle;
import cn.edu.fzu.sm2020.shape.Line;
import com.sun.xml.internal.ws.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MyPaint extends JFrame {

    private final JPanel ctrlPanel1;
    private final DrawPanel drawPanel;
    private final JRadioButton rbLine,rbCircle,rbRect;
    private final ButtonGroup btnGroup;
    private int drawType=0;//绘图类型模式,0-直线 ,1-圆

    //for circle
    private Circle circle;
    private List<Circle> circleList= new ArrayList<>();
    private Point p1=null,p2=null;


    //for line
    private Point prePos=null,curPos=null;
    private Line line;
    private List<Line> lineList = new ArrayList<>();
    private boolean isPrePosSet=false;


    class DrawPanel extends JPanel{
        public DrawPanel() {
            setBackground(Color.WHITE);
            addMouseListener(new MyMouseAdapter());//为绘图区域添加鼠标侦听器
            addMouseMotionListener(new MyMouseMotionAdapter());
            Cursor cursor=new Cursor(Cursor.CROSSHAIR_CURSOR);
            this.setCursor(cursor);
        }


        @Override
        protected  void  paintComponent(Graphics g){

           super.paintComponent(g);
           Graphics2D g2d=(Graphics2D)g;

           //draw lines
           if(lineList.size()>0){
                for(int i=0;i<lineList.size();i++){
                    prePos=lineList.get(i).prePos;
                    curPos=lineList.get(i).curPos;
                    g2d.drawLine(prePos.x,prePos.y,curPos.x,curPos.y);
                }
           }

           //draw circles
            if(circleList.size()>0){
                for (int i=0;i<circleList.size();i++){
                    p1=circleList.get(i).point1;
                    p2=circleList.get(i).point2;
                    int radius= (int)(Math.sqrt(Math.pow(p1.x-p2.x,2)+Math.pow(p1.y-p2.y,2))+0.5);
                    Point startPoint =new Point(p1.x-radius,p1.y-radius);
                    int diameter = radius*2;
                    g2d.drawOval(startPoint.x,startPoint.y,diameter,diameter);
                }
            }




        }
    }

    class MyMouseAdapter extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e){
            super.mousePressed(e);
            if(!isPrePosSet){
                if(drawType==0){
                    line = new Line();
                    line.prePos = e.getPoint();
                }else if(drawType==1){
                    circle = new Circle();
                    circle.point1=e.getPoint();
                }

                isPrePosSet=true;
            }else{
                if(drawType==0){
                    line.curPos=e.getPoint();
                    lineList.add(line);
                }else if(drawType==1){
                    circle.point2=e.getPoint();
                    circleList.add(circle);
                }

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
            if(isPrePosSet){
                if (drawType==0){
                    line.curPos=e.getPoint();
                    lineList.add(line);
                    drawPanel.repaint();
                }else if(drawType==1){
                    circle.point2=e.getPoint();
                    circleList.add(circle);
                    drawPanel.repaint();
                }

            }

        }
    }

    private void initBtnsListener() {
       rbLine.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
               drawType=0;
           }
       });

       rbCircle.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
               drawType=1;
           }
       });
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




        //按钮分组
        btnGroup.add(rbLine);
        btnGroup.add(rbCircle);
        btnGroup.add(rbRect);

        //默认直线模式
        rbLine.setSelected(true);

        ctrlPanel1.add(rbLine);
        ctrlPanel1.add(rbCircle);
        ctrlPanel1.add(rbRect);
        add(ctrlPanel1,BorderLayout.NORTH);
        add(drawPanel);

        //
        initBtnsListener();



    }


}
