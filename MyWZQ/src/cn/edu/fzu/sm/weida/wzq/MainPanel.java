package cn.edu.fzu.sm.weida.wzq;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private static final int COLUMN=16,ROW=16,GAP=40;

    private char[][] allChess=new char[ROW][COLUMN];//*--没有棋子, !--黑色棋子, ~--白色棋子

    public MainPanel() {
        for(int i=0;i<ROW;i++){
            for (int j=0;j<COLUMN;j++){
                allChess[i][j]='*';//默认无棋子
            }
        }

        allChess[2][6]='!';
        allChess[11][9]='~';
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
}
