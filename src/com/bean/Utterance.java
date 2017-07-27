package com.bean;

/**
 * 말뭉치 bean
 *
 */
public class Utterance {

	private String intent;
	private String utterance;
	
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
}
