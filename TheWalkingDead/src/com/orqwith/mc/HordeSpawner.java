package com.orqwith.mc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

/**
 * This class is an invisible, omnipresent Zombie spawner
 * 
 * @author JavaWarlord
 * 
 */
public class HordeSpawner implements Runnable {
	private static final int MINIMUM_DISTANCE = 5;
	private static final int RANDOM_DISTANCE = 5;
	private TheWalkingDeadConfig config;
	private Server server;
	private World world;
	private boolean active;
	private int chanceOfSpawn;
	private int maxHordeSize;
	private int minimumDistance;
	private int randomDistance;

	/**
	 * Constructor
	 * 
	 * @param configFile
	 */
	HordeSpawner(Server server, TheWalkingDeadConfig config) {
		this.server = server;
		this.world = server.getWorld("world");
		this.config = config;
		this.active = true;
		this.chanceOfSpawn = config.getSpawnChance();
		this.maxHordeSize = config.getHordeSize();
		this.minimumDistance = MINIMUM_DISTANCE;
		this.randomDistance = RANDOM_DISTANCE;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (rollToSpawn()) {
			spawnNearPlayer(getTargetPlayer());
		}
	}

	/**
	 * Spawns a zombie horde near a player.
	 * 
	 * @param player
	 *            the player
	 */
	public void spawnNearPlayer(Player player) {
		// TODO Auto-generated method stub
		if (player != null) {
			server.getLogger().info("Our victim: " + player.getName());
			player.sendMessage("Something putrid assails your nostrils...");
			spawnNear(player.getLocation());
		}
	}

	/**
	 * Spawns a zombie horde near a location.
	 * 
	 * @param location
	 *            the location
	 */
	public void spawnNear(Location location) {
		// TODO Auto-generated method stub
		spawnAt(Utilities.getRandomNearbyLocation(location, minimumDistance,
				randomDistance).toLocation(world));
	}

	/**
	 * Spawns a zombie horde at a location.
	 * 
	 * @param location
	 *            the location
	 */
	public void spawnAt(Location location) {
		// TODO Auto-generated method stub
		spawn(location, maxHordeSize);
	}

	/**
	 * Randomly select on online player
	 * 
	 * @return the player or null
	 */
	public Player getTargetPlayer() {
		// TODO Auto-generated method stub
		List<Player> players = Arrays.asList(server.getOnlinePlayers());
		if (players.size() > 0) {
			Collections.shuffle(players);
			return players.get(0);
		}
		return null;
	}

	/**
	 * Roll to see if a zombie horde spawns
	 * 
	 * @return true for spawn
	 */
	public boolean rollToSpawn() {
		// TODO Auto-generated method stub
		int roll = new Random().nextInt(100) + 1;
		server.getLogger().info(
				String.format(
						"Checking for spawn...  chance: %s, rolled: %s\n",
						chanceOfSpawn, roll));
		if (roll <= chanceOfSpawn)
			return true;
		else
			return false;
	}

	private void spawn(Location location, int size) {
		// TODO Auto-generated method stub
		for (int i = 0; i < maxHordeSize; i++) {
			world.spawn(location, Zombie.class);
		}
	}
}
