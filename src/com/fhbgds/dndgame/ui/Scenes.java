package com.fhbgds.dndgame.ui;

import java.util.ArrayList;
import java.util.List;

import com.fhbgds.dndgame.DnDGame;
import com.fhbgds.dndgame.util.Resource;

public class Scenes {
	static List<Scene> scenes = new ArrayList<Scene>();
	
	public static Scene menu;

	private Scenes(){}
	
	public static void initScenes(){
		menu = new Scene("mainMenu");
			TexturedRectangle tRect = new TexturedRectangle(0, 0, DnDGame.getGL().getWidth(), DnDGame.getGL().getHeight(), menu, new Resource("textures", "bg"));
			menu.addElement("bg", tRect);
		
			ProgressBar progressBar = new ProgressBar(100, (DnDGame.getGL().getHeight()/2)-10, DnDGame.getGL().getWidth()-100, (DnDGame.getGL().getHeight()/2)+10, menu);
			menu.addElement("progressBar1", progressBar);
			
			Rectangle centerline = new Rectangle(DnDGame.getGL().getWidth()/2-4, 0, DnDGame.getGL().getWidth()/2, DnDGame.getGL().getHeight(), menu).setColor(0, 1, 0);
			centerline.setHidden(true);
			menu.addElement("centerLine", centerline);
			
			Text text = new Text("loadingText", menu).setSize(2).setColor(0, 0, 0);
			double size = text.getDisplayWidth();
			text.setXY((DnDGame.getGL().getWidth()/2)-(size/2) + text.getScale(), (DnDGame.getGL().getHeight()/2) - 7);
			menu.addElement("text1", text);
			
			Text text1 = new Text("logoText", menu).setSize(8).setColor(0, 0, 0);
			size = text1.getDisplayWidth();
			text1.setXY((DnDGame.getGL().getWidth()/2 - (size/2)) + text1.getScale(), (DnDGame.getGL().getHeight()/4) - 7);
			menu.addElement("text2", text1);
		scenes.add(menu);
	}
}
