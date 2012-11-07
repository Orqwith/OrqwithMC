package com.orqwith.mc;

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

	private Server server;
	private World world;
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
		/* placeholder in case we want to implement for worlds not named "world" */
		this.world = server.getWorld("world");
		this.chanceOfSpawn = config.getSpawnChance();
		this.maxHordeSize = config.getHordeSize();
		this.minimumDistance = MINIMUM_DISTANCE;
		this.randomDistance = RANDOM_DISTANCE;
	}

	@Override
	public void run() {
		if (rollToSpawn()) {
			spawnNear(Utilities.getRandomPlayer(server), 0);
		}
	}

	/**
	 * Roll to see if a zombie horde spawns
	 * 
	 * @return true for spawn
	 */
	public boolean rollToSpawn() {
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

	/**
	 * Spawns a zombie horde near a player. If the size of the horde is 0, the
	 * horde size will be a random number between 1 and the maximum configured
	 * horde size.
	 * 
	 * @param player
	 *            the player
	 * @param size
	 *            the size of the horde
	 */
	public void spawnNear(Player player, int size) {
		if (player != null) {
			server.getLogger().info("Our victim: " + player.getName());
			player.sendMessage("Something putrid assails your nostrils...");
			if (size == 0)
				size = new Random().nextInt(maxHordeSize) + 1;
			spawnNear(player.getLocation(), size);
		}
	}

	/**
	 * Spawns a zombie horde of random size near a player.
	 * 
	 * @param player
	 *            the player
	 */
	public void spawnNear(Player player) {
		if (player != null) {
			server.getLogger().info("Our victim: " + player.getName());
			player.sendMessage("Something putrid assails your nostrils...");
			spawnNear(player.getLocation(),
					new Random().nextInt(maxHordeSize) + 1);
		}
	}

	/**
	 * Spawns a zombie horde in a random place near a location. If the size of
	 * the horde is 0, the horde size will be a random number between 1 and the
	 * maximum configured horde size.
	 * 
	 * @param location
	 *            the location
	 * @param size
	 *            the size of the horde
	 */
	public void spawnNear(Location location, int size) {
		if (size == 0)
			size = new Random().nextInt(maxHordeSize) + 1;
		spawnAt(Utilities.getRandomNearbyLocation(location, minimumDistance,
				randomDistance).toLocation(world), size);
	}

	/**
	 * Spawns a zombie horde at a location. If the size of the horde is 0, the
	 * horde size will be a random number between 1 and the maximum configured
	 * horde size.
	 * 
	 * @param location
	 *            the location
	 * @param size
	 *            the size of the horde
	 */
	public void spawnAt(Location location, int size) {
		if (size == 0)
			size = new Random().nextInt(maxHordeSize) + 1;
		spawn(location, size);
	}

	/**
	 * Spawn a zombie horde of a certain size at a certain location. This is the
	 * method that actually puts the zombies into the world.
	 * 
	 * @param location
	 * @param size
	 */
	private void spawn(Location location, int hordeSize) {
		for (int i = 0; i < hordeSize; i++) {
			world.spawn(location, Zombie.class);
		}
	}
}
