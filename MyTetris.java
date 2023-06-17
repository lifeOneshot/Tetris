package Tetris;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.net.Socket;
import java.net.InetAddress;

public class MyTetris extends JFrame  {

    private JPanel contentPane;
    private TetrisCanvas tetrisCanvas;
    private TetrisCanvas multiCanvas;
	private Scoreboard scoreboard = new Scoreboard();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MyTetris frame = new MyTetris();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MyTetris() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 484, 600);


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu gameMenu = new JMenu("게임");
        menuBar.add(gameMenu);

        JMenuItem mntmNewMenuItem = new JMenuItem("시작");
        mntmNewMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tetrisCanvas.start();
                

            }
        });
        gameMenu.add(mntmNewMenuItem);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("종료");
        mntmNewMenuItem_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("점수");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getScoreboard().setVisible(true);
			}
		});
		gameMenu.add(mntmNewMenuItem_2);
        gameMenu.add(mntmNewMenuItem_1);
        
        JMenu multiMenu = new JMenu("멀티");
        menuBar.add(multiMenu);
        
        JMenuItem CreateGame = new JMenuItem("방 만들기");
        CreateGame.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                TetrisServer server = new TetrisServer(1234, tetrisCanvas);
                server.repaint();
        	}
        });
        
        JMenuItem MultiMod = new JMenuItem("멀티모드");
        MultiMod.addActionListener(new ActionListener() {
            boolean multiMod = true;
        	public void actionPerformed(ActionEvent e) {
        		if (multiMod == true) {

                    multiCanvas = new MultiCanvas();
              		setBounds(100, 100, 484+320, 600);
                    contentPane.add(multiCanvas, BorderLayout.CENTER);
                    MultiMod.setText("싱글모드");
                    
                    multiMod = false;
            	}
            	else {
                    setBounds(100, 100, 484, 600);
                    contentPane.remove(multiCanvas);
            		MultiMod.setText("멀티모드");
            		multiMod = true;
            	}
        	}

        	
        });

        multiMenu.add(MultiMod);
        multiMenu.add(CreateGame);
        
        JMenuItem AttendGame = new JMenuItem("참여");
        AttendGame.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new TetrisClient("localhost", tetrisCanvas);
        	}
        });
        multiMenu.add(AttendGame);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        tetrisCanvas = new TetrisCanvas();
        contentPane.add(tetrisCanvas, BorderLayout.CENTER);

    }

    public TetrisCanvas getTetrisCanvas() {
        return tetrisCanvas;
    }
	public Scoreboard getScoreboard() {
		if(scoreboard == null) {
			scoreboard = new Scoreboard();
		}
		return scoreboard;
	}

    
}
