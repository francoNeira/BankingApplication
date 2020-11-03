package com.bank.model;

public abstract class Cash {
	
	private Double value;
	
	private String country;
	
	public Cash(Double value, String country) {
		this.value = value;
		this.country = country;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}