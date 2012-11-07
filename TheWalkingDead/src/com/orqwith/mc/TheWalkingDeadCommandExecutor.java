package com.orqwith.mc;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TheWalkingDeadCommandExecutor implements CommandExecutor {
	private Server server;
	private TheWalkingDeadConfig config;
	private HordeSpawner hordeSpawner;

	public TheWalkingDeadCommandExecutor(Server server,
			TheWalkingDeadConfig config, HordeSpawner hordeSpawner) {
		this.server = server;
		this.config = config;
		this.hordeSpawner = hordeSpawner;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("twd.spawnHorde")) {
			return this.spawnHordeCommand(sender, cmd, label, args);
		}
		return false;
	}

	public boolean spawnHordeCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		if (args.length == 0) {
			if ((sender instanceof Player)) {
				// spawn a horde at current location
				Player player = (Player) sender;
				hordeSpawner.spawnNearPlayer(player);
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
				hordeSpawner.spawnNearPlayer(target);
				return true;
			}
		} else {
			sender.sendMessage("Too many parms passed.");
			return false;
		}
	}

}
