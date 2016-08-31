package com.fhbgds.dndgame.ui;

import com.fhbgds.dndgame.DnDGame;

public class Scenes {
	public static Scene menu;

	private Scenes(){}
	
	public static void initScenes(){
		menu = new Scene("mainMenu");
			ProgressBar progressBar = new ProgressBar(100, (DnDGame.getGL().getHeight()/2)-10, DnDGame.getGL().getWidth()-100, (DnDGame.getGL().getHeight()/2)+10, menu);
			menu.addElement("progressBar1", progressBar);
			
			Rectangle centerline = new Rectangle(DnDGame.getGL().getWidth()/2-4, 0, DnDGame.getGL().getWidth()/2, DnDGame.getGL().getHeight(), menu).setColor(0, 1, 0);
			centerline.setHidden(true);
			menu.addElement("centerLine", centerline);
			
			Text text = new Text("string.loadingText", menu).setSize(2).setColor(0, 0, 0);
			double size = text.getDisplayWidth();
			text.setXY((DnDGame.getGL().getWidth()/2)-(size/2) + text.getScale(), (DnDGame.getGL().getHeight()/2) - 7);
			menu.addElement("text1", text);
			
			Text text1 = new Text("string.logoText", menu).setSize(8).setColor(1, 1, 1);
			size = text1.getDisplayWidth();
			text1.setXY((DnDGame.getGL().getWidth()/2 - (size/2)) + text1.getScale(), (DnDGame.getGL().getHeight()/4) - 7);
			menu.addElement("text2", text1);
		
	}
}
