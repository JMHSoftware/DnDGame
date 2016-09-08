package com.fhbgds.dndgame.enums;

public enum EnumResolutionOptions {
	sn1920x1080,
	sn1280x720,
	sn640x480,
	sn1366x768,
	ef1440x900,
	ft1600x1200,
	ft800x600,
	ft400x300;
//	uhd4k;
	
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
		case sn1366x768:
			return "1366 x 768";
		case ef1440x900:
			return "1440 x 900";
//		case uhd4k:
//			return "3840 x 2160";
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
		case "1366 x 768":
			return sn1366x768;
		case "1440 x 900":
			return ef1440x900;
//		case "3840 x 2160":
//			return uhd4k;
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
