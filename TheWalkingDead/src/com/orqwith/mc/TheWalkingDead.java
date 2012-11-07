package com.orqwith.mc;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class TheWalkingDead extends JavaPlugin {

	public TheWalkingDeadConfig config;

	@Override
	public void onEnable() {
		String pluginFolder = this.getDataFolder().getAbsolutePath();
		new File(pluginFolder).mkdirs();
		getConfig().options().copyDefaults(true);
		saveConfig();
		config = new TheWalkingDeadConfig(this);

		getCommand("twd.spawnHorde").setExecutor(
				new TheWalkingDeadCommandExecutor(getServer(), config));
		getServer().getPluginManager().registerEvents(
				new SpawnHordeListener(this), this);
		getServer().getPluginManager().registerEvents(new ZombieListener(this),
				this);

		scheduleZombieHorde(config);
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
				new RunnableHordeSpawner(configFile), 60L,
				configFile.getSpawnInterval());
		getLogger().info(
				"Scheduling random zombie horde spawner as taskID: " + taskID);
	}

	class RunnableHordeSpawner implements Runnable {
		TheWalkingDeadConfig configFile;

		/**
		 * Constructor
		 * 
		 * @param configFile
		 */
		RunnableHordeSpawner(TheWalkingDeadConfig configFile) {
			this.configFile = configFile;
		}

		public void run() {
			randomHordeSpawn(getServer(), configFile.getHordeSize(),
					configFile.getSpawnChance());
		}
	}

	public static void randomHordeSpawn(Server server, int maxHordeSize,
			int spawnChance) {
		Random rand = new Random();

		server.getLogger().info("TWD Spawn Chance: " + spawnChance + "%");

		int rollSpawn = rand.nextInt(100) + 1;

		server.getLogger().info(
				String.format("SpawnChance: %s, Rolled: %s\n", spawnChance,
						rollSpawn));

		if (rollSpawn <= spawnChance) {
			List<Player> players = Arrays.asList(server.getOnlinePlayers());
			if (players.size() > 0) {
				Collections.shuffle(players);
				Player target = players.get(0);
				server.getLogger().info("Our victim: " + target.getName());
				target.sendMessage("Something putrid assails your nostrils...");
				spawnHorde(server, target.getLocation(), maxHordeSize);
			}
		}
	}

	public static void spawnHorde(Server server, Location location,
			int hordeSize) {
		World world = server.getWorld("world");

		for (int i = 0; i < hordeSize; i++) {
			world.spawn(Utilities.getRandomNearbyLocation(location, 5, 10)
					.toLocation(world), Zombie.class);
		}
	}
}
