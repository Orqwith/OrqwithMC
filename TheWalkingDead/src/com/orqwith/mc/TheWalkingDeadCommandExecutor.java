package com.orqwith.mc;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.util.Vector;

public class TheWalkingDeadCommandExecutor implements CommandExecutor {
	private Server server;
	
	private int maxHordeSize = 5;

	public TheWalkingDeadCommandExecutor(int maxHordeSize, Server server)
	{
		this.maxHordeSize = maxHordeSize;
		this.server = server;
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
				spawnHorde(player.getLocation());
				return true;
			} else {
				sender.sendMessage("This command can only be run by a player.");
				return false;
			}
		} else if (args.length == 1) {
			Player target = (server.getPlayer(args[0]));
	        if (target == null) {
	        	sender.sendMessage(args[0] + " is not online!");
	            return false;
	        } else {
	        	spawnHorde(target.getLocation());
	        	return true;
	        }
		} else {
			sender.sendMessage("Too many parms passed.");
            return false;
		}
	}
	
	public void spawnHorde(Location location)
	{
		World world = server.getWorld("world");
		
		for(int i = 0; i < maxHordeSize; i++)
		{
			world.spawn(getRandomLocatioNearLocation(location, 5, 10).toLocation(world), Zombie.class);
		}
	}

	
	public Vector getRandomLocatioNearLocation(Location location, int minimumDistance, int randomDistance)
	{
		Vector v = location.toVector();
		
		
		v.setX(v.getX() + Math.random()%randomDistance + minimumDistance);
		v.setY(v.getY() + Math.random()%randomDistance + minimumDistance);
		v.setZ(v.getZ() + Math.random()%randomDistance + minimumDistance);
		
		return v;
	}
}
