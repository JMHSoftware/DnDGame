package com.fhbgds.dndgame.render;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class GLHelper {

	int sizeX, sizeY;
	private static GLHelper instance;
	public int getWidth() {
		return sizeX;
	}

	public int getHeight() {
		return sizeY;
	}

	public GLHelper() {
		instance = this;
	}

	public void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void initGL(int WIDTH, int HEIGHT) {
		this.sizeX = WIDTH;
		this.sizeY = HEIGHT;
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glEnable(GL11.GL_TEXTURE);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glLoadIdentity();
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		GL11.glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
	}

	public void setTitle(String title) {
		Display.setTitle(title);
	}

	public void clearColor(double r, double g, double b, double a) {
		GL11.glClearColor((float) r, (float) g, (float) b, (float) a);
	}

	public void setDrawColor(double r, double g, double b) {
		GL11.glColor3d(r, g, b);
	}
	
	public static void drawTexturedRectangle(double startX, double startY, double endX, double endY, Texture texture){
		instance.setDrawColor(1,1,1);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2d(startX, startY);
		
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2d(endX, startY);
		
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2d(endX, endY);
		
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2d(startX, endY);
		
		
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
}
