package com.orqwith.mc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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
}
