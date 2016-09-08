package com.fhbgds.dndgame.ui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.ConfigurableEffect;

import com.fhbgds.dndgame.TalesOfOld;
import com.fhbgds.dndgame.enums.EnumLang;
import com.fhbgds.dndgame.util.FontHandling;

public class Text extends UIElement{
	
	private double originalStartX;
	double defaultSize = 24;
	double currentSize = 24;
	Font baseFont;
	UnicodeFont drawFont;
	Runnable clickAction = null;
	boolean centered = false;
	Color textColor = Color.WHITE;
	List<ConfigurableEffect> effects = new ArrayList<ConfigurableEffect>();
	long timeOfLastLocalization;
	
	EnumLang currentLang = EnumLang.EN;
	boolean localized = false;
	boolean localizable = true;
	
	String unlocalizedText = "-";
	String localizedText = "-";

	public Text(double x, double y, Scene parent, Font font, String unlocText) {
		super(x, y, parent);
		this.originalStartX = x;
		this.unlocalizedText = unlocText;
		this.baseFont = font;
		this.drawFont = FontHandling.getUnicodeFont(this.baseFont, this.defaultSize);
		this.clickable = true;
		this.currentLang = TalesOfOld.getGame().getLang();
	}
	
	public void localize(){
		if(this.localizable){
			this.timeOfLastLocalization = this.parentScene.lastTimeOfLangUpdate;
			this.localizedText = this.parentScene.getLocalizedString(unlocalizedText);
		}else{
			this.localizedText = this.unlocalizedText;
		}
		this.localized = true;
	}
	
	public Text setLocalizable(boolean bool){
		this.localizable = bool;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	private void rebuildDrawFont(){
		this.drawFont.getEffects().clear();
		this.drawFont = new UnicodeFont(this.baseFont.deriveFont(0, (float) this.currentSize));
		this.drawFont.addAsciiGlyphs();
		this.drawFont.getEffects().add(new ColorEffect(this.textColor));
		this.drawFont.getEffects().addAll(effects);
		try {
			this.drawFont.loadGlyphs();
		} catch (SlickException e1) {
		    e1.printStackTrace();
		}
		this.drawFont.getEffects().addAll(effects);
	}
	
	public Text setTextColor(Color c){
		this.textColor = c;
		this.rebuildDrawFont();
		return this;
	}
	
	public Text setClickAction(Runnable r){
		this.clickAction = r;
		return this;
	}
	
	public Text setCenterAlign(boolean align){
		this.centered = align;
		this.reAlignText();
		return this;
	}
	
	public void reAlignText(){
		if(!this.localized || this.currentLang != TalesOfOld.getGame().getLang() || this.timeOfLastLocalization != this.parentScene.lastTimeOfLangUpdate){
			this.localize();
		}
		if(this.centered){
			this.startX = this.originalStartX - this.drawFont.getWidth(this.localizedText)/2;
		}else{
			this.startX = this.originalStartX;
		}
	}
	
	public Text addEffect(ConfigurableEffect e){
		this.effects.add(e);
		this.rebuildDrawFont();
		return this;
	}
	
	public Text setFontSize(double size){
		this.currentSize = size;
		this.rebuildDrawFont();
		this.reAlignText();
		return this;
	}
	
	public Text setUnlocalizedText(String desiredText){
		this.unlocalizedText = desiredText;
		this.reAlignText();
		return this;
	}
	
	@Override
	public void draw() {
		if(!this.localized || this.currentLang != TalesOfOld.getGame().getLang() || this.timeOfLastLocalization != this.parentScene.lastTimeOfLangUpdate){
			this.localize();
		}
		
		if(!this.localizable){
			this.localizedText = this.unlocalizedText;
			this.reAlignText();
		}
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		this.drawFont.drawString((float)this.startX, (float)this.startY, this.localizedText);
		
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public void click(int button, int action) {
		if(this.clickAction != null){
			this.clickAction.run();
		}
	}

}
