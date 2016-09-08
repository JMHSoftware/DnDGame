package com.fhbgds.dndgame.enums;

public enum EnumResolutionOptions {
	sn1920x1080,
	sn1280x720,
	sn640x480,
	ft1600x1200,
	ft800x600,
	ft400x300;
	
	public EnumResolutionOptions getNext(){
		int indexOf = this.ordinal();
		if(indexOf == EnumResolutionOptions.values().length - 1) return EnumResolutionOptions.values()[0];
		return EnumResolutionOptions.values()[indexOf + 1];
	}
	
	public String getString(){
		switch(this){
		case ft1600x1200:
			return "1600 x 1200";
		case ft400x300:
			return "400 x 300";
		case ft800x600:
			return "800 x 600";
		case sn1280x720:
			return "1280 x 720";
		case sn1920x1080:
			return "1920 x 1080";
		case sn640x480:
			return "640 x 480";
		default:
			return "ERROR";
		}
	}
	
	public static EnumResolutionOptions getFromString(String s){
		switch(s){
		case "1600 x 1200":
			return ft1600x1200;
		case "800 x 600":
			return ft800x600;
		case "400 x 300":
			return ft400x300;
		case "1920 x 1080":
			return sn1920x1080;
		case "1280 x 720":
			return sn1280x720;
		case "640 x 480":
			return sn640x480;
		default:
			return null;
		}
	}
	
	public int getWidth(){
		return Integer.valueOf(this.getString().substring(0, this.getString().indexOf(" x ")));
	}
	
	public int getHeight(){
		return Integer.valueOf(this.getString().substring(this.getString().indexOf(" x ") + 3));
	}
}
