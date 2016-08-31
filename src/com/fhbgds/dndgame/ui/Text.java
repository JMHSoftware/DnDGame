package com.fhbgds.dndgame.ui;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBEasyFont;

import com.fhbgds.dndgame.DnDGame;
import com.fhbgds.dndgame.enums.EnumLang;

public class Text extends UIElement{
	
	boolean initialized = false;
	EnumLang lastLang;
	
	String unlocalizedText = "";
	String localizedText = "";
	int quads;
	double size = 1;

	public Text(String text, Scene parent) {
		super(0, 0, parent);
		this.unlocalizedText = text;
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
		this.localize();
		ByteBuffer charBuffer = BufferUtils.createByteBuffer(localizedText.length() * 270);
		quads = STBEasyFont.stb_easy_font_print(0, 0, localizedText, null, charBuffer);
		GL11.glVertexPointer(2, GL11.GL_FLOAT, 16, charBuffer);
	}

	public void localize(){
		this.localizedText = parentScene.getLocalizedString(unlocalizedText);
		if(this.localizedText == null || this.localizedText.isEmpty()) this.localizedText = this.unlocalizedText;
	}
	
	@Override
	public void draw(){
		if(!initialized || this.lastLang != DnDGame.getGame().getLang()){
			init();
		}
		DnDGame.getGL().setDrawColor(r, g, b);
		GL11.glPushMatrix();
		
		// Scroll
		GL11.glTranslated(this.startX, this.startY, 0f);

		// Zoom
		GL11.glScaled(1 * size, 1 * size, 1);
		
		GL11.glDrawArrays(GL11.GL_QUADS, 0, quads * 4);

		GL11.glPopMatrix();
	}
	
	public double getDisplayWidth(){
		if(!initialized || this.lastLang != DnDGame.getGame().getLang()){
			localize();
		}
		int length = this.localizedText.length();
		double width = (length * 6)*this.size;
		
		return width;
	}
	public double getDisplayHeight(){
		return 7 * this.size;
	}
	
	public double getScale(){
		return this.size;
	}

	@Override
	public void click(int button, int action) {
		
	}
}
