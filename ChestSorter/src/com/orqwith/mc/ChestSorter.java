package com.orqwith.mc;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ChestSorter extends JavaPlugin {

	public void onEnable() {
		getServer().getPluginManager().registerEvents(
				new ChestSorterListener(this), this);
	}

	private ItemStack[] stackItems(ItemStack[] items, int first, int last) {
		for (int i = first; i < last; i++) {
			ItemStack item1 = items[i];
			if (item1 == null) {
				continue;
			}
			int maxStackSize = item1.getMaxStackSize();
			if (item1.getAmount() <= 0 || maxStackSize == 1) {
				continue;
			}
			if (item1.getAmount() < maxStackSize) {
				int needed = maxStackSize - item1.getAmount();
				for (int j = i + 1; j < last; j++) {
					ItemStack item2 = items[j];
					if (item2 == null || item2.getAmount() <= 0
							|| maxStackSize == 1) {
						continue;
					}
					if (item2.getTypeId() == item1.getTypeId()
							&& item1.getDurability() == item2.getDurability()
							&& item1.getEnchantments().equals(
									item2.getEnchantments())) {
						if (item2.getAmount() > needed) {
							item1.setAmount(maxStackSize);
							item2.setAmount(item2.getAmount() - needed);
							break;
						} else {
							items[j] = null;
							item1.setAmount(item1.getAmount()
									+ item2.getAmount());
							needed = maxStackSize - item1.getAmount();
						}
					}
				}
			}
		}
		return items;
	}

	public void sort(Chest chest, Player player) {
		// TODO Auto-generated method stub
		ItemStack[] chestItems = chest.getInventory().getContents();
		chestItems = sortItems(chestItems, 0, chestItems.length);
		chest.getInventory().setContents(chestItems);
		player.sendMessage(ChatColor.DARK_GREEN + "Chest sorted!");
	}

	private ItemStack[] sortItems(ItemStack[] items, int first, int last) {
		items = stackItems(items, first, last);
		Arrays.sort(items, first, last, new ItemComparator());
		return items;
	}
}