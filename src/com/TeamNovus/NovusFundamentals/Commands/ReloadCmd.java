package com.TeamNovus.NovusFundamentals.Commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.TeamNovus.NovusFundamentals.NovusFundamentals;

public class ReloadCmd implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(commandLabel.equalsIgnoreCase("reloadnovus")) {
			if(!(sender.hasPermission("novusfundamentals.reload"))) {
				sender.sendMessage(ChatColor.RED + "You do not have permission for this command!");
				return true;
			}
			
			if (!new File(NovusFundamentals.getPlugin().getDataFolder(), "config.yml").exists()) {
				NovusFundamentals.getPlugin().saveDefaultConfig();
			}
			NovusFundamentals.getPlugin().reloadConfig();
			
			sender.sendMessage(ChatColor.GREEN + "Configuration reloaded sucessfully!");
		}
		return true;
	}
}
