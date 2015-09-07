package main;

import gui.HighScoreWindow;

import java.sql.SQLException;
import java.util.ArrayList;

import model.HighScoreModel;
import database.Connect;

public class Score {
	private int score;
	private Connect database = new Connect ();
	private HighScoreWindow highScoreWindow = new HighScoreWindow ();
	
	public void setScore (){
		this.score = score + 1;
		System.out.println(score);
	}
	
	public int getScore (){
		return score;
	}
	
	public void compareScore (int score) throws SQLException{
		database.connectToDatabase();
		ArrayList <HighScoreModel> highScoreList = database.getHighScoreList();
		int size = highScoreList.size();
		
		if (size < 10){
			highScoreWindow.registerHighScore(score);
		}
		
		else if (size >= 10){
			for (int i = 0; i < size; i++){
				HighScoreModel highScoreModel = highScoreList.get(i);
				int curentHighScore = highScoreModel.getScore();
				if (score > curentHighScore){
					database.removeFromDatabase(highScoreModel);
					highScoreWindow.registerHighScore(score);
				}
			}
		}
		
		else{
			highScoreWindow.highScoreWindow();
		}
	}
	
	public void resetScore (){
		this.score = 0;
	}
}
