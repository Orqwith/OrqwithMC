package com.orqwith.mc;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class TheWalkingDead extends JavaPlugin{

	private int spawnChance = 25;
	
	@Override
	public void onEnable() 
	{
		String pluginFolder = this.getDataFolder().getAbsolutePath();
		new File(pluginFolder).mkdirs();

		getConfig().options().copyDefaults(true);
		saveConfig();
		
		int maxHordeSize = getMaxHordeSize();
			
		getCommand("twd.spawnHorde").setExecutor(new TheWalkingDeadCommandExecutor(maxHordeSize, getServer()));
		getServer().getPluginManager().registerEvents(new SpawnHordeOnDeathListener(this), this);
		getServer().getPluginManager().registerEvents(new ZombieListener(this), this);
		
		scheduleZombieHorde();

	}
	
	public int getMaxHordeSize()
	{
		return getConfig().getInt("zombies.maxHordeSize");
	}
	
	public long getSpawnInterval()
	{
		return getConfig().getLong("zombies.spawnIntervalInSeconds")*20;
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
				   int maxHordeSize = getMaxHordeSize();
				   
			       spawnChance = getConfig().getInt("zombies.spawnChance");
			       
			       System.out.println("TWD Spawn Chance: " + spawnChance + "%");
			       
			       int rollSpawn = rand.nextInt(100/spawnChance);
			       
			       System.out.println("Rolled: " + rollSpawn);
			       
			       if(rollSpawn == 0)
			       {
			    	   //TODO if a horde should spawn, randomly pick a target victim and spawn a horde)
			    	   List<Player> players = Arrays.asList(getServer().getOnlinePlayers());
			    	   if(players.size() > 0)
			    	   {
			    		   Collections.shuffle(players);
			    	   
			    		   Player target = players.get(0);
			    	   
			    		   System.out.println("Our victim: " + target.getName());
			    	   
			    		   new TheWalkingDeadCommandExecutor(maxHordeSize, getServer()).spawnHorde(target.getLocation());
			    	   }
			    	   
			       }
			   }
			}, 60L, getSpawnInterval());
		getLogger().info("Scheduling random zombie horde spawner as taskID: " + taskID);
	}
}
