package com.fhbgds.dndgame;

import java.security.SecureRandom;
import java.util.HashMap;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.fhbgds.dndgame.config.Configuration;
import com.fhbgds.dndgame.config.ConfigurationManager;
import com.fhbgds.dndgame.render.GLHelper;
import com.fhbgds.dndgame.ui.ProgressBar;
import com.fhbgds.dndgame.ui.Text;
import com.fhbgds.dndgame.ui.UI;
import com.fhbgds.dndgame.ui.UIElement;

public class DnDGame {
	private static GLHelper gl;
	private static UI ui;
	private SecureRandom rand = new SecureRandom();

	public DnDGame(){
		init();
		loop();
	}
	
	private void init(){
		Configuration video = ConfigurationManager.getVideoConfig();
		int width = Integer.valueOf(video.getSettings().get("width")), height = Integer.valueOf(video.getSettings().get("height"));
		gl = new GLHelper(width, height);
		gl.setTitle("DnDGame");
		gl.initGL(Boolean.valueOf(video.getSettings().get("fullscreen")));
		HashMap<String, UIElement> map = new HashMap<String, UIElement>();
		map.put("progressBar1", new ProgressBar(100, 350, 1180, 370));
		map.put("text1", new Text((gl.getWidth()/2)-80, 351, "Now Loading...").setSize(2).setColor(0, 0, 0));
		ui = new UI(map);
		
	}
	
	private void loop(){
		float temp = 0;
		gl.getReadyToDraw();
		gl.clearColor(0, 0, 0, 1);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		while(GLFW.glfwWindowShouldClose(gl.window()) == GL11.GL_FALSE){
			temp += 0.001;
			if(temp > 1) temp = 0;
			gl.clear();
			
			if(rand.nextFloat() > 0.9){
				((ProgressBar) ui.getElement("progressBar1")).setProgress(temp);
			}
			ui.drawElements();
			
			GLFW.glfwSwapBuffers(gl.window());
			GLFW.glfwPollEvents();
		}
	}
	
	public static void main(String[] args){
		new DnDGame();
	}
	
	public static GLHelper getGL(){
		return gl;
	}
}
