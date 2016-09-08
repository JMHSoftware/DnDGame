package com.fhbgds.dndgame.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.newdawn.slick.opengl.Texture;

import com.fhbgds.dndgame.util.Resource;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class UIElement {

	double startX, startY, endX = 0, endY = 0;
	double r = 1, g = 1, b = 1;
	boolean clickable = false;
	boolean hasTexture = false;
	Scene parentScene;
	Resource textureLoc;
	Texture texture;
	Map<String, Texture> additionalTextures = new HashMap<String, Texture>();
	UUID id;
	SimpleBooleanProperty disabled = new SimpleBooleanProperty();
	
	public UIElement(double x, double y, Scene parent){
		this.startX = x;
		this.startY = y;
		this.parentScene = parent;
		this.id = UUID.randomUUID();
	}
	
	public void setStartXY(double x, double y){
		this.startX = x;
		this.startY = y;
	}
	
	public void setEndXY(double x, double y){
		this.endX = x;
		this.endY = y;
	}
	
	public SimpleBooleanProperty getDisabledProperty(){
		return this.disabled;
	}
	
	public ReadOnlyBooleanProperty getDisabled(){
		return SimpleBooleanProperty.readOnlyBooleanProperty(disabled);
	}
	
	public boolean hasAdditionalTextures(){
		return false;
	}
	
	public List<Resource> getAdditionalTextureLocations(){
		return null;
	}
	
	public void addTexture(String id, Texture tex){
		this.additionalTextures.put(id, tex);
	}
	
	public abstract void draw();

	public void click(int button){
		this.click(button, -1);
	}
	
	public abstract void click(int button, int action);
	
	@Override
	public boolean equals(Object o){
		if(o instanceof UIElement){
			UIElement e = (UIElement) o;
			return e.id.equals(this.id);
		}else{
			return false;
		}
	}
	
	@Override
	public String toString(){
		return String.format("[%s ID:%s]", this.getClass().getSimpleName(), this.id.toString());
	}
}
