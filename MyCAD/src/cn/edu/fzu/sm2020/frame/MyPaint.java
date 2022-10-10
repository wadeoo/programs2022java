package cn.edu.fzu.sm2020.frame;

import cn.edu.fzu.sm2020.shape.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MyPaint extends JFrame {

    private final JPanel ctrlPanel1;
    private final DrawPanel drawPanel;
    private final JRadioButton rbLine,rbCircle,rbRect,rbStar,rbCircle2;
    private final ButtonGroup btnGroup;
    private JButton colorButton;
    private JButton setWidthButton;




    private Color currentColor=Color.BLACK;
    private int currentWidth=1;
    private int drawType=0;//绘图类型模式,0-直线 ,1-圆 2-矩形
    private boolean isPrePosSet=false;
    private int ctrlForCircle2=0;//1代表第一点画了,2代表第二点画了,第三点画了就回到0

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

    //for circle2
    private  Circle2 circle2;
    private  List<Circle2> circle2List=new ArrayList<>();
    private Point c2p1=null,c2p2=null,c2p3=null;


    //for line
    private Point prePos=null,curPos=null;
    private Line line;
    private List<Line> lineList = new ArrayList<>();




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
                    BasicStroke bs=new BasicStroke(lineList.get(i).width
                            ,BasicStroke.JOIN_ROUND
                            ,BasicStroke.JOIN_ROUND);
                    g2d.setStroke(bs);
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
                    BasicStroke bs=new BasicStroke(circleList.get(i).width
                            ,BasicStroke.JOIN_ROUND
                            ,BasicStroke.JOIN_ROUND);
                    g2d.setStroke(bs);
                    g2d.setColor(circleList.get(i).color);
                    g2d.drawOval(startPoint.x,startPoint.y,diameter,diameter);
                }
            }

            //draw circle2s(3 points)
            if(circle2List.size()>0){
                for (int i=0;i<circle2List.size();i++){
                    c2p1=circle2List.get(i).point1;
                    c2p2=circle2List.get(i).point2;
                    c2p3=circle2List.get(i).point3;

                    Point midP1=new Point(),midP2=new Point();
                    midP1.x=(c2p2.x+c2p1.x)/2;
                    midP1.y=(c2p2.y+c2p1.y)/2;

                    midP2.x=(c2p3.x+c2p1.x)/2;
                    midP2.y=(c2p3.y+c2p1.y)/2;

                    try{
                        float k1=-(c2p2.x-c2p1.x)/(c2p2.y-c2p1.y);
                        float k2=-(c2p3.x-c2p1.x)/(c2p3.y-c2p1.y);

                        int cx=(int)((midP2.y-midP1.y-k2*midP2.x+k1*midP1.x)/(k1-k2)+0.5);
                        int cy=(int)(midP1.y+k1*(midP2.y-midP1.y-k2*midP2.x+k2*midP1.x)/(k1+k2)+0.5);

                        int radius=(int)(Math.sqrt(Math.pow((cx-c2p1.x),2)+Math.pow((cy-c2p1.y),2))+0.5);
                        int diameter=radius*2;

                        Point startPoint=new Point(cx-radius,cy-radius);

                        BasicStroke bs=new BasicStroke(circle2List.get(i).width
                                ,BasicStroke.JOIN_ROUND
                                ,BasicStroke.JOIN_ROUND);
                        g2d.setStroke(bs);
                        g2d.setColor(circle2List.get(i).color);

                        g2d.drawOval(startPoint.x,startPoint.y,diameter,diameter);
                    }catch (Exception e2){
                        System.out.println("/0!");
                    }




                }
            }

            //draw rectangles
            if(rectList.size()>0){
                for (int i=0;i<rectList.size();i++){
                    rectP1=rectList.get(i).point1;
                    rectP2=rectList.get(i).point2;
                    int width=rectP2.x-rectP1.x;
                    int height=rectP2.y-rectP1.y;

                    BasicStroke bs=new BasicStroke(rectList.get(i).width
                            ,BasicStroke.JOIN_ROUND
                            ,BasicStroke.JOIN_ROUND);
                    g2d.setStroke(bs);

                    g2d.setColor(rectList.get(i).color);
                    if(width<0&&height>0){
                        g2d.drawRect(rectP2.x,rectP1.y,-width,height);
                    }else if(width<0&&height<0){
                        g2d.drawRect(rectP2.x,rectP2.y,-width,-height);
                    }else if(width>0&&height<0){
                        g2d.drawRect(rectP1.x,rectP2.y,width,-height);
                    }else{
                        g2d.drawRect(rectP1.x,rectP1.y,width,height);
                    }
                }
            }

            //draw star
            if (starList.size()>0){
                for (int i=0;i<starList.size();i++){
                    starP1=starList.get(i).point1;
                    starP2=starList.get(i).point2;
                    int x1=starP1.x, y1=starP1.y;
                    int radius= (int)(Math.sqrt(Math.pow(starP1.x-starP2.x,2)
                            +Math.pow(starP1.y-starP2.y,2))+0.5);
                    double offset=0.5;
                    double a=Math.cos(0.1*Math.PI),b=Math.cos(0.3*Math.PI)
                            ,c=Math.sin(0.1*Math.PI),d=Math.sin(0.3*Math.PI)
                            ,e=Math.tan(0.3*Math.PI),f=Math.cos(0.2*Math.PI)
                            ,h=radius*c/e,j=Math.sin(0.2*Math.PI);

                    Point p1=new Point(x1,y1-radius);
                    Point p2=new Point((int)(x1+h+offset),(int)(y1-radius*c+offset));
                    Point p3=new Point((int)(x1+radius*a+offset),(int)(y1-radius*c+offset));
                    Point p4=new Point((int)(x1+(2*h*f)+offset),(int)((y1+h/b)-2*h*j+offset));
                    Point p5=new Point((int)(x1+radius*b+offset),(int)(y1+radius*d+offset));
                    Point p6=new Point(x1,(int)(y1+h/b+offset));
                    Point p7=new Point((int)(x1-radius*b+offset),(int)(y1+radius*d+offset));
                    Point p8=new Point((int)(x1-(2*h*f)+offset),(int)((y1+h/b)-2*h*j+offset));
                    Point p9=new Point((int)(x1-radius*a+offset),(int)(y1-radius*c+offset));
                    Point p10=new Point((int)(x1-h+offset),(int)(y1-radius*c+offset));


                    int x[]=new int[]{p1.x,p2.x,p3.x,p4.x,p5.x,p6.x,p7.x,p8.x,p9.x,p10.x};

                    int y[]=new int[]{p1.y,p2.y,p3.y,p4.y,p5.y,p6.y,p7.y,p8.y,p9.y,p10.y};
                    int n=10;

                    Polygon plg=new Polygon(x,y,n);
                    BasicStroke bs=new BasicStroke(starList.get(i).width
                            ,BasicStroke.JOIN_ROUND
                            ,BasicStroke.JOIN_ROUND);
                    g2d.setStroke(bs);
                    g2d.setColor(starList.get(i).color);
                    g2d.drawPolygon(plg);
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
                    line.width=currentWidth;
                }else if(drawType==1){
                    circle = new Circle();
                    circle.point1=e.getPoint();
                    circle.color=currentColor;
                    circle.width=currentWidth;
                }else if(drawType==2){
                    rect = new Rect();
                    rect.point1=e.getPoint();
                    rect.color=currentColor;
                    rect.width=currentWidth;
                }else if (drawType==3){
                    star=new Star();
                    star.point1=e.getPoint();
                    star.color=currentColor;
                    star.width=currentWidth;
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

            //for circle2
            if(drawType==4){
                if(ctrlForCircle2==0){
                    circle2=new Circle2();
                    circle2.color=currentColor;
                    circle2.width=currentWidth;
                    circle2.point1=e.getPoint();
                    ctrlForCircle2=1;
                }else if(ctrlForCircle2==1){
                    circle2.point2=e.getPoint();
                    ctrlForCircle2=2;
                }else if(ctrlForCircle2==2) {
                    circle2.point3=e.getPoint();
                    circle2List.add(circle2);
                    ctrlForCircle2=0;
                    drawPanel.repaint();
                }

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

            if(drawType==4){
                if(ctrlForCircle2==2){
                    circle2.point3=e.getPoint();
                    circle2List.add(circle2);
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

       rbCircle2.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) { drawType=4; }
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
                           setWidthButton.setText("宽度: "+currentWidth);
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
        rbCircle2=new JRadioButton("三点画圆");
        colorButton=new JButton("颜色");
        setWidthButton=new JButton("宽度: "+currentWidth);




        //按钮分组
        btnGroup.add(rbLine);
        btnGroup.add(rbCircle);
        btnGroup.add(rbRect);
        btnGroup.add(rbStar);
        btnGroup.add(rbCircle2);

        //默认直线模式
        rbLine.setSelected(true);

        ctrlPanel1.add(rbLine);
        ctrlPanel1.add(rbCircle);
        ctrlPanel1.add(rbRect);
        ctrlPanel1.add(rbStar);
        ctrlPanel1.add(rbCircle2);
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
