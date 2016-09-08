package com.fhbgds.dndgame.ui;

import org.lwjgl.opengl.GL11;

import com.fhbgds.dndgame.TalesOfOld;

import javafx.beans.property.SimpleFloatProperty;

public class ProgressBar extends UIElement {

	float progress = 0;
	SimpleFloatProperty prog = new SimpleFloatProperty();
	int padding = 2;
	
	double fullLength;
	
	public ProgressBar(double x, double y, double endX, double endY, Scene parent){
		super(x, y, parent);
		this.endX = endX;
		this.endY = endY;
		
		this.fullLength = (this.endX - padding) - this.startX;
	}
	
	public SimpleFloatProperty progressProperty(){
		return this.prog;
	}
	
	@Override
	public void draw(){
		this.progress = prog.get();
		TalesOfOld.getGL().setDrawColor(0.5, 0.5, 0.5);
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glPushMatrix();
		GL11.glColor3d(0.4, 0.4, 0.4);
		GL11.glVertex2d(startX, startY);
		GL11.glColor3d(0.4, 0.4, 0.4);
		GL11.glVertex2d(endX, startY);
		GL11.glColor3d(0.6, 0.6, 0.6);
		GL11.glVertex2d(endX, endY);
		GL11.glColor3d(0.6, 0.6, 0.6);
		GL11.glVertex2d(startX, endY);
		GL11.glPopMatrix();
		GL11.glEnd();
		
		TalesOfOld.getGL().setDrawColor(0.5, 1, 0.5);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glPushMatrix();
		GL11.glColor3d(0.7, 1, 0.7);
		GL11.glVertex2d(startX + padding, startY + padding);
		GL11.glColor3d(0.7, 1, 0.7);
		GL11.glVertex2d(startX + (fullLength * progress) + padding, startY + padding);
		GL11.glColor3d(0.4, 1, 0.4);
		GL11.glVertex2d(startX + (fullLength * progress) + padding, endY - padding);
		GL11.glColor3d(0.4, 1, 0.4);
		GL11.glVertex2d(startX + padding, endY - padding);
		GL11.glPopMatrix();
		GL11.glEnd();
	}

	@Override
	public void click(int button) {
	}

	@Override
	public void click(int button, int action) {
	}
	
}
