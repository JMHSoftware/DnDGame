package com.fhbgds.dndgame.enums;

public enum EnumCastType {
	TARGET,
	BEAM,
	PROJECTILE;
	
	public enum TargetType{
		SELF,
		OTHER,
		OBJECT;
	}
}
