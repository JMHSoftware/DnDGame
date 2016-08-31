package com.fhbgds.dndgame.ui;

import org.lwjgl.opengl.GL11;

import com.fhbgds.dndgame.util.Resource;

public class TexturedRectangle extends Rectangle {
	

	public TexturedRectangle(double x, double y, double endX, double endY, Scene parent, Resource texture) {
		super(x, y, endX, endY, parent);
		this.texture = texture;
		this.hasTexture = true;
	}
	
	@Override
	public void draw(){
		GL11.glBegin(GL11.GL_QUADS);
		
//		GL11.glEnable(GL11.GL_BLEND);
//		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texID);
		
		GL11.glColor3d(1, 1, 1);
		
		GL11.glPushMatrix();
		
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2d(startX, startY);
		
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2d(endX, startY);
		
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2d(endX, endY);
		
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2d(startX, endY);
		
		GL11.glPopMatrix();
		
//		GL11.glDisable(GL11.GL_BLEND);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		GL11.glEnd();
	}

}
