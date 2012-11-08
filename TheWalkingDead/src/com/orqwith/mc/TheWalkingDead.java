package com.orqwith.mc;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import com.orqwith.mc.commandexecutor.TheWalkingDeadCommandExecutor;
import com.orqwith.mc.listener.SpawnHordeListener;
import com.orqwith.mc.listener.ZombieListener;

public final class TheWalkingDead extends JavaPlugin {

	private TheWalkingDeadConfig twdConfig;
	private HordeSpawner hordeSpawner;

	@Override
	public void onEnable() {
		/* Create a folder to hold the config file */
		new File(this.getDataFolder().getAbsolutePath()).mkdirs();
		getConfig().options().copyDefaults(true);
		saveConfig();
		twdConfig = new TheWalkingDeadConfig(this);
		this.hordeSpawner = new HordeSpawner(this);

		getCommand("twd.spawnHorde").setExecutor(
				new TheWalkingDeadCommandExecutor(this, hordeSpawner));
		getServer().getPluginManager().registerEvents(
				new SpawnHordeListener(this, hordeSpawner), this);
		getServer().getPluginManager().registerEvents(new ZombieListener(this),
				this);

		scheduleZombieHorde(twdConfig);
	}

	@Override
	public void onDisable() {
		getLogger().info("Disabling TheWalkingDead addon");
	}

	/**
	 * Every spawnInterval, randomly spawn a horde of zombies
	 */
	public void scheduleZombieHorde(TheWalkingDeadConfig configFile) {
		int taskID = getServer().getScheduler().scheduleSyncRepeatingTask(this,
				hordeSpawner, 60L, configFile.getSpawnInterval());
		getLogger().info(
				"Scheduling random zombie horde spawner as taskID: " + taskID);
	}

	public TheWalkingDeadConfig getTwdConfig() {
		return twdConfig;
	}

	public void setTwdConfig(TheWalkingDeadConfig twdConfig) {
		this.twdConfig = twdConfig;
	}
}
