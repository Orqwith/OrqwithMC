package com.orqwith.mc;
 
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DisableExperienceLossListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayDeath(PlayerDeathEvent event)
	{
		event.setKeepLevel(true);
		event.setDroppedExp(0);
	}
}
