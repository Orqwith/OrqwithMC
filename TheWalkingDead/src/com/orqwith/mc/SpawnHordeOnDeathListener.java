package com.orqwith.mc;
 
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SpawnHordeOnDeathListener implements Listener {
	
	TheWalkingDead plugin;
	
	public SpawnHordeOnDeathListener(TheWalkingDead plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayDeath(PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		String deathMessage = String.format("%s has died. A horde of zombies has been attracted to their corpse...", player.getName());
		event.setDeathMessage(deathMessage);
		new TheWalkingDeadCommandExecutor(plugin.getMaxHordeSize(), plugin.getServer()).spawnHorde(player.getLocation());
	}
}
