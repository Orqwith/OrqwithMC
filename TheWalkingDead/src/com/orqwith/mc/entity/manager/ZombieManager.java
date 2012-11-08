package com.orqwith.mc.entity.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;

import com.orqwith.mc.entity.TheWalkingDeadZombie;

public class ZombieManager {
	private static List<TheWalkingDeadZombie> zombies = new ArrayList<TheWalkingDeadZombie>();

	public static void addZombie(TheWalkingDeadZombie zombie) {
		zombies.add(zombie);
	}

	public static void damageZombie(TheWalkingDeadZombie zombie, int damage) {
		zombie.setHealth(zombie.getHealth() - damage);
	}

	public static TheWalkingDeadZombie getZombie(Entity entity) {
		for (TheWalkingDeadZombie z : zombies) {
			if (z.getLivingEntity() == entity) {
				return z;
			}
		}

		return null;
	}

	public static List<TheWalkingDeadZombie> getZombies() {
		return zombies;
	}

	public static boolean isDead(TheWalkingDeadZombie zombie) {
		if (zombie.getHealth() <= 0)
			return true;
		return false;
	}

	public static void removeZombie(TheWalkingDeadZombie zombie) {
		zombies.remove(zombie);
	}
}
