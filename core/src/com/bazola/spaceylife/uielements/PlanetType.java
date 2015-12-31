package com.bazola.spaceylife.uielements;

public enum PlanetType {
	
	ARCTIC01("arctic01"),
	ARCTIC02("arctic02"),
	ARCTIC03("arctic03"),
	ARCTIC04("arctic04"),
	ARCTIC05("arctic05"),
	ARCTIC06("arctic06"),
	
	DESERT01("desert01"),
	DESERT02("desert02"),
	DESERT03("desert03"),
	DESERT04("desert04"),
	DESERT05("desert05"),
	DESERT06("desert06"),
	
	GAS01("gas01"),
	GAS02("gas02"),
	GAS03("gas03"),
	GAS04("gas04"),
	GAS05("gas05"),
	GAS06("gas06"),
	GAS07("gas07"),
	GAS08("gas08"),
	GAS09("gas09"),
	GAS10("gas10"),
	GAS11("gas11"),
	
	INFERNO01("inferno01"),
	INFERNO02("inferno02"),
	INFERNO03("inferno03"),
	INFERNO04("inferno04"),
	
	JUNGLE01("jungle01"),
	JUNGLE02("jungle02"),
	JUNGLE03("jungle03"),
	JUNGLE04("jungle04"),
	JUNGLE05("jungle05"),
	JUNGLE06("jungle06"),
	
	OCEAN01("ocean01"),
	OCEAN02("ocean02"),
	OCEAN03("ocean03"),
	OCEAN04("ocean04"),
	OCEAN05("ocean05"),
	OCEAN06("ocean06"),
	
	ROCK01("rock01"),
	ROCK02("rock02"),
	ROCK03("rock03"),
	ROCK04("rock04"),
	ROCK05("rock05"),
	ROCK06("rock06"),
	
	SHIELD01("shield01"),
	
	TERRAN01("terran01"),
	TERRAN02("terran02"),
	TERRAN03("terran03"),
	TERRAN04("terran04"),
	TERRAN05("terran05"),
	TERRAN06("terran06"),
	
	TOXIC01("toxic01"),
	TOXIC02("toxic02"),
	TOXIC03("toxic03"),
	TOXIC04("toxic04");

	public final String fileName;

	private PlanetType(String fileName) {
		this.fileName = fileName;
	}
}
