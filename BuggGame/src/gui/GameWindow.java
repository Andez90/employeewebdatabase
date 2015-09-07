package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import main.Score;

public class GameWindow extends JFrame implements ActionListener{
	private JPanel gamePanel;
	private JButton bugg = new JButton ("BUGG!");
	private JLabel timeLabel;
	private Score score = new Score ();
	private Timer gameTimer;
	private Timer buggTimer;
	private Random random;
	private int time;
	private int x;
	private int y;
	
	public void gameWindow (){
		time = 0;
		score.resetScore();
		setTitle ("Click the bugg game");
		setSize (800, 600);
		setVisible (true);
		setLayout(null);
		gameTimer = new Timer (60000, new ActionListener (){
            public void actionPerformed(ActionEvent e){
            	buggTimer.stop();
            	gameTimer.stop();
            	bugg.setVisible(false);
            	try {
					score.compareScore(score.getScore());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
           		dispose();
            }
        });
		gameTimer.start();
		add(bugg);
		bugg.addActionListener(this);
		bugg.setVisible(false);
		bugg.setSize(75, 50);
		buggTimer = new Timer (1000, new ActionListener (){
            public void actionPerformed(ActionEvent e){
            	time++;
           		generateBugg();
        		bugg.setVisible(true);
        		buggTimer.restart();
            }
        });
        buggTimer.start();
	}
	
	public void generateBugg (){
		random = new Random();
		
		x = random.nextInt(800-75);
		y = random.nextInt(600-50);
		
		bugg.setLocation(x, y);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource () == bugg){
			score.setScore();
			bugg.setVisible(false);
			buggTimer.restart();
		}
	}
}
