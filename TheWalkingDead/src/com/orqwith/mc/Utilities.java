package com.orqwith.mc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Utilities {
	public static Vector getRandomNearbyLocation(Location location,
			int minimumDistance, int randomDistance) {
		Vector v = location.toVector();

		v.setX(v.getX() + (Math.random() * randomDistance) + minimumDistance);
		v.setY(v.getY() + (Math.random() * randomDistance) + minimumDistance);
		v.setZ(v.getZ() + (Math.random() * randomDistance) + minimumDistance);

		return v;
	}

	/**
	 * Randomly select on online player
	 * 
	 * @return the player or null
	 */
	public static Player getRandomPlayer(Server server) {
		List<Player> players = Arrays.asList(server.getOnlinePlayers());
		if (players.size() > 0) {
			Collections.shuffle(players);
			return players.get(0);
		}
		return null;
	}
}
