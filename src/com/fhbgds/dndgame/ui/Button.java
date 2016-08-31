package com.fhbgds.dndgame.ui;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.fhbgds.dndgame.DnDGame;
import com.fhbgds.dndgame.io.Textures;
import com.fhbgds.dndgame.util.Resource;

public class Button extends Rectangle{
	
	Text text;
	Runnable onClick;
	boolean showText = true;
	int textTexID;
	int pressedTexID;
	boolean pressed = false;

	public Button(double x, double y, double endX, double endY, String text, Scene parent) {
		super(x, y, endX, endY, parent);
		this.text = new Text(text, parent).setSize(2);
		double size = this.text.getDisplayWidth();
		this.text.setXY(this.startX + (this.getWidth()/2 - (size/2)) + this.text.getScale()-1, this.startY + (this.getHeight()) - (9 * this.text.getScale()));
		this.text.setColor(0, 0, 0);
		this.clickable = true;
		this.texture = new Resource("textures", "button");
		this.pressedTexID = Textures.decodePNGIntoTexture("button_pressed");
		this.hasTexture = true;
	}
	
	public void setTextTexture(Resource texture){
		this.showText = false;
		this.textTexID = Textures.decodePNGIntoTexture(texture.getName());
	}
	
	public double getWidth(){
		return this.endX - this.startX;
	}
	
	public double getHeight(){
		return this.endY - this.startY;
	}
	
	public void setClickBehavior(Runnable r){
		this.onClick = r;
	}
	
	@Override
	public void draw(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.pressed? this.pressedTexID : this.texID);
		
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glPushMatrix();
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2d(startX, startY);
		
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2d(endX, startY);
		
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2d(endX, endY);
		
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2d(startX, endY);
		
		GL11.glPopMatrix();
		
		GL11.glEnd();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		if(this.showText){
			if(!this.text.initialized || this.text.lastLang != DnDGame.getGame().getLang()){
				this.text.init();
			}
			DnDGame.getGL().setDrawColor(this.text.r, this.text.g, this.text.b);
			GL11.glPushMatrix();
			
			GL11.glTranslated(this.text.startX, this.text.startY, 0f);
	
			GL11.glScaled(1 * this.text.size, 1 * this.text.size, 1);
			
			GL11.glDrawArrays(GL11.GL_QUADS, 0, this.text.quads * 4);
		}else{
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textTexID);
			
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glPushMatrix();
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2d(startX, startY);
			
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2d(endX, startY);
			
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2d(endX, endY);
			
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2d(startX, endY);
			
			GL11.glPopMatrix();
			
			GL11.glEnd();
			
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}

		GL11.glPopMatrix();
	}

	@Override
	public void click(int button, int action) {
		if(action == GLFW.GLFW_RELEASE){
			this.pressed = false;
		}else if(action == GLFW.GLFW_PRESS){
			this.pressed = true;
		}
		if(button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_RELEASE){
			this.onClick.run();
		}
	}
}
