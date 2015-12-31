package com.bazola.spaceylife.audio;

public enum SoundType {
	ALIEN_CALL_01("sounds/alienCall01.mp3"),
	ALIEN_CAPTURE_01("sounds/alienCapture01.mp3"),
	ALIEN_DIE_01("sounds/alienDie01.mp3"),
	ALIEN_EAT_01("sounds/alienEat01.mp3"),
	ALIEN_SPAWN_01("sounds/alienSpawn01.mp3"),
	FIGHTER_CAPTURE_01("sounds/fighterCapture01.mp3"),
	FIGHTE_DIE_01("sounds/fighterDie01.mp3"),
	FIGHTER_SPAWN_01("sounds/fighterSpawn01.mp3"),
	NO_02("sounds/no02.mp3"),
	PLACE_FLAG_01("sounds/placeFlag02.mp3"),
	SHOT_01("sounds/shot01.mp3"),
	TRADER_CAPTURE_01("sounds/traderCapture01.mp3"),
	TRADER_CRY_01("sounds/traderCry01.mp3"),
	TRADER_CRY_02("sounds/traderCry02.mp3"),
	TRADER_DIE_01("sounds/traderDie01.mp3"),
	TRADER_SAD_01("sounds/traderSad01.mp3"),
	TRADER_SPAWN_01("sounds/traderSpawn01.mp3");
	
	public final String path;
	
	private SoundType(String path) {
		this.path = path;
	}
}
