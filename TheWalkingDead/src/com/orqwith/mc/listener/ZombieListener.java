package com.orqwith.mc.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.orqwith.mc.TheWalkingDead;
import com.orqwith.mc.entity.TheWalkingDeadZombie;
import com.orqwith.mc.entity.manager.ZombieManager;

public class ZombieListener  implements Listener {

	TheWalkingDead plugin;

	public ZombieListener(TheWalkingDead plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCombust (EntityCombustEvent event) 
	{
		if(event.getEntityType() == EntityType.ZOMBIE)
		{
			if (plugin.getConfig().getBoolean("zombies.immuneToDaylight")) {
				event.setCancelled(true);
			} else {
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		// disable anything that's not a zombie. Note that this includes non-monsters as well (chickens, villagers, ocelots, etc)
		if(event.getEntityType() != EntityType.ZOMBIE)
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityDeadh(EntityDeathEvent event)
	{
		TheWalkingDeadZombie zombie = ZombieManager.getZombie(event.getEntity());

		if(zombie != null)
			ZombieManager.removeZombie(zombie);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent event) 
	{
		// Check to make sure this is some hawt entity on entity hard-core action
		if(event instanceof EntityDamageByEntityEvent)
		{
			Entity assailant = ((EntityDamageByEntityEvent) event).getDamager();
			Entity victim = event.getEntity();

			// Issue #4...apparently shooting damage acts different. Fun.
			if(assailant instanceof Projectile)
			{
				Projectile projectile = (Projectile) assailant;
				assailant = projectile.getShooter();
			}

			// check to see if the assailant was one of our zombies
			TheWalkingDeadZombie assailingZombie  = ZombieManager.getZombie(assailant);

			if(assailingZombie != null)
			{
				// yep, one of our zombies is smacking a ho
				doZombieOnOtherAttack(event);
			} else if (ZombieManager.getZombie(victim) != null) {
				// Some ho's smacking our zombie!
				doOtherOnZombieAttack(event);
			}

		}
	}

	public void doOtherOnZombieAttack(EntityDamageEvent event)
	{
		Entity assailant = ((EntityDamageByEntityEvent) event).getDamager();
		Entity victim = event.getEntity();
		
		LivingEntity zombieVictim = ZombieManager.getZombie(victim).getLivingEntity();

		Player player = null;

		if(assailant instanceof Player)
		{
			player = (Player) assailant;

			// may need to do some player specific stuff here, like send messages, etc

			//TODO add total zombie damage done stat tracking here
		}

		// damage the zombie
		int damage = event.getDamage();

		// disable some damage types. Add more as necessary. Mostly just the obvious ones for now. 
		switch(event.getCause()) 
		{
		case FALL:
			event.setCancelled(true);
			break;
		case DROWNING:
			event.setCancelled(true);
			break;
		case SUFFOCATION:
			event.setCancelled(true);
			break;
		case POISON:
			event.setCancelled(true);
			break;
		}

		if(event.isCancelled()) // don't do the below if we cancelled this event above
		{
			return;
		}

		TheWalkingDeadZombie zombie = ZombieManager.getZombie(zombieVictim);
		ZombieManager.damageZombie(zombie, damage); // smack them with yo' baseball bat

		if(player != null)
		{
			// Let the player know how damaged the zombie is
			player.sendMessage("Zombie health: " + ChatColor.DARK_RED + (zombie.getHealth()) + " (-" + damage + ")");
		}

		if(zombie.getHealth() <= 0)
		{
			// it dead!
			event.setDamage(zombieVictim.getMaxHealth()); // kill it!

			// zombieVictim.setHealth(1);
			
		} else {
			// Make sure we don't ACTUALLY deal damage to our victim or else it will die before the total quasi damage is met.
			event.setDamage(0);
		}
	}

	public void doZombieOnOtherAttack(EntityDamageEvent event)
	{
		Entity victim = event.getEntity();

		// make sure our zombie is slapping something that's alive and not like...a tree
		if(victim instanceof LivingEntity)
		{
			LivingEntity livingVictim = (LivingEntity) victim; // Living...for now! mwahaha...

			// Do player specific things, like send them a message, or infect them, or give them a cheeseburger
			if(livingVictim.getType() == EntityType.PLAYER)
			{
				Player player = (Player) livingVictim;
				player.sendMessage("You've been bitten!");
			}

			event.setDamage(1);

			//TODO if this doesn't kill someone, this is where some "applyInfection" logic will need to go
			// assailingZombie.applyInfection(event, victim, ActivationCondition.ONATTACK);
		}
	}
}

