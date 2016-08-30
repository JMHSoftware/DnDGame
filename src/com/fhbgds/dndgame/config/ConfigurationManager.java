package com.fhbgds.dndgame.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ConfigurationManager {
	
	String defaultVideoConfigName = "video";
	
	public static Map<String, String> loadSettings(String name) throws Exception{
		Map<String, String> settings = new HashMap<String, String>();
		
		File configFile = new File("config/" + name + ".cfg");
		if(!configFile.exists()){ throw new FileNotFoundException(configFile.getName());}
		
		List<String> list = Files.readAllLines(configFile.toPath());
		
		settings = convertListToMap(list);
		
		return settings;
	}
	
	public static void saveSettings(Configuration config){
		File configFile = new File("config/" + config.name + ".cfg");
		File configDIR = new File("config/");
		if(!configDIR.exists()){
			configDIR.mkdirs();
		}
		try {
			RandomAccessFile file = new RandomAccessFile(configFile, "rw");
			file.setLength(0);
			file.close();
			Files.write(configFile.toPath(), formatMapForSaving(config.settings), StandardOpenOption.WRITE);
		} catch (Exception e) {
			System.err.println("Error writing to config file \"" + configFile.getName() + "\"");
			e.printStackTrace();
		}
	}
	
	public static Configuration getVideoConfig(){
		return new Configuration("video").load();
	}
	
	public static Map<String, String> loadDefaultSettings(String name){
		switch(name){
		case "video":
			Map<String, String> settings = new HashMap<String, String>();
		
			settings.put("width", "800");
			settings.put("height", "600");
			settings.put("fullscreen", "false");
		
			return settings;
		default:
			return null;
		}
	}
	
	public static List<String> formatMapForSaving(Map<String, String> settings){
		List<String> list = new ArrayList<String>();
		
		Iterator<Entry<String, String>> it = settings.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, String> e = it.next();
			String temp = e.getKey() + "=" + e.getValue();
			list.add(temp);
		}
		
		return list;
	}
	
	public static Map<String, String> convertListToMap(List<String> list){
		Map<String, String> settings = new HashMap<String, String>();
		
		for(String s : list){
			String k = "", v = "";
			k = s.substring(0, s.indexOf("="));
			v = s.substring(s.indexOf("=") + 1);
			settings.put(k, v);
		}
		
		return settings;
	}
}
