package com.orqwith.mc;

import org.bukkit.Location;
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
}
