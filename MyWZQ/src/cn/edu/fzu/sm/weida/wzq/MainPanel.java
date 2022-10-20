package cn.edu.fzu.sm.weida.wzq;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainPanel extends JPanel implements MouseListener {
    private static final int COLUMN=16,ROW=16,GAP=40;

    private char[][] allChess=new char[ROW][COLUMN];//*--没有棋子, !--黑色棋子, ~--白色棋子

    private boolean isBlack=true;

    public MainPanel() {
        for(int i=0;i<ROW;i++){
            for (int j=0;j<COLUMN;j++){
                allChess[i][j]='*';//默认无棋子
            }
        }

        allChess[2][6]='!';
        allChess[11][9]='~';

        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        super.paint(g);
        for(int i=0;i<ROW;i++){
           g.drawLine(20,20+i*GAP,620,20+i*GAP);
        }
        for (int i=0;i<COLUMN;i++){
           g.drawLine(20+i*GAP,20,20+i*GAP,620);
        }

        for (int i=0;i<16;i++){
           for (int j=0;j<16;j++) {
               if(allChess[i][j]=='!'){
                   g.setColor(Color.BLACK);
                   g.fillOval(i*40+5,j*40+5,30,30);
               }
               if(allChess[i][j]=='~'){
                   g.setColor(Color.WHITE);
                   g.fillOval(i*40+5,j*40+5,30,30);
               }
           }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int press_x=e.getX();
        int press_y=e.getY();
        int check_x=(int)Math.round((press_x)/GAP);
        int check_y=(int)Math.round((press_y)/GAP);

        if(isBlack){
            if(allChess[check_x][check_y]!='*'){
               return;
            }
            allChess[check_x][check_y]='!';
            isBlack=false;
        }else{
            if(allChess[check_x][check_y]!='*'){
                return;
            }
            allChess[check_x][check_y]='~';
            isBlack=true;
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
