package cn.edu.fzu.sm2020.frame;

import cn.edu.fzu.sm2020.shape.Circle;
import cn.edu.fzu.sm2020.shape.Line;
import cn.edu.fzu.sm2020.shape.Star;
import cn.edu.fzu.sm2020.shape.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MyPaint extends JFrame {

    private final JPanel ctrlPanel1;
    private final DrawPanel drawPanel;
    private final JRadioButton rbLine,rbCircle,rbRect,rbStar;
    private final ButtonGroup btnGroup;
    private JButton colorButton;
    private JButton setWidthButton;




    private Color currentColor=Color.BLACK;
    private int currentWidth=1;
    private int drawType=0;//绘图类型模式,0-直线 ,1-圆 2-矩形

    //fot star
    private Star star;
    private List<Star>  starList=new ArrayList<>();
    private  Point starP1=null,starP2=null;

    //for rect
    private Rect rect;
    private List<Rect> rectList= new ArrayList<>();
    private Point rectP1=null, rectP2=null;

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
                    prePos=lineList.get(i).point1;
                    curPos=lineList.get(i).point2;
                    g2d.setColor(lineList.get(i).color);
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
                    g2d.setColor(circleList.get(i).color);
                    g2d.drawOval(startPoint.x,startPoint.y,diameter,diameter);
                }
            }

            //draw rectangles
            if(rectList.size()>0){
                for (int i=0;i<rectList.size();i++){
                    rectP1=rectList.get(i).point1;
                    rectP2=rectList.get(i).point2;
                    int width=rectP2.x-rectP1.x;
                    int height= rectP2.y-rectP1.y;
                    g2d.setColor(rectList.get(i).color);
                    g2d.drawRect(rectP1.x,rectP1.y,width,height);
                }
            }

            //draw star
            if (starList.size()>0){
                for (int i=0;i<starList.size();i++){
                    starP1=starList.get(i).point1;
                    starP2=starList.get(i).point2;

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
                    line.point1 = e.getPoint();
                    line.color=currentColor;
                }else if(drawType==1){
                    circle = new Circle();
                    circle.point1=e.getPoint();
                    circle.color=currentColor;
                }else if(drawType==2){
                    rect = new Rect();
                    rect.point1=e.getPoint();
                    rect.color=currentColor;
                }else if (drawType==3){
                    star=new Star();
                    star.point1=e.getPoint();
                }

                isPrePosSet=true;
            }else{
                if(drawType==0){
                    line.point2=e.getPoint();
                    lineList.add(line);
                }else if(drawType==1){
                    circle.point2=e.getPoint();
                    circleList.add(circle);
                }else if(drawType==2){
                    rect.point2=e.getPoint();
                    rectList.add(rect);
                }else if(drawType==3){
                    star.point2=e.getPoint();
                    starList.add(star);
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
                    line.point2=e.getPoint();
                    lineList.add(line);
                }else if(drawType==1){
                    circle.point2=e.getPoint();
                    circleList.add(circle);
                }else if (drawType==2){
                    rect.point2=e.getPoint();
                    rectList.add(rect);
                }else if(drawType==3){
                    star.point2=e.getPoint();
                    starList.add(star);
                }
                drawPanel.repaint();
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

       rbRect.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
               drawType=2;
           }
       });

       rbStar.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
               drawType=3;
           }
       });


       colorButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               currentColor=JColorChooser.showDialog(null,"选择颜色",Color.BLACK);
               colorButton.setBackground(currentColor);
           }
       });


       setWidthButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               JDialog jd=new JDialog(MyPaint.this);
               jd.setTitle("设置宽度");

               jd.setSize(400,200);
               jd.setLocationRelativeTo(null);

               GridLayout gridLayout=new GridLayout(2,2);
               jd.setLayout(gridLayout);

               JLabel jl=new JLabel("    线条宽度:");
               JTextField widTF=new JTextField();
               JButton okBtn=new JButton("确认");
               JButton cancelBtn=new JButton("取消");


               okBtn.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       try{
                           currentWidth=Integer.parseInt(widTF.getText());
                           jd.dispose();
                       }catch (Exception e1){
                           JOptionPane.showMessageDialog(null,"请输入整数");
                       }
                   }
               });

               cancelBtn.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       jd.dispose();
                   }
               });

               jd.add(jl);
               jd.add(widTF);
               jd.add(okBtn);
               jd.add(cancelBtn);
               jd.setVisible(true);
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
        rbStar=new JRadioButton("五角星");
        colorButton=new JButton("颜色");
        setWidthButton=new JButton("宽度");




        //按钮分组
        btnGroup.add(rbLine);
        btnGroup.add(rbCircle);
        btnGroup.add(rbRect);
        btnGroup.add(rbStar);

        //默认直线模式
        rbLine.setSelected(true);

        ctrlPanel1.add(rbLine);
        ctrlPanel1.add(rbCircle);
        ctrlPanel1.add(rbRect);
        ctrlPanel1.add(rbStar);
        ctrlPanel1.add(colorButton);
        ctrlPanel1.add(setWidthButton);
        add(ctrlPanel1,BorderLayout.NORTH);
        add(drawPanel);

        //
        initBtnsListener();
        colorButton.setBackground(currentColor);

        //热键
        ctrlPanel1.getInputMap().put(KeyStroke.getKeyStroke("F2"),"quit");
        //ctrlPanel1.getActionMap().put("quit",);

    }


}
