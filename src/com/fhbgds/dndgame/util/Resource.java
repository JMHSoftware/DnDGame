package com.fhbgds.dndgame.util;

public class Resource {

	String location;
	String name;
	
	public Resource(String location, String name){
		this.location = location;
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public String getLocation(){
		return location;
	}
	
}
