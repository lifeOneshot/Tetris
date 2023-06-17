package Tetris;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Scoreboard extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {
			Scoreboard dialog = new Scoreboard();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void UpdateScore() {
		String scoreData = ScoreSave.read("scores.txt");
		String[] scoreLine = scoreData.split("&&");
		String[] score = new String[10]; // 점수가 없는 공백 생성을 위한 배열
		System.arraycopy(scoreLine, 0, score, 0, scoreLine.length); // scoreLine 내용 score에 복사
		String PrintScore = "<html><h2>"; // Label에 들어갈 이름과 점수 리스트 생성
		
		for (int i = 0; i < score.length; i++) {
		    if (score[i] != null) {
		        PrintScore += String.format("&nbsp;&nbsp; %s<br>", scoreLine[i]);
		    }
		    else {
		        PrintScore += String.format("<br>");
		    }
		}
		PrintScore += "</h2></html>";
		lblNewLabel.setText(PrintScore);

	}
	
	/**
	 * Create the dialog.
	 */
	public Scoreboard() {
		setTitle("점수판");
		setBounds(100, 100, 300, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		

		{
			lblNewLabel = new JLabel("");
			lblNewLabel.setToolTipText("이름+점수");
			contentPanel.add(lblNewLabel, BorderLayout.CENTER);
			UpdateScore();//초기 점수 설정
		}

		{
			JLabel lblNewLabel_1 = new JLabel("<html><div align=\"right\"><h2>\r\n1.<br>\r\n2.<br>\r\n3.<br>\r\n4.<br>\r\n5.<br>\r\n6.<br>\r\n7.<br>\r\n8.<br>\r\n9.<br>\r\n&nbsp;&nbsp;&nbsp;&nbsp;10.<br>\r\n</h3></html>");
			lblNewLabel_1.setToolTipText("순위");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel_1, BorderLayout.WEST);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("<html>\r\n<h2>\r\n<font color=\"FF3300\">\r\nBEST SCORE\r\n</font>\r\n</h2>\r\n<html>");
			lblNewLabel_2.setToolTipText("BEST SCORE");
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel_2, BorderLayout.NORTH);
		}

		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Scoreboard.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
			
		}
	}

}
