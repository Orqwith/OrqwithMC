package com.orqwith.mc;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;

public class ZombieListener  implements Listener {
	
	TheWalkingDead plugin;
	
	public ZombieListener(TheWalkingDead plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCombust (EntityCombustEvent event) {
			if (plugin.getConfig().getBoolean("zombies.immuneToDaylight")) {
				event.setCancelled(true);
			} else {
				return;
			}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		// disable anything that's not a zombie. Note that this includes non-monsters as well (chickens, villagers, ocelots, etc)
		if(event.getEntityType() != EntityType.ZOMBIE)
			event.setCancelled(true);
	}
}
