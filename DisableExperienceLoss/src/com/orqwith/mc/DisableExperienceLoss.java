package com.orqwith.mc;

import org.bukkit.plugin.java.JavaPlugin;

public class DisableExperienceLoss extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new DisableExperienceLossListener(), this);
	}
	
	@Override
	public void onDisable()
	{
		
	}
}
