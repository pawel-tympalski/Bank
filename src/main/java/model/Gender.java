package model;

import java.io.Serializable;

public enum Gender implements Serializable{
	
	MALE("Mr."), FEMALE("Ms");
	private String title;
	private Gender(String title) {
		this.title = title;
	}
	
	public String getTitle(){
		
		return title;
	}
}
