package com.orqwith.mc;

import org.bukkit.plugin.java.JavaPlugin;

public class TheWalkingDeadConfig {
	boolean immuneToDaylight = false;
	int hordeSize = 5;
	int spawnChance = 50;
	int blockBreakSpawnInterval = 15;
	int spawnInterval = 0;
	int zombieHealth = 20;
	
	TheWalkingDeadConfig(JavaPlugin plugin)
	{
		this.hordeSize = plugin.getConfig().getInt("zombies.hordeSize");
		this.spawnChance = plugin.getConfig().getInt("zombies.spawnChance");
		this.blockBreakSpawnInterval = plugin.getConfig().getInt("zombies.blockBreakSpawnInterval");
		this.immuneToDaylight = plugin.getConfig().getBoolean("zombies.immuneToDaylight");
		this.spawnInterval = plugin.getConfig().getInt("zombies.spawnInterval") * 20;
		this.zombieHealth = plugin.getConfig().getInt("zombies.health");
	}
	
	public boolean isImmuneToDaylight() {
		return immuneToDaylight;
	}
	public void setImmuneToDaylight(boolean immuneToDaylight) {
		this.immuneToDaylight = immuneToDaylight;
	}
	public int getHordeSize() {
		return hordeSize;
	}
	public void setHordeSize(int hordeSize) {
		this.hordeSize = hordeSize;
	}
	public int getSpawnChance() {
		return spawnChance;
	}
	public void setSpawnChance(int spawnChance) {
		this.spawnChance = spawnChance;
	}
	public int getBlockBreakSpawnInterval() {
		return blockBreakSpawnInterval;
	}
	public void setBlockBreakSpawnInterval(int blockBreakSpawnInterval) {
		this.blockBreakSpawnInterval = blockBreakSpawnInterval;
	}
	public int getSpawnInterval() {
		return spawnInterval;
	}
	public void setSpawnInterval(int spawnInterval) {
		this.spawnInterval = spawnInterval;
	}
	public int getZombieHealth() {
		return zombieHealth;
	}
	public void setZombieHealth(int zombieHealth) {
		this.zombieHealth = zombieHealth;
	}	
	
}
