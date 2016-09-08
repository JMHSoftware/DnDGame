package com.fhbgds.dndgame.ui;

import com.fhbgds.dndgame.render.GLHelper;
import com.fhbgds.dndgame.util.Resource;

public class TexturedRectangle extends Rectangle {
	

	public TexturedRectangle(double x, double y, double endX, double endY, Scene parent, Resource texture) {
		super(x, y, endX, endY, parent);
		this.textureLoc = texture;
		this.hasTexture = true;
	}
	
	@Override
	public void draw(){
		GLHelper.drawTexturedRectangle(this.startX, this.startY, this.endX, this.endY, this.texture);
	}

}
