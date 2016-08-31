package com.fhbgds.dndgame.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class UI {
	
	Scene loadedScene;
	Map<String, String> loadedStrings;
	LinkedHashMap<String, UIElement> loadedElements = new LinkedHashMap<String, UIElement>();
	
	public volatile double mouseX, mouseY;
	
	public UI(Scene s){
		this.loadScene(s);
	}
	
	public void loadScene(Scene s){
		if(s != null){
			loadedElements = new LinkedHashMap<String, UIElement>();
			this.loadedElements.putAll(s.getElements());
			this.loadedStrings = s.loadedStrings;
			this.loadedScene = s;
		}
	}
	
	public Scene loadedScene(){
		return this.loadedScene;
	}
	
	public void drawElements(){
		for(String key : loadedElements.keySet()){
			UIElement e = loadedElements.get(key);
			if(!e.hidden){
				e.draw();
			}
		}
	}
	
	
	public void processClick(int button, int action){
		Set<Entry<String, UIElement>> set = loadedElements.entrySet();
		List<UIElement> list = new ArrayList<UIElement>();
		set.forEach(e -> list.add(e.getValue()));
		List<UIElement> clickables = new ArrayList<UIElement>();
		for(UIElement e: list){
			if(e.clickable){
				clickables.add(e);
			}
		}
		for(UIElement e : clickables){
			double x = e.startX;
			double x1 = e.endX;
			double y = e.startY;
			double y1 = e.endY;
			
			boolean inside = true;
			
			if(mouseX < x || mouseX > x1) inside = false;
			if(mouseY < y || mouseY > y1) inside = false;
			if(inside){
				if(!e.hidden) e.click(button, action);
			}
		}
	}
}
