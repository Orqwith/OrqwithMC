package com.orqwith.mc;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

public class TheWalkingDeadCommandExecutor implements CommandExecutor {
	private TheWalkingDead plugin;
	
	private int maxHordeSize = 5;

	public TheWalkingDeadCommandExecutor(TheWalkingDead plugin)
	{
		this.plugin = plugin;
		maxHordeSize = plugin.getConfig().getInt("parameters.maxHordeSize");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("twd.spawnHorde"))
		{
			return this.spawnHordeCommand(sender, cmd, label, args);
		}

		return false;
	}
	
	public boolean spawnHordeCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(args.length == 0)
		{
			if ((sender instanceof Player)) {
				// spawn a horde at current location
				Player player = (Player) sender;
				spawnHorde(player);
				return true;
			} else {
				sender.sendMessage("This command can only be run by a player.");
				return false;
			}
		} else if (args.length == 1) {
			Player target = (plugin.getServer().getPlayer(args[0]));
	        if (target == null) {
	        	sender.sendMessage(args[0] + " is not online!");
	            return false;
	        } else {
	        	spawnHorde(target);
	        	return true;
	        }
		} else {
			sender.sendMessage("Too many parms passed.");
            return false;
		}
	}
	
	public void spawnHorde(Player target)
	{
		World world = plugin.getServer().getWorld("world");
		
		for(int i = 0; i < maxHordeSize; i++)
			world.spawn(target.getLocation(), Zombie.class);
	}

}
