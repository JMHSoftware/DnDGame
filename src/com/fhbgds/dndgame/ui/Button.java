package com.fhbgds.dndgame.ui;

import org.lwjgl.opengl.GL11;

import com.fhbgds.dndgame.DnDGame;

public class Button extends Rectangle{
	
	Text text;
	Runnable onClick;

	public Button(double x, double y, double endX, double endY, String text, Scene parent) {
		super(x, y, endX, endY, parent);
		this.text = new Text(text, parent).setSize(2);
		double size = this.text.getDisplayWidth();
		this.text.setXY(this.startX + (this.getWidth()/2 - (size/2)) + this.text.getScale()-1, this.startY + (this.getHeight()) - (9 * this.text.getScale()));
		this.text.setColor(0, 0, 0);
		this.clickable = true;
	}
	
	public double getWidth(){
		return this.endX - this.startX;
	}
	
	public double getHeight(){
		return this.endY - this.startY;
	}
	
	public void setClickBehavior(Runnable r){
		this.onClick = r;
	}
	
	@Override
	public void draw(){
		DnDGame.getGL().setDrawColor(0.5, 0.5, 0.5);
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
		
		if(!this.text.initialized || this.text.lastLang != DnDGame.getGame().getLang()){
			this.text.init();
		}
		DnDGame.getGL().setDrawColor(this.text.r, this.text.g, this.text.b);
		GL11.glPushMatrix();
		
		GL11.glTranslated(this.text.startX, this.text.startY, 0f);

		GL11.glScaled(1 * this.text.size, 1 * this.text.size, 1);
		
		GL11.glDrawArrays(GL11.GL_QUADS, 0, this.text.quads * 4);

		GL11.glPopMatrix();
	}

	@Override
	public void click() {
		this.onClick.run();
	}
}
