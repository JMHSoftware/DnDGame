package com.fhbgds.dndgame.ui;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBEasyFont;

import com.fhbgds.dndgame.DnDGame;

public class Text extends UIElement{
	
	boolean initialized = false;
	String text;
	int quads;
	double size = 1;

	public Text(double x, double y, String text) {
		super(x, y);
		this.text = text;
	}
	
	public Text setSize(double size){
		this.size = size;
		return this;
	}
	
	public Text setColor(double r, double g, double b){
		this.r = r;
		this.g = g;
		this.b = b;
		
		return this;
	}
	
	void init(){
		ByteBuffer charBuffer = BufferUtils.createByteBuffer(text.length() * 270);
		quads = STBEasyFont.stb_easy_font_print(0, 0, text, null, charBuffer);
		GL11.glVertexPointer(2, GL11.GL_FLOAT, 16, charBuffer);
	}

	@Override
	public void draw(){
		if(!initialized){
			init();
		}
		DnDGame.getGL().setDrawColor(r, g, b);
		GL11.glPushMatrix();
		// Zoom
		GL11.glScaled(1 * size, 1 * size, 1);
		// Scroll
		GL11.glTranslated(this.startX * (1/size), this.startY * (1/size), 0f);

		GL11.glDrawArrays(GL11.GL_QUADS, 0, quads * 4);

		GL11.glPopMatrix();
	}
}
