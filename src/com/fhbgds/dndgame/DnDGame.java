package com.fhbgds.dndgame;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.fhbgds.dndgame.config.Configuration;
import com.fhbgds.dndgame.config.ConfigurationManager;
import com.fhbgds.dndgame.render.GLHelper;

public class DnDGame {
	private GLHelper gl;

	public DnDGame(){
		init();
		loop();
	}
	
	private void init(){
		Configuration video = ConfigurationManager.getVideoConfig();
		int width = Integer.valueOf(video.getSettings().get("width")), height = Integer.valueOf(video.getSettings().get("height"));
		gl = new GLHelper(width, height);
		gl.setTitle("DnDGame");
		gl.initGL();
	}
	
	private void loop(){
		gl.getReadyToDraw();
		gl.clearColor(0, 0, 0, 1);
		while(GLFW.glfwWindowShouldClose(gl.window()) == GL11.GL_FALSE){
			gl.clear();
			
			
			
			GLFW.glfwSwapBuffers(gl.window());
			GLFW.glfwPollEvents();
		}
	}
	
	public static void main(String[] args){
		new DnDGame();
	}
}
