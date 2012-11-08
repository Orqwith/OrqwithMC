package com.orqwith.mc.entity;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.orqwith.mc.TheWalkingDeadConfig;

public class TheWalkingDeadZombie {
	private int health;
	private LivingEntity livingEntity;
	
	public TheWalkingDeadZombie(LivingEntity livingEntity, TheWalkingDeadConfig config) {
		this.setLivingEntity(livingEntity);
		health = config.getZombieHealth();
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
		
	public void OnDeath(EntityDeathEvent event) {
		// Zombies should drop no items nor experience.
		List<ItemStack> originalDrops = event.getDrops();
		originalDrops.clear();
		event.setDroppedExp(0);
	}

	public LivingEntity getLivingEntity() {
		return livingEntity;
	}

	public void setLivingEntity(LivingEntity livingEntity) {
		this.livingEntity = livingEntity;
	}
}
