package com.fhbgds.dndgame.ui;

import com.fhbgds.dndgame.util.Resource;

public abstract class UIElement {

	double startX, startY, endX = 0, endY = 0;
	double r = 1, g = 1, b = 1;
	boolean clickable = false;
	boolean hasTexture = false;
	Scene parentScene;
	Resource texture;
	int texID = -1;
	
	boolean hidden = false;
	
	public UIElement(double x, double y, Scene parent){
		this.startX = x;
		this.startY = y;
		this.parentScene = parent;
	}
	
	public void setXY(double x, double y){
		this.startX = x;
		this.startY = y;
	}
	
	public void setHidden(boolean hidden){
		this.hidden = hidden;
	}
	
	public abstract void draw();
	
	public abstract void click();
}
