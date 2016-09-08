package com.fhbgds.dndgame;

import java.awt.Font;
import java.io.File;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.fhbgds.dndgame.config.Configuration;
import com.fhbgds.dndgame.config.ConfigurationManager;
import com.fhbgds.dndgame.enums.EnumLang;
import com.fhbgds.dndgame.enums.EnumResolutionOptions;
import com.fhbgds.dndgame.render.GLHelper;
import com.fhbgds.dndgame.ui.ProgressBar;
import com.fhbgds.dndgame.ui.Scenes;
import com.fhbgds.dndgame.ui.UI;
import com.fhbgds.dndgame.util.Util;

import javafx.beans.property.SimpleFloatProperty;

public class TalesOfOld {
	
	private static GLHelper gl;
	private static UI ui;
	private EnumLang lang;
	private static TalesOfOld instance;
	SimpleFloatProperty progress = new SimpleFloatProperty();
	boolean loading = true;
	boolean running = true;
	boolean isFullscreen;
	
	Cursor emptyCursor;
	Cursor nativeCursor;
	DisplayMode defaultMode;
	DisplayMode fullscreen = Display.getDesktopDisplayMode();
	
	private Font baseFont;
	
	public static TalesOfOld getGame(){
		return instance;
	}
	
	public EnumLang getLang(){
		return this.lang;
	}
	
	public UI getUI(){
		return ui;
	}

	public TalesOfOld(){
		init();
		loop();
	}
	
	private void init(){
		try{
			this.baseFont = Font.createFont(Font.TRUETYPE_FONT, new File("data/DINEngschrift-Regular.ttf"));
			
			Configuration lang = new Configuration("lang").load();
			this.lang = EnumLang.getFromString(lang.getSettings().get("lang"));
			instance = this;
			Configuration video = ConfigurationManager.getVideoConfig();
			this.isFullscreen = Boolean.valueOf(video.getSettings().get("fullscreen"));
			int width = Integer.valueOf(video.getSettings().get("width")), height = Integer.valueOf(video.getSettings().get("height"));
			
			defaultMode = new DisplayMode(width, height);
			
			gl = new GLHelper();
			if(!Boolean.valueOf(video.getSettings().get("fullscreen"))){
				Display.setDisplayMode(defaultMode);
			}else{
				Display.setDisplayMode(fullscreen);
				Display.setFullscreen(true);
			}
			Display.setTitle("Tales of Old");
			Display.create();
			Mouse.create();
			emptyCursor = new Cursor(1, 1, 0, 0, 1, BufferUtils.createIntBuffer(1), null);
			nativeCursor = Mouse.getNativeCursor();
			
			gl.setTitle("Tales of Old");
			gl.initGL(width, height);
			
			Scenes.initScenes();
			ui = new UI(Scenes.menu);
			
			ProgressBar bar = ((ProgressBar) Scenes.menu.getElement("progressBar1"));
			if(bar != null) bar.progressProperty().bind(progress);
			progress.set(0.1f);
			
			
			gl.clear();
			Display.update();
			
			progress.set(0.15f);
			gl.clear();
			Display.update();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void loop() {
		while(running){
			if(progress.get() > 0.99){
				progress.set(0);
				loading = false;
				Scenes.getScenes().get(0).getElement("exitButton").getDisabledProperty().set(false);
				Scenes.getScenes().get(0).getElement("progressBar1").getDisabledProperty().set(true);
				Scenes.getScenes().get(0).getElement("playButton").getDisabledProperty().set(false);
				Scenes.getScenes().get(0).getElement("videoSettingsButton").getDisabledProperty().set(false);
			}
			gl.clear();
			
			if(loading){
				progress.set(progress.get() + 0.001f);
			}
			
			ui.drawElements();
			this.handleKeyboardMouse();
			Display.update();
			if(Display.isCloseRequested()) running = false;
		}
	}
	
	public void handleKeyboardMouse(){
		while (Mouse.next()){
		    if (Mouse.getEventButtonState()) {
		    	ui.mouseX = Mouse.getEventX();
				ui.mouseY = gl.getHeight() - Mouse.getY();
				ui.processClick(Mouse.getEventButton(), 1);
		    }else{
		    	ui.mouseX = Mouse.getEventX();
				ui.mouseY = gl.getHeight() - Mouse.getY();
				ui.processClick(Mouse.getEventButton(), 0);
		    }
		}
	}
	
	public void setFullscreen(boolean bool){
		Configuration video = ConfigurationManager.getVideoConfig();
		Util.setDisplayMode(Integer.valueOf(video.getSettings().get("width")), Integer.valueOf(video.getSettings().get("height")), bool);
		video.getSettings().put("fullscreen", String.valueOf(bool));
		ConfigurationManager.saveSettings(video);
		this.isFullscreen = bool;
	}
	
	public void setResolution(EnumResolutionOptions opt){
		Configuration video = ConfigurationManager.getVideoConfig();
		boolean fullscreen = Boolean.valueOf(video.getSettings().get("fullscreen"));
		Util.setDisplayMode(opt.getWidth(), opt.getHeight(), fullscreen);
		gl.initGL(opt.getWidth(), opt.getHeight());
		Scenes.initScenes();
		Scenes.menu.getElement("exitButton").getDisabledProperty().set(false);
		Scenes.menu.getElement("progressBar1").getDisabledProperty().set(true);
		Scenes.menu.getElement("playButton").getDisabledProperty().set(false);
		Scenes.menu.getElement("videoSettingsButton").getDisabledProperty().set(false);
		video.getSettings().put("width", String.valueOf(opt.getWidth()));
		video.getSettings().put("height", String.valueOf(opt.getHeight()));
		ConfigurationManager.saveSettings(video);
	}
	
	public void exit(){
		this.running = false;
		Display.destroy();
		Mouse.destroy();
		System.exit(0);
	}
	
	public static void main(String[] args) throws Exception{
		System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
		new TalesOfOld();
	}
	
	public static GLHelper getGL(){
		return gl;
	}

	public Font getBaseFont() {
		return baseFont;
	}
	
	public boolean isFullscreen(){
		return this.isFullscreen;
	}
	
	public void setLang(EnumLang lang){
		this.lang = lang;
	}
}
