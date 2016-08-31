package com.fhbgds.dndgame.ui;

public abstract class UIElement {

	double startX, startY, endX = 0, endY = 0;
	double r = 1, g = 1, b = 1;
	boolean clickable = false;
	Scene parentScene;
	
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
