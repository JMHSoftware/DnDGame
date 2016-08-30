package com.fhbgds.dndgame.ui;

import java.util.HashMap;
import java.util.Map.Entry;

public class UI {
	
	HashMap<String, UIElement> elements = new HashMap<String, UIElement>();
	
	public UI(HashMap<String, UIElement> initialEntries){
		this.elements.putAll(initialEntries);
	}
	
	public void drawElements(){
		for(Entry<String, UIElement> e : elements.entrySet()){
			e.getValue().draw();
		}
	}
	
	public UIElement getElement(String name){
		return this.elements.get(name);
	}
}
