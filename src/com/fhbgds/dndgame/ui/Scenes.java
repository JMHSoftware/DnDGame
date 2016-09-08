package com.fhbgds.dndgame.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.newdawn.slick.font.effects.OutlineEffect;

import com.fhbgds.dndgame.TalesOfOld;
import com.fhbgds.dndgame.config.Configuration;
import com.fhbgds.dndgame.config.ConfigurationManager;
import com.fhbgds.dndgame.enums.EnumLang;
import com.fhbgds.dndgame.enums.EnumResolutionOptions;
import com.fhbgds.dndgame.util.Resource;

public class Scenes {
	static List<Scene> scenes = new ArrayList<Scene>();
	
	public static Scene menu;
	public static Scene settings;
	public static Scene testScene;

	private Scenes(){}
	
	public static void initScenes(){
		scenes.add(generateMainMenuScene()); // 
		scenes.add(testScene());
		scenes.add(generateSettingsScene());
	}
	
	public static Scene generateSettingsScene(){
		double width = TalesOfOld.getGL().getWidth(), height = TalesOfOld.getGL().getHeight();
		settings = new Scene("settings");
		
		TexturedRectangle tRect = new TexturedRectangle(0, 0, width, height, settings, new Resource("textures", "bg"));
		settings.addElement("bg", tRect);
		
		Button backButton = new Button(width/2 - (100 * TalesOfOld.getGL().getWidth()/800), height - (80 * TalesOfOld.getGL().getHeight()/600), width/2 + (100 * TalesOfOld.getGL().getWidth()/800), height - 4, settings);
		backButton.setClickBehavior(new Runnable(){
			public void run(){
				Text text = ((Text) settings.getElement("resolutionText"));
				String string = text.unlocalizedText;
				EnumResolutionOptions option = EnumResolutionOptions.getFromString(string);
				TalesOfOld.getGame().setResolution(option);
				TalesOfOld.getGame().getUI().loadScene(menu);
			}
		});
		settings.addElement("backButton", backButton);
		
		Text backText = new Text(width/2, backButton.startY + ((backButton.endY - backButton.startY)/8), settings, TalesOfOld.getGame().getBaseFont(), "backButtonText").setFontSize(32*(TalesOfOld.getGL().getHeight()/300)).setCenterAlign(true).setClickAction(backButton.onClick);
		backText.getDisabledProperty().bind(backButton.getDisabledProperty());
		backText.addEffect(new OutlineEffect(1, Color.BLACK));
		settings.addElement("backText", backText);
		
		Button resolutionButton = new Button(width/2 - (100 * TalesOfOld.getGL().getWidth()/800), (height*.5) - (36 * TalesOfOld.getGL().getHeight()/600), width/2 + (100 * TalesOfOld.getGL().getWidth()/800), (height*.5) + (36 * TalesOfOld.getGL().getHeight()/600), settings);
		resolutionButton.setClickBehavior(new Runnable(){
			public void run(){
				Text text = ((Text) settings.getElement("resolutionText"));
				String oldString = text.unlocalizedText;
				EnumResolutionOptions option = EnumResolutionOptions.getFromString(oldString);
				if(option == null) option = EnumResolutionOptions.values()[0];
				EnumResolutionOptions newOption = option.getNext();
				text.setUnlocalizedText(newOption.getString());
				text.reAlignText();
			}
		});
		settings.addElement("resolutionButton", resolutionButton);
		
		Text resolutionText = new Text(width/2, resolutionButton.startY + ((resolutionButton.endY - resolutionButton.startY)/8), settings, TalesOfOld.getGame().getBaseFont(), (int)width + " x " + (int)height).setLocalizable(false).setFontSize(32*(TalesOfOld.getGL().getHeight()/300)).setCenterAlign(true).setClickAction(resolutionButton.onClick);
		resolutionText.getDisabledProperty().bind(resolutionButton.getDisabledProperty());
		resolutionText.addEffect(new OutlineEffect(1, Color.BLACK));
		settings.addElement("resolutionText", resolutionText);
		
		Button fullscreenButton = new Button(width/2 - (100 * TalesOfOld.getGL().getWidth()/800), resolutionButton.endY + 4, width/2 + (100 * TalesOfOld.getGL().getWidth()/800), resolutionButton.endY + (72 * TalesOfOld.getGL().getHeight()/600), settings);
		fullscreenButton.setClickBehavior(new Runnable(){
			public void run(){
				boolean full = TalesOfOld.getGame().isFullscreen();
				TalesOfOld.getGame().setFullscreen(!full);
				((Text) settings.getElement("fullscreenText")).setUnlocalizedText(TalesOfOld.getGame().isFullscreen() ? "Fullscreen" : "Windowed");
			}
		});
		settings.addElement("fullscreenButton", fullscreenButton);
		
		Button langButton = new Button(width/2 - (100 * TalesOfOld.getGL().getWidth()/800), fullscreenButton.endY + 4, width/2 + (100 * TalesOfOld.getGL().getWidth()/800), fullscreenButton.endY + (72 * TalesOfOld.getGL().getHeight()/600), settings);
		langButton.setClickBehavior(new Runnable(){
			public void run(){
				Text text = ((Text) settings.getElement("langText"));
				String oldString = text.unlocalizedText;
				EnumLang lang = EnumLang.getFromString(oldString);
				int valuesMax = EnumLang.values().length - 1;
				int index = TalesOfOld.getGame().getLang().ordinal();
				if(lang == null) lang = EnumLang.values()[0];
				if(index >= valuesMax) index = -1;
				EnumLang newOption = EnumLang.values()[index + 1];
				text.setUnlocalizedText(newOption.getString());
				text.reAlignText();
				TalesOfOld.getGame().setLang(newOption);
				Configuration config = new Configuration("lang").load();
				config.getSettings().put("lang", newOption.name().toLowerCase());
				ConfigurationManager.saveSettings(config);
				TalesOfOld.getGame().getUI().loadedScene().loadedStrings = TalesOfOld.getGame().getUI().loadedScene().loadText();
				Set<Entry<String, UIElement>> set = settings.elements.entrySet();
				for (Entry<String, UIElement> e1 : set) {
					if (e1.getValue() instanceof Text){
						Text t = (Text) e1.getValue();
						t.reAlignText();
					}
				}
			}
		});
		settings.addElement("langButton", langButton);
		
		Text langText = new Text(width/2, langButton.startY + ((langButton.endY - langButton.startY)/8), settings, TalesOfOld.getGame().getBaseFont(), "langText").setLocalizable(false).setFontSize(32*(TalesOfOld.getGL().getHeight()/300)).setCenterAlign(true).setClickAction(fullscreenButton.onClick);
		langText.getDisabledProperty().bind(langButton.getDisabledProperty());
		langText.addEffect(new OutlineEffect(1, Color.BLACK));
		langText.setUnlocalizedText(TalesOfOld.getGame().getLang().getString());
		settings.addElement("langText", langText);
		
		Text fullscreenText = new Text(width/2, fullscreenButton.startY + ((fullscreenButton.endY - fullscreenButton.startY)/8), settings, TalesOfOld.getGame().getBaseFont(), "fullscreenButtonText").setLocalizable(false).setFontSize(32*(TalesOfOld.getGL().getHeight()/300)).setCenterAlign(true).setClickAction(fullscreenButton.onClick);
		fullscreenText.getDisabledProperty().bind(fullscreenButton.getDisabledProperty());
		fullscreenText.addEffect(new OutlineEffect(1, Color.BLACK));
		fullscreenText.setUnlocalizedText(TalesOfOld.getGame().isFullscreen() ? "Fullscreen" : "Windowed");
		settings.addElement("fullscreenText", fullscreenText);
		
		Text settingsText = new Text(width/2, 10 + TalesOfOld.getGL().getHeight() / 10, settings, TalesOfOld.getGame().getBaseFont(), "settingsTitleText").setFontSize(TalesOfOld.getGL().getWidth()/10).setTextColor(Color.WHITE).setCenterAlign(true);
		settingsText.addEffect(new OutlineEffect(1, Color.BLACK));
		settings.addElement("settingsTitleText", settingsText);
		
		return settings;
	}
	
	private static Scene generateMainMenuScene(){
		double width = TalesOfOld.getGL().getWidth(), height = TalesOfOld.getGL().getHeight();
		menu = new Scene("mainMenu");
		TexturedRectangle tRect = new TexturedRectangle(0, 0, width, height, menu, new Resource("textures", "bg"));
		menu.addElement("bg", tRect);
	
		ProgressBar progressBar = new ProgressBar(100, (height/2)-10, width-100, (height/2)+10, menu);
		menu.addElement("progressBar1", progressBar);
		
		Button videoSettingsButton = new Button(width/2 - (144 * TalesOfOld.getGL().getWidth()/800), (height*.5)+(40 * TalesOfOld.getGL().getHeight()/600), width/2 + (144 * TalesOfOld.getGL().getWidth()/800), (height*.5) + (112 * TalesOfOld.getGL().getHeight()/600), menu);
		videoSettingsButton.getDisabledProperty().set(true);
		videoSettingsButton.setClickBehavior(new Runnable(){
			public void run(){
				System.out.println("Switching scenes");
				TalesOfOld.getGame().getUI().loadScene(settings);
			}
		});
		menu.addElement("videoSettingsButton", videoSettingsButton);
		
		Button exitButton = new Button(width/2 - (144 * TalesOfOld.getGL().getWidth()/800), videoSettingsButton.endY + 4, width/2 + (144 * TalesOfOld.getGL().getWidth()/800), videoSettingsButton.endY + (76 * TalesOfOld.getGL().getHeight()/600), menu);
		exitButton.getDisabledProperty().set(true);
		exitButton.setClickBehavior(new Runnable(){
			public void run(){
				TalesOfOld.getGame().exit();
			}
		});
		menu.addElement("exitButton", exitButton);
		
		Text videoSettingsText = new Text(width/2, videoSettingsButton.startY + ((videoSettingsButton.endY - videoSettingsButton.startY)/8), menu, TalesOfOld.getGame().getBaseFont(), "videoSettingsButtonText").setFontSize(32*(TalesOfOld.getGL().getHeight()/300)).setCenterAlign(true).setClickAction(videoSettingsButton.onClick);
		videoSettingsText.getDisabledProperty().bind(videoSettingsButton.getDisabledProperty());
		videoSettingsText.addEffect(new OutlineEffect(1, Color.BLACK));
		menu.addElement("videoSettingsText", videoSettingsText);
		
		Button playButton = new Button(width/2 - (144 * TalesOfOld.getGL().getWidth()/800), (height*.5)-(36 * TalesOfOld.getGL().getHeight()/600), width/2 + (144 * TalesOfOld.getGL().getWidth()/800), (height*.5) + (36 * TalesOfOld.getGL().getHeight()/600), menu);
		playButton.setColor(0.5, 0.5, 0.5);
		playButton.getDisabledProperty().set(true);
		playButton.setClickBehavior(new Runnable(){
			public void run(){
				System.out.println("clicked");
			}
		});
		menu.addElement("playButton", playButton);
		
		Text playText = new Text(width/2, playButton.startY + ((playButton.endY - playButton.startY)/8), menu, TalesOfOld.getGame().getBaseFont(), "playButtonText").setFontSize(32*(TalesOfOld.getGL().getHeight()/300)).setCenterAlign(true).setClickAction(playButton.onClick);
		playText.getDisabledProperty().bind(playButton.getDisabledProperty());
		playText.addEffect(new OutlineEffect(1, Color.BLACK));
		menu.addElement("playText", playText);
		
		Text exitText = new Text(width/2, exitButton.startY + ((exitButton.endY - exitButton.startY)/8), menu, TalesOfOld.getGame().getBaseFont(), "exitButtonText").setFontSize(32*(TalesOfOld.getGL().getHeight()/300)).setCenterAlign(true).setClickAction(exitButton.onClick);
		exitText.getDisabledProperty().bind(exitButton.getDisabledProperty());
		exitText.addEffect(new OutlineEffect(1, Color.BLACK));
		menu.addElement("exitText", exitText);
		
		Rectangle centerline = new Rectangle(width/2-4, 0, width/2, height, menu).setColor(0, 1, 0);
		centerline.getDisabledProperty().set(true);
		menu.addElement("centerLine", centerline);
		
		Text logoText = new Text(width/2, 10 + TalesOfOld.getGL().getHeight() / 10, menu, TalesOfOld.getGame().getBaseFont(), "titleText").setFontSize(TalesOfOld.getGL().getWidth()/10).setTextColor(Color.GRAY).setCenterAlign(true);
		logoText.addEffect(new OutlineEffect(1, Color.BLACK));
		menu.addElement("logoText", logoText);
		
		Text loadingText = new Text(width/2, progressBar.startY, menu, TalesOfOld.getGame().getBaseFont(), "loadingText").setFontSize(18).setTextColor(Color.BLACK).setCenterAlign(true);
		loadingText.getDisabledProperty().bind(progressBar.getDisabledProperty());
		menu.addElement("loadingText", loadingText);
		
		return menu;
	}
	
	private static Scene testScene(){
		double width = TalesOfOld.getGL().getWidth(), height = TalesOfOld.getGL().getHeight();
		testScene = new Scene("test");
		
		Button test = new Button(100, 100, 200, 200, testScene);
		test.setColor(1, 1, 1);
		
		TexturedRectangle tRect = new TexturedRectangle(0, 0, width, height, menu, new Resource("textures", "bg"));
		testScene.addElement("bg", tRect);
		
		testScene.addElement("test", test);
		
		return testScene;
	}
	
	public static Scene getScene(String name){
		for(Scene s : scenes){
			if(name.contentEquals(s.name)){
				return s;
			}
		}
		return null;
	}
	
	public static List<Scene> getScenes(){
		return scenes;
	}
 }
