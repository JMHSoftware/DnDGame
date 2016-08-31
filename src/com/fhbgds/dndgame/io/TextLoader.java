package com.fhbgds.dndgame.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TextLoader {
	
	public static Map<String, String> loadText(String name, String lang) throws Exception{
		File stringsLoc = new File("data/" + lang + "/" + name +".lang");
		Map<String, String> strings = new HashMap<String, String>();
		
		if(!stringsLoc.exists()){ throw new FileNotFoundException(stringsLoc.getName());}
		
		List<String> list = Files.readAllLines(stringsLoc.toPath());
		
		strings = convertListToMap(list);
		
		return strings;
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
