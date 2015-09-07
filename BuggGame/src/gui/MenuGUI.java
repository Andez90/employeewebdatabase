package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuGUI extends JFrame implements ActionListener{
	private GameWindow gameWindow = new GameWindow ();
	private HighScoreWindow highScoreWindow = new HighScoreWindow ();
	private AboutWindow aboutWindow = new AboutWindow();
	private JMenuBar menuBar = new JMenuBar ();
	private JMenu arkive = new JMenu ("Arkiv");
	private JMenu help = new JMenu ("Hjälp");
	private JMenuItem newGame = new JMenuItem ("Nytt Spel");
	private JMenuItem highScore = new JMenuItem ("Topplista");
	private JMenuItem about = new JMenuItem ("Om");
	private JMenuItem exit = new JMenuItem ("Avsluta");
	private JButton buttonNewGame = new JButton ("Nytt Spel");
	private JButton buttonHighScore = new JButton ("Topplista");
	private JButton buttonAbout = new JButton ("Om");
	private JButton buttonExit = new JButton ("Avsluta");
	
	public MenuGUI (){
		setSize (300, 600);
		setLayout (new FlowLayout());
		createMenuBar ();
		createButtons ();
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setVisible (true);
	}
	
	public void createMenuBar (){
		setJMenuBar(menuBar);
		menuBar.add (arkive);
		menuBar.add (help);
		arkive.add (newGame);
		arkive.add (highScore);
		arkive.add (exit);
		help.add (about);
		newGame.addActionListener (this);
		highScore.addActionListener (this);
		exit.addActionListener (this);
		about.addActionListener (this);
	}
	
	public void createButtons (){
		add (buttonNewGame);
		add (buttonHighScore);
		add (buttonAbout);
		add (buttonExit);
		buttonNewGame.addActionListener (this);
		buttonHighScore.addActionListener (this);
		buttonAbout.addActionListener (this);
		buttonExit.addActionListener (this);
	}
	
	@Override
	public void actionPerformed (ActionEvent e) {
		if (e.getSource () == buttonNewGame || e.getSource () == newGame){
			gameWindow.gameWindow();
		}
		
		else if (e.getSource () == buttonHighScore || e.getSource () == highScore){
			try {
				highScoreWindow.highScoreWindow();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		else if (e.getSource () == buttonAbout || e.getSource () == about){
			aboutWindow.aboutWindow();
		}

		else if (e.getSource () == buttonExit || e.getSource () == exit){
			dispose ();
		}
	}
}
