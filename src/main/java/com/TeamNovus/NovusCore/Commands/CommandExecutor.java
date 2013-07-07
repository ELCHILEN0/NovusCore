package com.TeamNovus.NovusCore.Commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CommandExecutor implements org.bukkit.command.CommandExecutor, TabCompleter {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		com.TeamNovus.NovusCore.Commands.Command cmd = null;
				
		String[] labels = (String[]) ArrayUtils.add(args, 0, label);
//		String[] trueArgs = labels;
//		for (int i = 0; i < labels.length; i++) {
//			String[] upToHereLabels = labels;
//			
//			while(upToHereLabels.length > i) {
//				upToHereLabels = (String[]) ArrayUtils.remove(upToHereLabels, upToHereLabels.length - 1);
//			}
//			
//			if(Commands.getCommand(upToHereLabels) != null) {
//				
//			}
//		}
		
		
		
		cmd = Commands.getCommand(labels);
		
		if (cmd == null) {
			sender.sendMessage(ChatColor.RED + "The specified command was not found!");
			
			return true;
		}

		if (cmd.doesntAllowConsole() && sender instanceof ConsoleCommandSender) {
			sender.sendMessage(cmd.getConsoleDisallowedMessage());
			
			return true;
		}
		
		if (cmd.doesntAllowPlayer() && sender instanceof Player) {
			sender.sendMessage(cmd.getPlayerDisallowedMessage());
			
			return true;
		}
		
		System.out.println(cmd.getMinArgs());
		System.out.println(cmd.getMaxArgs());
		
//		if (trueArgs.length < cmd.getMinArgs() || trueArgs.length > cmd.getMaxArgs()) {
//			sender.sendMessage(cmd.getUsageMessage());
//			
//			return true;
//		}
				
		cmd.onCommand(labels, sender, args);
		
		return true;
	}

	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		
		return new ArrayList<String>();
	}
	


}
