package com.bank.model.cash;

public class Check extends Cash {

	private String commentary;
	
	public Check(Double value, String country, String commentary) {
		super(value, country);
		this.commentary = commentary;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

}
