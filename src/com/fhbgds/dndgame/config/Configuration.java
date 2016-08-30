package com.fhbgds.dndgame.config;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

	String name = "config";
	Map<String, String> settings = new HashMap<String, String>();
	
	public Configuration(String name){
		this.name = name;
	}
	
	public Configuration load(){
		try{
			this.settings = ConfigurationManager.loadSettings(name);
		}catch(Exception e){
			System.err.println("Error loading config \"" + name + ".cfg\"");
			e.printStackTrace();
			this.settings = ConfigurationManager.loadDefaultSettings(name);
			ConfigurationManager.saveSettings(this);
		}
		return this;
	}
	
	public Configuration loadFrom(String location){
		try{
			this.settings = ConfigurationManager.loadSettings(location);
		}catch(Exception e){
			System.err.println("Error loading config \"" + name + ".cfg\"");
			e.printStackTrace();
			this.settings = ConfigurationManager.loadDefaultSettings(location);
		}
		return this;
	}
	
	public Map<String, String> getSettings(){
		return settings;
	}
}
