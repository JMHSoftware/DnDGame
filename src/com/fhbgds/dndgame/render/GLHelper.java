package com.fhbgds.dndgame.render;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class GLHelper {
	
	int sizeX, sizeY;
	long window;
	String title = "";
	
	public GLHelper(){
		this(800, 600);
	}
	
	public long window(){
		return window;
	}
	
	public GLHelper(int x, int y){
		this.sizeX = x;
		this.sizeY = y;
	}
	
	public void clear(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void initGL(){
		if(glfwInit() != GL11.GL_TRUE) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		long monitor = GLFW.glfwGetPrimaryMonitor();
		GLFWVidMode mode = glfwGetVideoMode(monitor);
		GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, mode.getRedBits());
		GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, mode.getGreenBits());
		GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, mode.getBlueBits());
//		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, mode.getRefreshRate());

		 GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
		 GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_FALSE);
		 GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, 144);

		window = glfwCreateWindow(sizeX, sizeY, title, NULL, NULL);
		if(window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}

		 //Get the resolution of the primary monitor
		 GLFWVidMode vidmode = glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		 GLFW.glfwSetWindowPos(
		 window,
		 (vidmode.getWidth() - sizeX) / 2,
		 (vidmode.getHeight() - sizeY) / 2
		 );

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);

		glfwShowWindow(window);
	}
	
	public void getReadyToDraw(){
		GL.createCapabilities();

		glMatrixMode(GL_PROJECTION);
		glEnable(GL_TEXTURE);
		glEnable(GL_TEXTURE_2D);
		glLoadIdentity();
		GL11.glViewport(0, 0, sizeX, sizeY);
		glOrtho(0, sizeX, sizeY, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		this.clearColor(0.1, 0.1, 0.1, 1);
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void clearColor(double r, double g, double b, double a){
		glClearColor((float)r, (float)g, (float)b, (float)a);
	}
	
}

