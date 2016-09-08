package com.fhbgds.dndgame.ui;

import org.lwjgl.opengl.GL11;

import com.fhbgds.dndgame.TalesOfOld;

public class Rectangle extends UIElement{

	double r, g, b;
	
	public Rectangle(double x, double y, double endX, double endY, Scene parent) {
		super(x, y, parent);
		this.endX = endX;
		this.endY = endY;
		this.setColor(0.5, 0.5, 0.5);
	}
	
	public Rectangle setColor(double r, double g, double b){
		this.r = r;
		this.g = g;
		this.b = b;
		
		return this;
	}
	
	@Override
	public void draw(){
		TalesOfOld.getGL().setDrawColor(0.5, 0.5, 0.5);
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glPushMatrix();
		GL11.glColor3d(r-.1, g-.1, b-.1);
		GL11.glVertex2d(startX, startY);
		GL11.glColor3d(r-.1, g-.1, b-.1);
		GL11.glVertex2d(endX, startY);
		GL11.glColor3d(r+.1, g+.1, b+.1);
		GL11.glVertex2d(endX, endY);
		GL11.glColor3d(r+.1, g+.1, b+.1);
		GL11.glVertex2d(startX, endY);
		GL11.glPopMatrix();
		
		GL11.glEnd();
	}

	@Override
	public void click(int button) {
		super.click(button);
	}

	@Override
	public void click(int button, int action) {
		
	}

}
