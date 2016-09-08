package com.fhbgds.dndgame.ui;

import com.fhbgds.dndgame.TalesOfOld;

public class Sound extends UIElement {

	private String soundName = "";
	
	public Sound(Scene parent, String name) {
		super(0, 0, parent);
		this.soundName = name;
	}

	public void play(boolean loop){
		TalesOfOld.getAL().playSound(soundName, 1.0f, 1.0f, loop);
	}
	
	@Override
	public void draw() {
		return;
	}

	@Override
	public void click(int button, int action) {
		return;
	}

}
