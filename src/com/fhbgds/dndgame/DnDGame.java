package com.fhbgds.dndgame;

import java.io.File;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.fhbgds.dndgame.config.Configuration;
import com.fhbgds.dndgame.config.ConfigurationManager;
import com.fhbgds.dndgame.enums.EnumLang;
import com.fhbgds.dndgame.render.GLHelper;
import com.fhbgds.dndgame.ui.ProgressBar;
import com.fhbgds.dndgame.ui.Scenes;
import com.fhbgds.dndgame.ui.UI;

import javafx.beans.property.SimpleFloatProperty;

public class DnDGame {
	private static GLHelper gl;
	private static UI ui;
	private EnumLang lang;
	private static DnDGame instance;
	SimpleFloatProperty progress = new SimpleFloatProperty();
	public static DnDGame getGame(){
		return instance;
	}
	
	public EnumLang getLang(){
		return this.lang;
	}
	
	public UI getUI(){
		return ui;
	}

	public DnDGame(){
		init();
		loop();
	}
	
	private void init(){
		Configuration lang = new Configuration("lang").load();
		this.lang = EnumLang.getFromString(lang.getSettings().get("lang"));
		instance = this;
		Configuration video = ConfigurationManager.getVideoConfig();
		int width = Integer.valueOf(video.getSettings().get("width")), height = Integer.valueOf(video.getSettings().get("height"));
		gl = new GLHelper(width, height);
		gl.setTitle("DnDGame");
		gl.initGL(Boolean.valueOf(video.getSettings().get("fullscreen")));
		
		ui = new UI(null);
		
		gl.setupMouseCallbacks();
		gl.getReadyToDraw();
		gl.clearColor(0, 0, 0, 1);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		Scenes.initScenes();
		((ProgressBar) Scenes.menu.getElement("progressBar1")).progressProperty().bind(progress);
	}
	
	private void loop(){
		progress.set(0.1f);
		ui.loadScene(Scenes.menu);
		while(GLFW.glfwWindowShouldClose(gl.window()) == GL11.GL_FALSE){
			if(progress.get() > 1) progress.set(0);
			gl.clear();
			
			ui.drawElements();
			
			GLFW.glfwSwapBuffers(gl.window());
			GLFW.glfwPollEvents();
		}
	}
	
	public static void main(String[] args){
		System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
		new DnDGame();
	}
	
	public static GLHelper getGL(){
		return gl;
	}
}
