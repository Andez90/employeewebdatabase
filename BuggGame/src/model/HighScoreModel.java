package model;

public class HighScoreModel {
	private int score;
	private String player;
	
	public void HighScore (int score, String player) {
		setScore(score);
		setPlayer(player);
	}
	

	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public String getPlayer() {
		return player;
	}

}
