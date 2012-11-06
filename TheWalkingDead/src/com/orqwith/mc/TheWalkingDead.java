package com.orqwith.mc;

import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;

public final class TheWalkingDead extends JavaPlugin{

	private int spawnChance = 25;
	
	@Override
	public void onEnable() 
	{
		getLogger().info("Enabling TheWalkingDead addon");		
		getCommand("twd.spawnHorde").setExecutor(new TheWalkingDeadCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
		
		scheduleZombieHorde();

	}
	
	@Override
	public void onDisable() 
	{
		getLogger().info("Disabling TheWalkingDead addon");
	}
	
	/**
	 * Every minute, randomly spawn a horde of zombies
	 */
	public void scheduleZombieHorde()
	{
		int taskID = getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			   public void run() {
			       //TODO roll against a percentage defined in the config to see if a horde should spawn.
				   Random rand = new Random();
				   
			       spawnChance = getConfig().getInt("parameters.spawnChance");
			       int rollSpawn = rand.nextInt(spawnChance);
			       
			       if(rollSpawn == 0)
			       {
			    	   //TODO if a horde should spawn, randomly pick a position to start the spawning (or randomly pick a target victim? hmmm...)
			    	   getServer().broadcastMessage("If the code was done, this would spawn a random zombie horde somewhere in the world.");
			       }
			   }
			}, 60L, 1200L);
		getLogger().info("Scheduling random zombie horde spawner as taskID: " + taskID);
	}
}
