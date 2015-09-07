package database;

import java.sql.*;
import java.util.ArrayList;

import model.HighScoreModel;

public class Connect {
	private static Connection connection = null;
	private static Statement statement = null;
	private ArrayList <HighScoreModel> highScoreList;

	public void connectToDatabase() throws SQLException{
		try{
			highScoreList = new ArrayList<HighScoreModel>();
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * from HighScore ORDER BY Score DESC");
			
			while(resultSet.next()){
				HighScoreModel highScore = new HighScoreModel ();

				highScore.setScore(resultSet.getInt("score"));
				highScore.setPlayer(resultSet.getString("player"));
				
				highScoreList.add(highScore);
			}
		}
		finally{
			try{
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			}
			catch(SQLException exception){
				throw exception;
			}
		}
	}
	
	public ArrayList<HighScoreModel> getHighScoreList(){
		return this.highScoreList;
	}
	
	public void addToDatabase(HighScoreModel highScoreModel) throws SQLException{
		try{
			connection = getConnection();
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO HighScore VALUES (" + highScoreModel.getScore() + ",'" + highScoreModel.getPlayer() + "');");
			connectToDatabase();
		}
		finally{
			try{
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			}
			catch(SQLException exception){
				throw exception;
			}
		}
	}
	
	public void removeFromDatabase(HighScoreModel highScore) throws SQLException{
		try{
			connection = getConnection();
			statement = connection.createStatement();
			
			statement.executeUpdate("DELETE from HighScore WHERE score = " + highScore.getScore() + " and player = '" + highScore.getPlayer() + "' limit 1;");
			connectToDatabase();
		}
		finally{
			try{
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			}
			catch(SQLException exception){
				throw exception;
			}
		}
	}
	
	public void retinDatabase() throws SQLException{
		try{
			connection = getConnection();
			statement = connection.createStatement();
			
			statement.executeUpdate("TRUNCATE HighScore;");
			connectToDatabase();
		}
		finally{
			try{
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			}
			catch(SQLException exception){
				throw exception;
			}
		}
	}
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Game?user=root");
	}
}
