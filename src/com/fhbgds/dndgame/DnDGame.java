package com.fhbgds.dndgame;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.fhbgds.dndgame.render.GLHelper;

public class DnDGame {
	private GLHelper gl;

	public DnDGame(){
		init();
		loop();
	}
	
	private void init(){
		gl = new GLHelper(800, 600);
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
