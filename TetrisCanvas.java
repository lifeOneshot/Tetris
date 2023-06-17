package Tetris;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import Tetris.Piece;

public class TetrisCanvas extends JPanel implements Runnable, KeyListener, Serializable {
   protected Thread worker;
   protected Color colors[];
   protected int w = 25;
   protected TetrisData data;
   protected int margin = 20;
   protected boolean stop, makeNew;
   protected Piece current;
   protected int interval = 2000;
   protected int level = 2;
   protected Piece nextPiece;  //추가
   protected JPanel savePanel;  // 추가
   protected Piece savedPiece; //추가
   protected Piece temp;
   protected boolean canSwap = true;

   public TetrisCanvas() {
      data = new TetrisData();
      addKeyListener(this);
      colors = new Color[8];
      colors[0] = new Color(80, 80, 80); // 검은 회색
      colors[1] = new Color(255, 0, 0); // 빨간색
      colors[2] = new Color(0, 255, 0); // 녹색
      colors[3] = new Color(0, 200, 255); // 노란색
      colors[4] = new Color(255, 255, 0); // 하늘색
      colors[5] = new Color(255, 150, 0); // 황토색
      colors[6] = new Color(210, 0, 240); // 보라색
      colors[7] = new Color(40, 0, 240); // 파란색
      
      temp = randomPiece();

   }

   public void start() {
      data.clear();
      
      worker = new Thread(this);
      worker.start();
      makeNew = true;
      stop = false;
      requestFocus();
      repaint();
      
   }

   public void stop() {
		String dir = "scores.txt";
		stop = true;
		current = null;
		int score = data.getLine() * 175 * level;
		String userName = JOptionPane.showInputDialog(null, "게임이 끝났습니다.\n점수: " + score + "\n(공백 없이)이름을 입력해주세요:"); //입력 다이얼로그
		userName = userName.trim();
		System.out.println(userName);
		while (userName == null || userName.isEmpty() || userName.contains(" ")) { // 창을 바로 닫을 시, 이름이 공백일 시, 이름에 공백이 들어가 있을 시
		       	if (userName == null) {
		            userName = JOptionPane.showInputDialog(null, "게임이 끝났습니다.\n점수: " + score + "\n(공백 없이)이름을 입력해주세요:");
		        } else {
		            userName = JOptionPane.showInputDialog(null, "잘 못된 이름입니다.\n공백 없이 이름을 다시 입력해주세요:");
		        }
		}
		String scoreData = ScoreSave.read(dir); //기존 데이터
		scoreData += userName+ " " + Integer.toString(score); //데이터 추가

		ScoreSave.clearFile(dir); //덮어쓰기 위해 이전 데이터 삭제, due to 'save'메소드가 이전 결과에 이어서 쓰기 때문
		ScoreSave.sorting(scoreData); // 파일에 저장
   }

   public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < TetrisData.ROW; i++) {
            for (int k = 0; k < TetrisData.COL; k++) {
                if (data.getAt(i, k) == 0) {
                    g.setColor(colors[data.getAt(i, k)]);
                    g.draw3DRect(margin / 2 + w * k, margin / 2 + w * i, w, w, true);
                } else {
                    g.setColor(colors[data.getAt(i, k)]);
                    g.fill3DRect(margin / 2 + w * k, margin / 2 + w * i, w, w, true);
                }
            }
        }

        if (current != null) {
            for (int i = 0; i < 4; i++) {
                g.setColor(colors[current.getType()]);
                g.fill3DRect(
                        margin / 2 + w * (current.getX() + current.c[i]),
                        margin / 2 + w * (current.getY() + current.r[i]),
                        w, w, true
                );
            }
        }
        
        if (nextPiece != null) { //추가
           int nextPieceX = margin /４ + w * (12+ nextPiece.getMinX());
           int nextPieceY = margin / 2 + w * (0+ nextPiece.getMaxY()+1);
           g.setColor(Color.BLACK);
           g.drawString("Next Piece", nextPieceX, nextPieceY -w/2 );
            for (int i = 0; i < 4; i++) {
                g.setColor(colors[nextPiece.getType()]);
                g.fill3DRect(
                        margin / 2 + w * (TetrisData.COL + 2 + nextPiece.c[i]),
                        margin / 2 + w * (2 + nextPiece.r[i]),
                        w, w, true
                ); 
            }
        }
        
           if (savedPiece != null) {  //추가
              int savedPieceX = margin / 2 + w * (12+ savedPiece.getMinX());
               int savedPieceY = margin / 2 + w * (6 + savedPiece.getMaxY() + 1);
               g.setColor(Color.BLACK);
               g.drawString("Saved Piece", savedPieceX, savedPieceY - w / 2);
            for (int i = 0; i < 4; i++) {
                g.setColor(colors[savedPiece.getType()]);
                g.fill3DRect(
                    margin / 2 + w * (12 + savedPiece.c[i]),
                    margin / 2 + w * (8 + savedPiece.r[i]),
                    w, w, true);
            }
            if (!canSwap) {
                g.setColor(Color.RED); // 교환이 불가능할 때 빨간색으로 표시
                g.drawString("교환불가", savedPieceX+w+60, savedPieceY - w / 2);
            }
           }


    }

   public JPanel getSavedPiecePanel() { //추가
      return savePanel;
   }

   public Piece getSavedPiece() { //추가
      return savedPiece;
   }

   public Color[] getColors() {
      return colors;
   }

   public Dimension getPreferredSize() { 
      int tw = w * TetrisData.COL + margin;
      int th = w * TetrisData.ROW + margin + w * 6 + margin;
      return new Dimension(tw, th);
   }

   public void run() {
      
      while (!stop) {
            if (makeNew) {
               current = temp;
               nextPiece = randomPiece();
               temp = nextPiece;
               makeNew = false;
               canSwap = true; // 다음 블록이 나오면 교체 가능하도록 설정
            } else {
               if (data.timeCtrl(1)) {
                  Down();
                  
               }
               data.removeLines();
               repaint();
            }
            
      }
   }
   
   public Piece getCurrent() { //추가
	   return current;
   }

   public Piece randomPiece() { //추가
      Piece pic;
      int random = (int) (Math.random() * Integer.MAX_VALUE) % 7;
      switch (random) {
      case 0:
         pic = new EI(data);
         break;
      case 1:
         pic = new EJ(data);
         break;
      case 2:
         pic = new EL(data);
         break;
      case 3:
         pic = new EO(data);
         break;
      case 4:
         pic = new ES(data);
         break;
      case 5:
         pic = new ET(data);
         break;
      case 6:
         pic = new EZ(data);
         break;
      default:
         pic = null;
         break;
      }
      return pic;
   }
   

   protected void savePiece() {
       if (savedPiece == null) {
           savedPiece = current;
           current = nextPiece;
           nextPiece =randomPiece();
           makeNew = true;
       } else if (canSwap) {
           Piece tempPiece = savedPiece;
           savedPiece = current;
           current = tempPiece;
           canSwap = false;
       }
       savedPiece.center = new Point(4,0);
       repaint();
   }

   public void keyPressed(KeyEvent e) {
      if (current == null)
         return;

      switch (e.getKeyCode()) {
      case 37: // 왼쪽 화살표
         current.moveLeft();
         repaint();
         break;
      case 39: // 오른쪽 화살표
         current.moveRight();
         repaint();
         break;
      case 38: // 윗쪽 화살표
         current.rotate();
         repaint();
         break;
      case 40: // 아랫쪽 화살표
         Down();
         repaint();
         break;
      case 67: // c 키  추가
         if(canSwap) {
         savePiece();
         canSwap = false;
         }
         break;
      case 32: // 스페이스 바 추가
         while (!current.moveDown()) {
         }
         makeNew = true;
         if (current.copy()) {
            stop();

         }
         data.removeLines();
         repaint();
      }
   }
   
   public void Down() {
      if (current.moveDown()) {
         makeNew = true;
         if (current.copy()) {
            stop();

         }
      }
   }

   public void showNextPiece(Graphics g) { //추가
      if (nextPiece != null) {
         for (int i = 0; i < 4; i++) {
            g.setColor(colors[nextPiece.getType()]);
            g.fill3DRect(margin / 2 + w * (TetrisData.COL + 2 + nextPiece.c[i]),
                  margin / 2 + w * (2 + nextPiece.r[i]), w, w, true);
         }
      }
   }

   public void showSavedPiece(Graphics g) { //추가
      if (savedPiece != null) {
         for (int i = 0; i < 4; i++) {
            g.setColor(colors[savedPiece.getType()]);
            g.fill3DRect(margin / 2 + w * (3 + savedPiece.c[i]), margin / 2 + w * (-2 + savedPiece.r[i]), // 2칸 위로
                                                                                    // 이동
                  w, w, true);
         }
      }
   }

   public void keyReleased(KeyEvent e) {
   }

   public void keyTyped(KeyEvent e) {
   }
}