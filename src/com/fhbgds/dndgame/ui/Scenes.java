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
		scenes.add(generateMainMenuScene()); // 
	}
	
	public static Scene generateMainMenuScene(){
		double width = DnDGame.getGL().getWidth(), height = DnDGame.getGL().getHeight();
		menu = new Scene("mainMenu");
		TexturedRectangle tRect = new TexturedRectangle(0, 0, width, height, menu, new Resource("textures", "bg"));
		menu.addElement("bg", tRect);
	
		ProgressBar progressBar = new ProgressBar(100, (height/2)-10, width-100, (height/2)+10, menu);
		menu.addElement("progressBar1", progressBar);
		
		Button exitButton = new Button(width/2 - 144, (height*.75)-36, width/2 + 144, (height*.75) + 36, "exitText", menu);
		exitButton.setColor(0.5, 0.5, 0.5);
		exitButton.setHidden(true);
		exitButton.setClickBehavior(new Runnable(){
			public void run(){
				DnDGame.getGame().exit();
			}
		});
		exitButton.setTextTexture(new Resource("textures", "exitText"));
		menu.addElement("exitButton", exitButton);
		
		Button playButton = new Button(width/2 - 144, (height*.5)-36, width/2 + 144, (height*.5) + 36, "playText", menu);
		playButton.setColor(0.5, 0.5, 0.5);
		playButton.setHidden(true);
		playButton.setClickBehavior(new Runnable(){
			public void run(){
				
			}
		});
		playButton.setTextTexture(new Resource("textures", "playText"));
		menu.addElement("playButton", playButton);
		
		Rectangle centerline = new Rectangle(width/2-4, 0, width/2, height, menu).setColor(0, 1, 0);
		centerline.setHidden(true);
		menu.addElement("centerLine", centerline);
		
		Text text = new Text("loadingText", menu).setSize(2).setColor(0, 0, 0);
		double size = text.getDisplayWidth();
		text.setXY((width/2)-(size/2) + text.getScale(), (height/2) - 7);
		menu.addElement("loadingText", text);
		
//		Text text1 = new Text("logoText", menu).setSize(8).setColor(0, 0, 0);
//		size = text1.getDisplayWidth();
//		text1.setXY((width/2 - (size/2)) + text1.getScale(), (height/4) - 7);
//		menu.addElement("logoText", text1);
		
		TexturedRectangle logo = new TexturedRectangle(100, 100, width-100, 100 + height/3, menu, new Resource("textures", "logoText"));
		menu.addElement("logo", logo);
		
		return menu;
	}
}
