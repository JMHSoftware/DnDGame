package com.fhbgds.dndgame.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lwjgl.opengl.GL13;

import java.util.Map.Entry;

import com.fhbgds.dndgame.DnDGame;
import com.fhbgds.dndgame.io.TextLoader;
import com.fhbgds.dndgame.io.Textures;

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
	
	public void loadTextures(){
		Set<Entry<String, UIElement>> set = elements.entrySet();
		List<UIElement> list = new ArrayList<UIElement>();
		set.forEach(e -> list.add(e.getValue()));
		List<UIElement> texturedElements = new ArrayList<UIElement>();
		for(UIElement e: list){
			if(e.hasTexture){
				texturedElements.add(e);
			}
		}
		for(UIElement e : texturedElements){
			e.texID = Textures.decodePNGIntoTexture(e.texture.getName(), GL13.GL_TEXTURE0);
		}
	}
}
