package com.orqwith.mc;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChestSorterListener implements Listener {
	private ChestSorter plugin;

	public ChestSorterListener(ChestSorter plugin) {
		// TODO Auto-generated constructor stub
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_BLOCK
				&& event.getClickedBlock().getType() == Material.CHEST) {
			plugin.sort((Chest) event.getClickedBlock().getState(),
					event.getPlayer());
		}
	}

}