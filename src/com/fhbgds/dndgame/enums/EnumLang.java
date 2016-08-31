package com.fhbgds.dndgame.enums;

import java.util.Arrays;
import java.util.List;

public enum EnumLang {
	EN,
	TEST;
	
	public static EnumLang getFromString(String name){
		List<EnumLang> langs = Arrays.asList(EnumLang.values());
		for(EnumLang l : langs){
			if(l.name().toLowerCase().contentEquals(name)){
				return l;
			}
		}
		return EN;
	}
}
