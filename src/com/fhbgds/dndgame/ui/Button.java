package com.fhbgds.dndgame.ui;

import java.util.ArrayList;
import java.util.List;

import com.fhbgds.dndgame.TalesOfOld;
import com.fhbgds.dndgame.render.GLHelper;
import com.fhbgds.dndgame.util.Resource;

public class Button extends Rectangle{
	
	Runnable onClick;
	boolean pressed = false;
	

	public Button(double x, double y, double endX, double endY, Scene parent) {
		super(x, y, endX, endY, parent);
		this.clickable = true;
		this.hasTexture = true;
		this.textureLoc = new Resource("textures", "button");
	}
	
	@Override
	public boolean hasAdditionalTextures(){
		return true;
	}
	
	public List<Resource> getAdditionalTextureLocations(){
		List<Resource> list = new ArrayList<Resource>();
		list.add(new Resource("textures", "button_pressed"));
		return list;
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
		GLHelper.drawTexturedRectangle(this.startX, this.startY, this.endX, this.endY, this.pressed? this.additionalTextures.get("button_pressed") : this.texture);
	}

	@Override
	public void click(int button, int action) {
		if(action == 0){
			this.pressed = false;
		}else if (action == 1){
			this.pressed = true;
		}
		if(action == 1){
			if(this.onClick != null){
				TalesOfOld.getAL().playSound("click", 1f, 1f, false);
				this.onClick.run();
				this.pressed = false;
			}
		}
	}
}
