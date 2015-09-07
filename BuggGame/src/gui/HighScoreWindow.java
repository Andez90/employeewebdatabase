package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import database.Connect;
import model.HighScoreModel;

public class HighScoreWindow extends JFrame implements ActionListener{
	private Connect database = new Connect ();
	private JButton resetHighScore = new JButton ("NollställHighScore");
	private JButton close = new JButton ("Stäng");
	private JButton addToHighScore = new JButton ("Läggtill");
	private JLabel highScoreList = new JLabel ();
	private JLabel playerLabel = new JLabel ("Spelar Namn");
	private JTextField playerTextField = new JTextField (15);
	private HighScoreModel highScoreModel = new HighScoreModel ();
	private JScrollPane scrol;
	private int score;
	private ArrayList <HighScoreModel> highScoreArray;
	
	public void highScoreWindow () throws SQLException{
		
		setTitle ("Click the bugg game");
		setSize (400, 600);
		setLayout (new FlowLayout ());
		add (resetHighScore);
		resetHighScore.addActionListener (this);
		add (close);
		close.addActionListener (this);
		getList();
		setVisible (true);
	}
	
	public void registerHighScore (int gameScore){
		score = gameScore;
		setTitle ("Registrera highScore");
		setSize (400, 200);
		setVisible(true);
		setLayout(new FlowLayout());
		add(playerLabel);
		add(playerTextField);
		add(addToHighScore);
		addToHighScore.addActionListener(this);
	}
	
	public void getList () throws SQLException {
		this.add(highScoreList);
		database.connectToDatabase();
		
		highScoreArray = new ArrayList <HighScoreModel> (database.getHighScoreList());
		
		DefaultListModel defaultList = new DefaultListModel();
		JList list = new JList(defaultList);
		int i = 0;
		
		for (HighScoreModel highScore: highScoreArray){
			++i;
			defaultList.addElement (i + " - " + highScore.getPlayer() + " - " + highScore.getScore());
		}
		scrol = new JScrollPane(list);
		scrol.setPreferredSize(new Dimension(375,150));
		add(scrol);
	}
	
	public void uppdateList() throws SQLException{
		remove (scrol);
		remove (highScoreList);
		getList ();
		revalidate ();
		repaint ();
		}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource () == resetHighScore){
			try {
				database.retinDatabase ();
				uppdateList ();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		else if (e.getSource () == close){
			dispose ();
		}
		
		else if (e.getSource() == addToHighScore){
			highScoreModel.setScore(score);
			highScoreModel.setPlayer(playerTextField.getText());
			
			try {
				database.addToDatabase(highScoreModel);
				remove (playerLabel);
				remove (playerTextField);
				remove (addToHighScore);
				dispose();
				highScoreWindow ();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
