package cn.edu.fzu.sm2020.frame;

import cn.edu.fzu.sm2020.shape.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Date;
import java.util.Iterator;

public class MyPaint extends JFrame {

    private final JPanel ctrlPanel1;
    public final DrawPanel drawPanel;
    private final JRadioButton rbLine,rbCircle,rbRect,rbStar,rbCircle2,rbArbitrary;
    private final ButtonGroup btnGroup;
    private JButton colorButton;
    private JButton setWidthButton;
    private JButton saveBtn;
    private JButton openBtn;
    private JButton deleteBtn;
    private JButton editBtn;
    private JRadioButton rbSelect;
    private AllShape allShape;
    private Line currentSelectLineTemp;
    public Line currentSelectLine;
    private Circle currentSelectCircleTemp;
    private Circle currentSelectCircle;





    private Color currentColor=Color.BLACK;
    private int currentWidth=1;
    private int drawType=0;//绘图类型模式,0-直线 ,1-圆 2-矩形
    private boolean isPrePosSet=false;
    private int ctrlForCircle2=0;//1代表第一点画了,2代表第二点画了,第三点画了就回到0

    //fot star
    private Star star;
    private  Point starP1=null,starP2=null;

    //for rect
    private Rect rect;
    private Point rectP1=null, rectP2=null;

    //for circle
    private Circle circle;
    private Point p1=null,p2=null;

    //for circle2
    private  Circle2 circle2;
    private Point c2p1=null,c2p2=null,c2p3=null;


    //for line
    private Point prePos=null,curPos=null;
    private Line line;

    //for arbitrary
    private ArbitraryLine arbitraryLine;
    private Point pointForArbitr;


    class DrawPanel extends JPanel implements KeyListener{
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

           if (allShape.getArbitraryLineList().size()>0){
               for (int i=0;i<allShape.getArbitraryLineList().size();i++){
                   if (allShape.getArbitraryLineList().get(i).pointList.size()>1){
                       System.out.println(""+allShape.getArbitraryLineList().get(i).pointList.size());
                       for (int j=0;j<allShape.getArbitraryLineList().get(i).pointList.size()-1;j++){
                           Point p1=allShape.getArbitraryLineList().get(i).pointList.get(j);
                           Point p2=allShape.getArbitraryLineList().get(i).pointList.get(j+1);
                           g2d.drawLine(p1.x,p1.y,p2.x,p2.y);
                       }
                   }

               }
           }

           //draw lines
           if(allShape.getLineList().size()>0){
                for(int i=0;i<allShape.getLineList().size();i++){
                    prePos=allShape.getLineList().get(i).point1;
                    curPos=allShape.getLineList().get(i).point2;
                    g2d.setColor(allShape.getLineList().get(i).color);
                    BasicStroke bs=new BasicStroke(allShape.getLineList().get(i).width
                            ,BasicStroke.JOIN_ROUND
                            ,BasicStroke.JOIN_ROUND);
                    g2d.setStroke(bs);
                    g2d.drawLine(prePos.x,prePos.y,curPos.x,curPos.y);

                }
           }

           //draw circles
            if(allShape.getCircleList().size()>0){
                for (int i=0;i<allShape.getCircleList().size();i++){
                    p1=allShape.getCircleList().get(i).point1;
                    p2=allShape.getCircleList().get(i).point2;
                    int radius= (int)(Math.sqrt(Math.pow(p1.x-p2.x,2)+Math.pow(p1.y-p2.y,2))+0.5);
                    Point startPoint =new Point(p1.x-radius,p1.y-radius);
                    allShape.getCircleList().get(i).center=p1;
                    allShape.getCircleList().get(i).radius=radius;
                    int diameter = radius*2;
                    BasicStroke bs=new BasicStroke(allShape.getCircleList().get(i).width
                            ,BasicStroke.JOIN_ROUND
                            ,BasicStroke.JOIN_ROUND);
                    g2d.setStroke(bs);
                    g2d.setColor(allShape.getCircleList().get(i).color);
                    g2d.drawOval(startPoint.x,startPoint.y,diameter,diameter);
                }
            }

            //draw circle2s(3 points)
            if(allShape.getCircle2List().size()>0){
                for (int i=0;i<allShape.getCircle2List().size();i++){
                    c2p1=allShape.getCircle2List().get(i).point1;
                    c2p2=allShape.getCircle2List().get(i).point2;
                    c2p3=allShape.getCircle2List().get(i).point3;

                    int x1,x2,x3,y1,y2,y3;
                    x1=c2p1.x;
                    x2=c2p2.x;
                    x3=c2p3.x;
                    y1=c2p1.y;
                    y2=c2p2.y;
                    y3=c2p3.y;

                    try{
                       double denominator =4*(y3-y2)*(x2-x1)-4*(y2-y1)*(x3-x2);
                       int x=(int)((2*(y3-y2)*(x2*x2+y2*y2-x1*x1-y1*y1)-2*(y2-y1)
                               *(x3*x3+y3*y3-x2*x2-y2*y2))/denominator);
                       int y=(int)((-2*(x3-x2)*(x2*x2+y2*y2-x1*x1-y1*y1)+2*(x2-x1)
                                *(x3*x3+y3*y3-x2*x2-y2*y2))/denominator);

                        System.out.println(x+","+y+","+x1);
                        int radius=(int)(Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1))+0.5);
                        int diameter=radius*2;

                        Point startPoint=new Point(x-radius,y-radius);

                        BasicStroke bs=new BasicStroke(allShape.getCircle2List().get(i).width
                                ,BasicStroke.JOIN_ROUND
                                ,BasicStroke.JOIN_ROUND);
                        g2d.setStroke(bs);

                        int ax[]={x1,x2,x3};
                        int ay[]={y1,y2,y3};
                        g2d.drawPolygon(ax,ay,3);

                        g2d.setColor(allShape.getCircle2List().get(i).color);
                        g2d.drawOval(startPoint.x,startPoint.y,diameter,diameter);
                    }catch (Exception e2){
                        System.out.println("/0!");
                    }




                }
            }

            //draw rectangles
            if(allShape.getRectList().size()>0){
                for (int i=0;i<allShape.getRectList().size();i++){
                    rectP1=allShape.getRectList().get(i).point1;
                    rectP2=allShape.getRectList().get(i).point2;
                    int width=rectP2.x-rectP1.x;
                    int height=rectP2.y-rectP1.y;

                    BasicStroke bs=new BasicStroke(allShape.getRectList().get(i).width
                            ,BasicStroke.JOIN_ROUND
                            ,BasicStroke.JOIN_ROUND);
                    g2d.setStroke(bs);

                    g2d.setColor(allShape.getRectList().get(i).color);
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
            if (allShape.getStarList().size()>0){
                for (int i=0;i<allShape.getStarList().size();i++){
                    starP1=allShape.getStarList().get(i).point1;
                    starP2=allShape.getStarList().get(i).point2;
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
                    BasicStroke bs=new BasicStroke(allShape.getStarList().get(i).width
                            ,BasicStroke.JOIN_ROUND
                            ,BasicStroke.JOIN_ROUND);
                    g2d.setStroke(bs);
                    g2d.setColor(allShape.getStarList().get(i).color);
                    g2d.drawPolygon(plg);
                }
            }

            if (currentSelectLineTemp!=null){
                Point p1=currentSelectLineTemp.point1;
                Point p2=currentSelectLineTemp.point2;
                g2d.setColor(Color.RED);
                g2d.drawRect(p1.x-5,p1.y-5,10,10);
                g2d.drawRect(p2.x-5,p2.y-5,10,10);
            }

            if (currentSelectLine!=null){
                Point p1=currentSelectLine.point1;
                Point p2=currentSelectLine.point2;
                g2d.setColor(Color.BLUE);
                g2d.drawRect(p1.x-5,p1.y-5,10,10);
                g2d.drawRect(p2.x-5,p2.y-5,10,10);
            }

            if(currentSelectCircleTemp!=null){
                Point center=currentSelectCircleTemp.center;
                int radius=currentSelectCircleTemp.radius;
                Point p1=new Point(center.x,center.y-radius);
                Point p2=new Point(center.x+radius,center.y);
                Point p3=new Point(center.x,center.y+radius);
                Point p4=new Point(center.x-radius,center.y);
                g2d.setColor(Color.RED);
                g2d.drawRect(p1.x-5,p1.y-5,10,10);
                g2d.drawRect(p2.x-5,p2.y-5,10,10);
                g2d.drawRect(p3.x-5,p3.y-5,10,10);
                g2d.drawRect(p4.x-5,p4.y-5,10,10);
            }

            if (currentSelectCircle!=null){
                Point center=currentSelectCircle.center;
                int radius=currentSelectCircle.radius;
                Point p1=new Point(center.x,center.y-radius);
                Point p2=new Point(center.x+radius,center.y);
                Point p3=new Point(center.x,center.y+radius);
                Point p4=new Point(center.x-radius,center.y);
                g2d.setColor(Color.BLUE);
                g2d.drawRect(p1.x-5,p1.y-5,10,10);
                g2d.drawRect(p2.x-5,p2.y-5,10,10);
                g2d.drawRect(p3.x-5,p3.y-5,10,10);
                g2d.drawRect(p4.x-5,p4.y-5,10,10);
            }


        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode()==KeyEvent.VK_Q){
                System.exit(0);
                System.out.println("?????");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    class MyMouseAdapter extends MouseAdapter{

        @Override
        public void mouseReleased(MouseEvent e){
            if (drawType==5){
                arbitraryLine=new ArbitraryLine();
            }
        }



        @Override
        public void mousePressed(MouseEvent e){
            super.mousePressed(e);
            if(!isPrePosSet){
                if(drawType==0){
                    line = new Line();
                    line.point1 = e.getPoint();
                    line.color=currentColor;
                    line.width=currentWidth;
                    isPrePosSet=true;
                }else if(drawType==1){
                    circle = new Circle();
                    circle.point1=e.getPoint();
                    circle.color=currentColor;
                    circle.width=currentWidth;
                    isPrePosSet=true;
                }else if(drawType==2){
                    rect = new Rect();
                    rect.point1=e.getPoint();
                    rect.color=currentColor;
                    rect.width=currentWidth;
                    isPrePosSet=true;
                }else if (drawType==3){
                    star=new Star();
                    star.point1=e.getPoint();
                    star.color=currentColor;
                    star.width=currentWidth;
                    isPrePosSet=true;
                }

            }else{
                if(drawType==0){
                    line.point2=e.getPoint();
                    allShape.getLineList().add(line);
                    isPrePosSet=false;
                }else if(drawType==1){
                    circle.point2=e.getPoint();
                    allShape.getCircleList().add(circle);
                    isPrePosSet=false;
                }else if(drawType==2){
                    rect.point2=e.getPoint();
                    allShape.getRectList().add(rect);
                    isPrePosSet=false;
                }else if(drawType==3){
                    star.point2=e.getPoint();
                    allShape.getStarList().add(star);
                    isPrePosSet=false;
                }

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
                    allShape.getCircle2List().add(circle2);
                    ctrlForCircle2=0;
                    drawPanel.repaint();
                }

            }

            //for select mode
            if (drawType==1000){

                if (currentSelectLineTemp!=null){
                    currentSelectLine=currentSelectLineTemp;
                    currentSelectLineTemp=null;
                    drawPanel.repaint();
                    deleteBtn.setEnabled(true);
                    editBtn.setEnabled(true);
                }else{
                    currentSelectLine=null;
                }


                if (currentSelectCircleTemp!=null){
                    currentSelectCircle=currentSelectCircleTemp;
                    currentSelectCircleTemp=null;
                    drawPanel.repaint();
                    deleteBtn.setEnabled(true);
                    editBtn.setEnabled(true);
                }else{
                    currentSelectCircle=null;
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
        public  void mouseDragged(MouseEvent e){
            super.mouseDragged(e);
            if (drawType==5){
                pointForArbitr=new Point(e.getX(),e.getY());
                arbitraryLine.pointList.add(pointForArbitr);
                allShape.getArbitraryLineList().add(arbitraryLine);
                drawPanel.repaint();
            }
        }





        @Override
        public void mouseMoved(MouseEvent e){
            super.mouseMoved(e);
            if(isPrePosSet){
                if (drawType==0){
                    line.point2=e.getPoint();
                    allShape.getLineList().add(line);
                }else if(drawType==1){
                    circle.point2=e.getPoint();
                    allShape.getCircleList().add(circle);
                }else if (drawType==2){
                    rect.point2=e.getPoint();
                    allShape.getRectList().add(rect);
                }else if(drawType==3){
                    star.point2=e.getPoint();
                    allShape.getStarList().add(star);
                }
                drawPanel.repaint();
            }

            if(drawType==4){
                if(ctrlForCircle2==2){
                    circle2.point3=e.getPoint();
                    allShape.getCircle2List().add(circle2);

                }
                drawPanel.repaint();
            }
            
            if(drawType==1000){

                for (int i=0;i<allShape.getLineList().size();i++){
                    Line l=allShape.getLineList().get(i);
                    int p1_p2=getDistance(l.point1,l.point2);
                    int m_p1=getDistance(e.getPoint(),l.point1);
                    int m_p2=getDistance(e.getPoint(),l.point2);
                    if ((m_p1+m_p2)<(p1_p2+2)){
                        drawPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        currentSelectLineTemp=l;
                        break;
                    }
                    else{
                        drawPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        currentSelectLineTemp=null;
                    }
                }

                for (int i=0;i<allShape.getCircleList().size();i++){
                    Circle c=allShape.getCircleList().get(i);
                    int r=c.radius;
                    Point center=c.center;
                    int m_center=getDistance(center,e.getPoint());
                    if (Math.abs(r-m_center)<5){
                        drawPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        currentSelectCircleTemp=c;
                        break;
                    }else{
                        drawPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        currentSelectCircleTemp=null;
                    }
                }
                drawPanel.repaint();
            }

        }
    }

    private void initBtnsListener() {


        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               JFileChooser  fileChooser=new JFileChooser("D:/BASIC/Desktop");
                FileNameExtensionFilter fileNameExtensionFilter
                        =new FileNameExtensionFilter("CAD(*.cad)"
                        ,"cad");
                fileChooser.setFileFilter(fileNameExtensionFilter);
                fileChooser.showSaveDialog(MyPaint.this);
                File file = fileChooser.getSelectedFile();

                if(file!=null){
                    if (!file.getPath().endsWith(".cad")){
                        file=new File(file.getPath()+".cad");
                    }
                    try{
                        FileOutputStream fos=new FileOutputStream(file.getAbsolutePath());
                        ObjectOutputStream oos=new ObjectOutputStream(fos);
                        oos.writeObject(allShape); //将所有图形的对象写到对象输出流
                        oos.close();
                        fos.close();
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }

            }
        });

        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser  fileChooser=new JFileChooser("D:/BASIC/Desktop");
                FileNameExtensionFilter fileNameExtensionFilter
                        =new FileNameExtensionFilter("CAD(*.cad)"
                        ,"cad");
                fileChooser.setFileFilter(fileNameExtensionFilter);
                fileChooser.showOpenDialog(MyPaint.this);
                File file = fileChooser.getSelectedFile();

                try{
                        FileInputStream fis=new FileInputStream(file);
                        ObjectInputStream ois=new ObjectInputStream(fis);
                        allShape= (AllShape) ois.readObject(); //将所有图形的对象写到对象输出流
                        ois.close();
                        fis.close();
                        drawPanel.repaint();
                }catch (Exception e1){
                        e1.printStackTrace();
                    }
                System.out.println(file.getPath());
            }
        });

        rbArbitrary.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
               drawType=5;

               Toolkit toolkit=Toolkit.getDefaultToolkit();
               Image image=toolkit.getImage("./././img/pencil-cursor.png");
               Cursor cursor=toolkit.createCustomCursor(image
                       ,new Point(0,31),"pen cursor");
               System.out.println(drawPanel.getY()+","+drawPanel.getX());
               drawPanel.setCursor(cursor);
           }
       });



       rbLine.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
               //isPrePosSet=(isPrePosSet)? false : true;
               drawType=0;
               Cursor cursor=new Cursor(Cursor.CROSSHAIR_CURSOR);
               drawPanel.setCursor(cursor);
           }
       });

       rbCircle.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
               drawType=1;
               Cursor cursor=new Cursor(Cursor.CROSSHAIR_CURSOR);
               drawPanel.setCursor(cursor);
           }
       });

       rbRect.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
               drawType=2;
               Cursor cursor=new Cursor(Cursor.CROSSHAIR_CURSOR);
               drawPanel.setCursor(cursor);
           }
       });

       rbStar.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
               drawType=3;
               Cursor cursor=new Cursor(Cursor.CROSSHAIR_CURSOR);
               drawPanel.setCursor(cursor);
           }
       });

       rbCircle2.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
               drawType=4;
               Cursor cursor=new Cursor(Cursor.CROSSHAIR_CURSOR);
               drawPanel.setCursor(cursor);
           }
       });

       rbSelect.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
               drawType=1000;//表示选择状态
               Cursor cursor=new Cursor(Cursor.DEFAULT_CURSOR);
               drawPanel.setCursor(cursor);
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

       deleteBtn.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if(currentSelectLine!=null){
                    //allShape.getLineList().removeIf(val->val==currentSelectLine);
                    Iterator iterator=allShape.getLineList().iterator();
                    int count=0;
                    while (iterator.hasNext()){
                        Line x=(Line)iterator.next();
                        if (x==currentSelectLine){
                            iterator.remove();
                            count++;
                        }
                    }
                   System.out.println(count);
                   currentSelectLine=null;
                   drawPanel.repaint();
               }

               if(currentSelectCircle!=null){
                   allShape.getCircleList().removeIf(val->val==currentSelectCircle);
                   currentSelectCircle=null;
                   drawPanel.repaint();
               }
               deleteBtn.setEnabled(false);
           }
       });

       editBtn.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               EditDialog editDialog=new EditDialog(MyPaint.this);

           }
       });

    }

    private int getDistance(Point p1,Point p2){
        int x1=p1.x, y1=p1.y, x2=p2.x, y2=p2.y;
        int d=(int)(Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2))+0.5);
        return d;
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
        saveBtn=new JButton("保存");
        openBtn=new JButton("打开");
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
        deleteBtn=new JButton("删除");
        rbSelect=new JRadioButton("选择");
        editBtn=new JButton("编辑");
        rbArbitrary=new JRadioButton("画笔");
        allShape=new AllShape();

        arbitraryLine=new ArbitraryLine();

        //按钮分组
        btnGroup.add(rbLine);
        btnGroup.add(rbCircle);
        btnGroup.add(rbRect);
        btnGroup.add(rbStar);
        btnGroup.add(rbCircle2);
        btnGroup.add(rbSelect);
        btnGroup.add(rbArbitrary);

        //默认直线模式
        rbLine.setSelected(true);

        deleteBtn.setEnabled(false);//删除按钮默认不可用
        editBtn.setEnabled(false);

        ctrlPanel1.add(saveBtn);
        ctrlPanel1.add(openBtn);
        ctrlPanel1.add(rbArbitrary);
        ctrlPanel1.add(rbLine);
        ctrlPanel1.add(rbCircle);
        ctrlPanel1.add(rbRect);
        ctrlPanel1.add(rbStar);
        ctrlPanel1.add(rbCircle2);
        ctrlPanel1.add(colorButton);
        ctrlPanel1.add(setWidthButton);
        ctrlPanel1.add(deleteBtn);
        ctrlPanel1.add(rbSelect);
        ctrlPanel1.add(editBtn);
        this.add(ctrlPanel1,BorderLayout.NORTH);
        this.add(drawPanel);

        //添加键盘监听器
        this.setVisible(true);
        this.addKeyListener(drawPanel);
        this.requestFocusInWindow();

        //
        initBtnsListener();
        colorButton.setBackground(currentColor);


    }


}
