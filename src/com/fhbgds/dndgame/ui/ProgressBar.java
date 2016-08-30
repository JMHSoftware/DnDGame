package com.fhbgds.dndgame.ui;

import org.lwjgl.opengl.GL11;

import com.fhbgds.dndgame.DnDGame;

public class ProgressBar extends UIElement {

	float progress = 0;
	int padding = 2;
	
	double endX, endY;
	double fullLength;
	
	public ProgressBar(double x, double y, double endX, double endY){
		super(x, y);
		this.endX = endX;
		this.endY = endY;
		
		this.fullLength = (this.endX - padding) - this.startX;
	}
	
	/**
	 * @param progress The precentage to set the bar to. Must be a value between 0 and 1.
	 */
	public void setProgress(float progress){
		if(progress < 0) progress = 0;
		if(progress > 1) progress = 1;
		this.progress = progress;
	}
	
	@Override
	public void draw(){
		DnDGame.getGL().setDrawColor(0.5, 0.5, 0.5);
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
		
		DnDGame.getGL().setDrawColor(0.5, 1, 0.5);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glPushMatrix();
		GL11.glColor3d(0.7, 1, 0.7);
		GL11.glVertex2d(startX + padding, startY + padding);
		GL11.glColor3d(0.7, 1, 0.7);
		GL11.glVertex2d(startX + (fullLength * progress), startY + padding);
		GL11.glColor3d(0.4, 1, 0.4);
		GL11.glVertex2d(startX + (fullLength * progress), endY - padding);
		GL11.glColor3d(0.4, 1, 0.4);
		GL11.glVertex2d(startX + padding, endY - padding);
		GL11.glPopMatrix();
		GL11.glEnd();
	}
	
}
