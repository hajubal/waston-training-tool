package com.bean;

import java.util.List;

/**
 * test data bean
 *
 */
public class TestUtterance {

	private String intent;
	private String utterance;
	private int vol;
	private List<Score> scores;
	
	public String getIntent() {
		return intent;
	}
	public void setIntent(String intent) {
		this.intent = intent;
	}
	public String getUtterance() {
		return utterance;
	}
	public void setUtterance(String utterance) {
		this.utterance = utterance;
	}
	public int getVol() {
		return vol;
	}
	public void setVol(int vol) {
		this.vol = vol;
	}
	public List<Score> getScores() {
		return scores;
	}
	public void setScores(List<Score> scores) {
		this.scores = scores;
	}


	class Score {
		private String intent;
		private double score;
		
		public String getIntent() {
			return intent;
		}
		public void setIntent(String intent) {
			this.intent = intent;
		}
		public double getScore() {
			return score;
		}
		public void setScore(double score) {
			this.score = score;
		}
	}
}
