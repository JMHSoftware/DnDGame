package com.fhbgds.dndgame.ui;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.newdawn.slick.opengl.TextureLoader;

import com.fhbgds.dndgame.TalesOfOld;
import com.fhbgds.dndgame.io.TextLoader;
import com.fhbgds.dndgame.render.GLHelper;
import com.fhbgds.dndgame.util.Resource;

public class Scene {
	String name;
	LinkedHashMap<String, UIElement> elements;
	Map<String, String> loadedStrings;
	long lastTimeOfLangUpdate;

	public Scene(String name) {
		this.name = name;
		this.elements = new LinkedHashMap<String, UIElement>();
		this.loadedStrings = this.loadText();
	}

	public Map<String, UIElement> getElements() {
		return this.elements;
	}

	public void addElement(String name, UIElement e) {
		this.elements.put(name, e);
	}

	public void draw() {
		for (String key : elements.keySet()) {
			UIElement e = elements.get(key);
			if (!e.getDisabled().get()) {
				e.draw();
			}
		}
	}

	public UIElement getElement(String name) {
		return this.elements.get(name);
	}

	public String getElementString(UIElement e) {
		Set<Entry<String, UIElement>> set = this.elements.entrySet();
		for (Entry<String, UIElement> e1 : set) {
			if (e1.getValue().equals(e))
				return e1.getKey();
		}
		return "";
	}
	
	public void reAlignAllText(){
		Set<Entry<String, UIElement>> set = this.elements.entrySet();
		for (Entry<String, UIElement> e1 : set) {
			if (e1.getValue() instanceof Text){
				Text text = (Text) e1;
				text.reAlignText();
			}
		}
	}
	
	public String getLocalizedString(String unlocalizedString) {
		String localized = loadedStrings.get(unlocalizedString);
		return localized == null ? this.name + "." + unlocalizedString : localized;
	}

	public Map<String, String> loadText() {
		try {
			Map<String, String> map = TextLoader.loadText(this.name, TalesOfOld.getGame().getLang().name().toLowerCase());
			this.lastTimeOfLangUpdate = System.nanoTime();
			return map;
		} catch (Exception e) {
			System.err.println("Error loading language file");
			e.printStackTrace();
		}
		return new HashMap<String, String>();
	}

	public void loadTextures() {
		Set<Entry<String, UIElement>> set = elements.entrySet();
		List<UIElement> list = new ArrayList<UIElement>();
		set.forEach(e -> list.add(e.getValue()));
		List<UIElement> texturedElements = new ArrayList<UIElement>();
		for (UIElement e : list) {
			if (e.hasTexture) {
				texturedElements.add(e);
			}
		}
		for (UIElement e : texturedElements) {
			if (e != null) {
				BufferedInputStream in;
				try {
					in = new BufferedInputStream(GLHelper.class.getResourceAsStream(e.textureLoc.getLocation() + "/" + e.textureLoc.getName() + ".png"));//new FileInputStream(new File(e.textureLoc.getLocation() + "/" + e.textureLoc.getName() + ".png")));
					e.texture = TextureLoader.getTexture("PNG", in);
					if (e.hasAdditionalTextures()) {
						List<Resource> resources = e.getAdditionalTextureLocations();
						for (Resource r : resources) {
							String id = r.getName();
							in = new BufferedInputStream(GLHelper.class.getResourceAsStream(r.getLocation() + "/" + id + ".png"));//new FileInputStream(new File(r.getLocation() + "/" + id + ".png")));
							e.addTexture(id, TextureLoader.getTexture("PNG", in));
						}
					}
				} catch (Exception e1) {
					System.err.println("ERROR loading texture:" + e.textureLoc.getLocation() + "/" + e.textureLoc.getName() + ".png");
					e1.printStackTrace();
				}
			}
		}
	}
}
