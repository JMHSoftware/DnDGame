package com.fhbgds.dndgame.ui;

import java.util.HashMap;
import java.util.Map;

import com.fhbgds.dndgame.DnDGame;
import com.fhbgds.dndgame.io.TextLoader;

public class Scene {
	String name;
	HashMap<String, UIElement> elements;
	Map<String, String> loadedStrings;
	
	public Scene(String name){
		this.name = name;
		this.elements = new HashMap<String, UIElement>();
		this.loadedStrings = this.loadText(name);
	}
	
	public Map<String, UIElement> getElements(){
		return this.elements;
	}
	
	public void addElement(String name, UIElement e){
		this.elements.put(name, e);
		if(DnDGame.getGame().getUI().loadedScene() == this){
			DnDGame.getGame().getUI().loadScene(this);
		}
	}
	
	public UIElement getElement(String name){
		return this.elements.get(name);
	}
	
	public String getLocalizedString(String unlocalizedString){
		return loadedStrings.get(unlocalizedString);
	}
	
	private Map<String, String> loadText(String sceneName){
		try {
			 return TextLoader.loadText(sceneName, DnDGame.getGame().getLang().name().toLowerCase());
		} catch (Exception e) {
			System.err.println("Error loading language file");
			e.printStackTrace();
		}
		return null;
	}
}
