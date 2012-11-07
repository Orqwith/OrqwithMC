package com.orqwith.mc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
* This comment is better
*/
public class SpawnHordeListener implements Listener {

	TheWalkingDead plugin;
	static int blocksBroken = 0;

	public SpawnHordeListener(TheWalkingDead plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayDeath(PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		String deathMessage = String.format("%s has died. A horde of zombies has been attracted to their corpse...", player.getName());
		event.setDeathMessage(deathMessage);
		TheWalkingDead.spawnHorde(plugin.getServer(), player.getLocation(), plugin.config.getHordeSize());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreakEvent(BlockBreakEvent event)
	{
		int blockBreakSpawnInterval = plugin.config.getBlockBreakSpawnInterval();
		blocksBroken++;
		if(blocksBroken%blockBreakSpawnInterval == 0)
		{
			int hordeSize = plugin.config.getHordeSize();
			int spawnChance = plugin.config.getSpawnChance();
			TheWalkingDead.randomHordeSpawn(plugin.getServer(), hordeSize, spawnChance);
		}
	}


}
