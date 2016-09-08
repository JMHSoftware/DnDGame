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
	
	public EnumLang getNext(){
		int indexOf = this.ordinal();
		if(indexOf == EnumLang.values().length - 1) return EnumLang.values()[0];
		return EnumLang.values()[indexOf + 1];
	}
	
	public String getString(){
		switch(this){
		case EN:
			return "English";
		case TEST:
			return "Test";
		default:
			return "ERROR";
		}
	}
}
