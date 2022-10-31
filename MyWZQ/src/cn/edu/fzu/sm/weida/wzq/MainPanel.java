package cn.edu.fzu.sm.weida.wzq;

import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MainPanel extends JPanel implements MouseListener {
    private static final int COLUMN=16,ROW=16,GAP=40;

    private char[][] allChess=new char[ROW][COLUMN];//*--没有棋子, !--黑色棋子, ~--白色棋子

    private boolean isBlack=true;

    private List<Pawn> pawnList=new ArrayList<>();

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
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawImage(new ImageIcon("././img/chessboard.jpg").getImage(),0,0,null);
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
                   //g.fillOval(i*40+5,j*40+5,30,30);
                   g.drawImage(new ImageIcon("././img/blackp.png").getImage(),i*40+5,j*40+5,null);
               }
               if(allChess[i][j]=='~'){
                   g.setColor(Color.WHITE);
                   //g.fillOval(i*40+5,j*40+5,30,30);
                   g.drawImage(new ImageIcon("././img/whitep.png").getImage(),i*40+5,j*40+5,null);
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
            Pawn pawn=new Pawn();
            pawn.setX(check_x);
            pawn.setY(check_y);
            pawn.setIsBlack(true);
            pawnList.add(pawn);
            isBlack=false;
        }else{
            if(allChess[check_x][check_y]!='*'){
                return;
            }
            allChess[check_x][check_y]='~';
            Pawn pawn=new Pawn();
            pawn.setX(check_x);
            pawn.setY(check_y);
            pawn.setIsBlack(false);
            pawnList.add(pawn);
            isBlack=true;
        }
        repaint();
        boolean result=isWon(check_x,check_y);

        if(result){
            if(isBlack){
                JOptionPane.showMessageDialog(MainPanel.this,"白棋获胜");
            }else{
                JOptionPane.showMessageDialog(MainPanel.this,"黑棋获胜");
            }
        }

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

    private boolean isWon(int x,int y){//判断是否有玩家赢了,最后一颗棋子作为参数
        char lastPiece=allChess[x][y]; //取出最后一颗棋子

        //判断横向
        int lastX=x;//
        while(allChess[lastX][y]==lastPiece){//向左判断是否有相同的棋子,如果有,坐标移动
            lastX--;

        }//退出循环时lastX所在棋子已是异色棋

        int horNum=0;
        lastX++;
        while (lastX<16&&allChess[lastX][y]==lastPiece){
            horNum++;
            lastX++;
        }
        if(horNum>=5){
            return true;
        }

        //判断纵向
        int lastY=y;
        while (allChess[x][lastY]==lastPiece){
            lastY--;
        }
        int verticalNum=0;
        lastY++;

        while(lastY<16&&allChess[x][lastY]==lastPiece){
            verticalNum++;
            lastY++;
        }

        if(verticalNum>=5){
            return true;
        }

        //对角线
        lastX=x;
        lastY=y;
        while(allChess[lastX][lastY]==lastPiece){
            lastX--;
            lastY--;
        }
        lastX++;
        lastY++;

        int diagonalNum1=0;
        while(lastX<16&&lastY<16&&allChess[lastX][lastY]==lastPiece){
            diagonalNum1++;
            lastX++;
            lastY++;
        }
        if(diagonalNum1>=5){
            return true;
        }

        lastX=x;
        lastY=y;
        while(lastX<16&&allChess[lastX][lastY]==lastPiece){
            lastX++;
            lastY--;
        }
        lastX--;
        lastY++;

        int diagonalNum2=0;
        while (lastY<16&&allChess[lastX][lastY]==lastPiece){
            diagonalNum2++;
            lastX--;
            lastY++;
        }
        if(diagonalNum2>=5){
            return true;
        }


        return false;
    }


    public void doRetraction(){
        if(pawnList.size()>0){
            Pawn pawn=pawnList.get(pawnList.size()-1);
            allChess[pawn.getX()][pawn.getY()]='*';
            isBlack=pawn.getIsBlack();
            pawnList.removeIf(val->val==pawn);
            repaint();
        }else{
            JOptionPane.showMessageDialog(MainPanel.this,"棋盘上已经没有棋子了");
        }
    }
}
